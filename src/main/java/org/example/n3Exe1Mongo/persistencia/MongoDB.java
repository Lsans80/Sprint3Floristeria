package org.example.n3Exe1Mongo.persistencia;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.n3Exe1Mongo.entidad.*;
import org.example.n3Exe1Mongo.excepcion.CantidadExcedida;
import org.example.n3Exe1Mongo.herramienta.Material;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

public class MongoDB implements InterfaceBaseDeDatos {

    private final String CONNECTION_URL;
    private static MongoDB instancia;
    private static MongoDatabase database;
    private int nextProductoId;
    private int nextTicketId;

    private MongoDB() {
        CONNECTION_URL = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(CONNECTION_URL);
        database = mongoClient.getDatabase("floristeria");
        nextProductoId = generarSiguienteId("producto");
        nextTicketId = generarSiguienteId("ticket");
    }
    public static MongoDB instanciar() {
        if (instancia == null) {
            instancia = new MongoDB();
        }
        return instancia;
    }
    @Override
    public void agregarProducto(Producto producto) {
        int id = producto.getProductoID();
        MongoCollection<Document> productos = database.getCollection("productos");
        Producto productoExistente = consultarProducto(id);

        if (productoExistente == null) {
            Document doc = mapearProductoADocumento(producto);
            productos.insertOne(doc);
        }
        else {
            actualizarCantidadProducto(id, producto.getProductoCantidad() + productoExistente.getProductoCantidad());
        }
    }
    @Override
    public Ticket agregarTicket(Ticket ticket) {
        MongoCollection<Document> tickets = database.getCollection("tickets");
        Document doc = mapearTicketADocumento(ticket);

        tickets.insertOne(doc);
        return ticket;
    }
    @Override
    public void actualizarCantidadProducto(int id, int nuevaCantidad) {
        MongoCollection<Document> productos = database.getCollection("productos");

        productos.updateOne(
                Filters.eq("_id", id),
                Updates.set("cantidad", nuevaCantidad)
        );
    }
    @Override
    public HashMap<Integer, Producto> consultarProductos() {
        HashMap<Integer, Producto> productosMap = new HashMap<>();
        MongoCollection<Document> productosCollection = database.getCollection("productos");

        productosCollection.find().forEach(
                productoDoc -> productosMap.put(productoDoc.getInteger("_id"), mapearDocumentoAProducto(productoDoc)));
        return productosMap;
    }
    private Producto mapearDocumentoAProducto(Document doc) {
        Producto producto = null;

        switch (doc.getString("tipo").toLowerCase()) {
            case "arbol":
                producto = new Producto_Arbol(
                        doc.getInteger("_id"),
                        doc.getString("nombre"),
                        doc.getDouble("precio").floatValue(),
                        doc.getDouble("altura").floatValue(),
                        doc.getInteger("cantidad"));
                break;
            case "flor":
                producto = new Producto_Flor(
                        doc.getInteger("_id"),
                        doc.getString("nombre"),
                        doc.getDouble("precio").floatValue(),
                        doc.getString("color"),
                        doc.getInteger("cantidad"));
                break;
            case "decoracion":
                producto = new Producto_Decoracion(
                        doc.getInteger("_id"),
                        doc.getString("nombre"),
                        doc.getDouble("precio").floatValue(),
                        Material.valueOf(doc.getString("material")),
                        doc.getInteger("cantidad"));
                break;
        }
        return producto;
    }
    @Override
    public HashMap<Integer, Ticket> consultarTickets() {
        HashMap<Integer, Ticket> ticketsMap = new HashMap<>();
        MongoCollection<Document> ticketsCollection = database.getCollection("tickets");

        ticketsCollection.find().forEach(
                ticketDoc -> ticketsMap.put(ticketDoc.getInteger("_id"), mapearDocumentoATicket(ticketDoc)));
        return ticketsMap;
    }
    private Ticket mapearDocumentoATicket(Document doc) {
        int id = doc.getInteger("_id");
        Ticket ticket = new Ticket(id);

        doc.getList("productos", Document.class).forEach(
                productoDoc -> ticket.agregarProductoAlTicket(mapearDocumentoAProducto(productoDoc)));
        return ticket;
    }
    @Override
    public Producto consultarProducto(int id) {
        Producto producto = null;
        MongoCollection<Document> productosCollection = database.getCollection("productos");
        Document productoDoc = productosCollection.find(Filters.eq("_id", id)).first();
        if (productoDoc != null) {
            producto = mapearDocumentoAProducto(productoDoc);
        }
        return producto;
    }
    @Override
    public Ticket consultarTicket(int id) {
        Ticket ticket = null;
        MongoCollection<Document> ticketsCollection = database.getCollection("tickets");
        Document ticketDoc = ticketsCollection.find(Filters.eq("_id", id)).first();
        if (ticketDoc != null) {
            ticket = mapearDocumentoATicket(ticketDoc);
        }
        return ticket;
    }
    private Document mapearProductoADocumento(Producto producto) {
        Document doc = new Document("_id", producto.getProductoID()).
            append("nombre", producto.getProductoNombre()).
            append("precio", producto.getProductoPrecio()).
            append("cantidad", producto.getProductoCantidad()).
            append("tipo", producto.getProductoTipo());
        switch (producto.getProductoTipo().toLowerCase()) {
            case "arbol":
                doc.append("altura", ((Producto_Arbol) producto).getArbolAltura());
                break;
            case "flor":
                doc.append("color", ((Producto_Flor) producto).getFlorColor());
                break;
            case "decoracion":
                doc.append("material", ((Producto_Decoracion) producto).getDecoracionMaterial());
                break;
        }
        return doc;
    }
    private Document mapearTicketADocumento(Ticket ticket) {
        Document doc;
        List<Document> productos = new ArrayList<>();

        ticket.getProductosVendidos().values().forEach(producto -> productos.add(mapearProductoADocumento(producto)));
        doc = new Document("_id", ticket.getTicketID()).
                append("fecha", Date.valueOf(ticket.getTicketDate())).
                append("productos", productos);
        return doc;
    }
    @Override
    public HashMap<Integer, Producto> consultarProductosFiltrando(String tipo) {
        HashMap<Integer, Producto> productosMap = new HashMap<>();
        MongoCollection<Document> productosCollection = database.getCollection("productos");
        productosCollection.find(Filters.eq("tipo", tipo)).forEach(
                productoDoc -> productosMap.put(productoDoc.getInteger("_id"), mapearDocumentoAProducto(productoDoc)));
        return productosMap;
    }
    @Override
    public float consultarValorTotalStock() {
        float valorTotal = 0;
        MongoCollection<Document> productos = database.getCollection("productos");
        Bson projection = Projections.fields(Projections.include("precio", "cantidad"), Projections.excludeId());
        Document producto;

        try (MongoCursor<Document> cursorIterator = productos.find().projection(projection).cursor()) {
            while (cursorIterator.hasNext()) {
                producto = cursorIterator.next();
                valorTotal += producto.getDouble("precio").floatValue() * producto.getInteger("cantidad");
            }
        }
        return valorTotal;
    }
    @Override
    public float consultarValorTotalTickets() {
        float valorTotal = 0;
        MongoCollection<Document> tickets = database.getCollection("tickets");
        Bson projection = Projections.fields(Projections.include("productos"), Projections.excludeId());
        List<Document> productos;

        try (MongoCursor<Document> cursorIterator = tickets.find().projection(projection).cursor()) {
            while (cursorIterator.hasNext()) {
                productos = cursorIterator.next().getList("productos", Document.class);
                for (Document producto : productos) {
                    valorTotal += producto.getDouble("precio").floatValue() * producto.getInteger("cantidad");
                }
            }
        }
        return valorTotal;
    }
    @Override
    public Producto eliminarProducto(int id, int cantidadEliminar) throws CantidadExcedida {
        Producto producto = consultarProducto(id);
        int cantidadActual = producto.getProductoCantidad();

        if (cantidadActual >= cantidadEliminar) {
            actualizarCantidadProducto(id, cantidadActual - cantidadEliminar);
            producto.reducirProductoCantidad(cantidadEliminar);
        } else {
            throw new CantidadExcedida("La cantidad indicada excede la cantidad en stock.");
        }
        return producto;
    }
    @Override
    public int obtenerSiguienteProductoId() {
        nextProductoId++;
        return nextProductoId;
    }
    @Override
    public int obtenerSiguienteTicketId() {
        nextTicketId++;
        return nextTicketId;
    }
    public int generarSiguienteId(String tipo) {
        int id = 1;

        MongoCollection<Document> collection = database.getCollection(tipo + "s");
        Document doc = collection.find().sort(descending("_id")).first();
        if (doc != null) {
            id = doc.getInteger("_id");
        }
        return id;
    }
}
