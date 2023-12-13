package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.herramienta.Input;
import org.example.n2Exe1MySQL.entidad.*;
import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;
import org.example.n2Exe1MySQL.herramienta.Material;

import java.sql.*;
import java.util.HashMap;
import java.util.Locale;

public class MySQLDB implements InterfaceBaseDeDatos{
    private static String CONNECTION_URL;
    private static MySQLDB instancia;
    private int nextProductoId;
    private int nextTicketId;

    private MySQLDB() {
        CONNECTION_URL = obtenerConexion();
        nextProductoId = generarSiguienteId("producto");
        nextTicketId = generarSiguienteId("ticket");
    }
    public static MySQLDB instanciar() {
        if (instancia == null) {
            instancia = new MySQLDB();
        }
        return instancia;
    }
    public String obtenerConexion(){
        String connection;
        boolean salir = false;
        String usuario;
        String password;

        do {
            usuario = Input.inputString("Dime tu usuario MySQL:");
            password = Input.inputString("Dime tu password MySQL:");
            connection = "jdbc:mysql://localhost/t3n2floristeria?user="+usuario+"&password="+password;

            try (Connection conn = DriverManager.getConnection(connection)) {
                System.out.println("La conexión se ha establecido.");
                salir = true;

            } catch (SQLException e){
                System.err.println("Usuario y/o contraseña no válidos.");
                e.getMessage();
            }

        } while (!salir);

        return connection;
    }

    @Override
    public HashMap<Integer, Producto> consultarProductos() {
        HashMap<Integer, Producto> productos = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QueriesSQL.GET_PRODUCTOS);
            productos = generaMapaProducto(rs);
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
        	e.getMessage();
        }
        return productos;
    }

    @Override
    public void agregarProducto(Producto producto) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(String.format(
                    "SELECT * FROM producto WHERE id = %d", producto.getProductoID()));

            if (rs.next()) {
                int nuevaCantidad = producto.getProductoCantidad() + rs.getInt("cantidad");
                actualizarCantidadProducto(producto.getProductoID(), nuevaCantidad);

            } else {

                String insertarProducto = String.format(Locale.US,
                        "INSERT INTO producto VALUES (%d, '%s', %f, '%s', %d)",
                        producto.getProductoID(), producto.getProductoNombre(),
                        producto.getProductoPrecio(), producto.getProductoTipo(),
                        producto.getProductoCantidad());
                stmt.executeUpdate(insertarProducto);

                String tipo = producto.getProductoTipo().toLowerCase();

                switch(tipo){
                    case "arbol":
                        String insertarArbol = String.format(Locale.US, "INSERT INTO arbol VALUES (%d, %f)",
                                producto.getProductoID(), ((Producto_Arbol) producto).getArbolAltura());
                        stmt.executeUpdate(insertarArbol);
                        break;
                    case "flor":
                        String insertarFlor = String.format(Locale.US, "INSERT INTO flor VALUES (%d, '%s')",
                                producto.getProductoID(), ((Producto_Flor) producto).getFlorColor());
                        stmt.executeUpdate(insertarFlor);
                        break;
                    case "decoracion":
                        String insertarDecoracion = String.format("INSERT INTO decoracion VALUES (%d,'%s')",
                                producto.getProductoID(), ((Producto_Decoracion) producto).getDecoracionMaterial());
                        stmt.executeUpdate(insertarDecoracion);
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
    }

    @Override
    public void actualizarCantidadProducto(int id, int nuevaCantidad) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE producto SET cantidad = " + nuevaCantidad + " WHERE producto.id = " + id);
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
    }

    @Override
    public Ticket agregarTicket(Ticket ticket) {
        PreparedStatement insertTicketOnTicketDB;

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            insertTicketOnTicketDB = conn.prepareStatement(QueriesSQL.AGREGAR_TICKET);
            insertTicketOnTicketDB.setInt(1, ticket.getTicketID());
            insertTicketOnTicketDB.setDate(2, Date.valueOf(ticket.getTicketDate()));
            insertTicketOnTicketDB.execute();

            ticket.getProductosVendidos().values().forEach(producto ->
                    agregarProductoAlTicket(producto, ticket.getTicketID(), conn));

        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }

        return ticket;
    }

    private void agregarProductoAlTicket(Producto producto, int ticketID, Connection conn) {
        PreparedStatement insertProductOnProductTicketDB;
        try {
            insertProductOnProductTicketDB = conn.prepareStatement(QueriesSQL.AGREGAR_PRODUCTO_TICKET);
            insertProductOnProductTicketDB.setInt(1, ticketID);
            insertProductOnProductTicketDB.setInt(2, producto.getProductoID());
            insertProductOnProductTicketDB.setInt(3, producto.getProductoCantidad());
            insertProductOnProductTicketDB.execute();

        } catch (SQLException e){
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
    }

    @Override
    public Producto consultarProducto(int id) {
        Producto producto = null;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QueriesSQL.GET_PRODUCTOS +
                    "WHERE producto.id = " + id);
            if (rs.next()) {
                switch (rs.getString("tipo").toLowerCase()) {
                    case "arbol":
                        producto = new Producto_Arbol(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getFloat("altura"),
                                rs.getInt("cantidad"));
                        break;
                    case "flor":
                        producto = new Producto_Flor(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getString("color"),
                                rs.getInt("cantidad"));
                        break;
                    case "decoracion":
                        producto = new Producto_Decoracion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                Material.valueOf(rs.getString("material")),
                                rs.getInt("cantidad"));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return producto;
    }

    @Override
    public HashMap<Integer, Ticket> consultarTickets() {
        HashMap<Integer, Ticket> tickets = new HashMap<>();
        Ticket ticket;
        int id;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticket");
            while (rs.next()) {
                id = rs.getInt("id");
                ticket = new Ticket(id);
                if (rs.getDate("fecha") != null){
                    ticket.setTicketDate(rs.getDate("fecha").toLocalDate());

                } else {
                    (ticket, conn);
                    tickets.put(id, ticket);
                }

            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return tickets;
    }

    //TODO es una copia de consultarTickets()??? Se puede borrar??
    @Override
    public Ticket consultarTicket(int id) {
        Ticket ticket = null;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticket " +
                    "WHERE ticket.id = " + id);
            if(rs.next()) {
                ticket = new Ticket(id);
                ticket.setTicketDate(rs.getDate("fecha").toLocalDate());
                agregarProductosTicket(ticket, conn);
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return ticket;
    }

    private void agregarProductosTicket (Ticket ticket, Connection conn) {
        int id = ticket.getTicketID();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( QueriesSQL.AGREGAR_PRODUCTOS_TICKET_PARA_CONSULTA + id);
            while (rs.next()) {
                switch (rs.getString("tipo").toLowerCase()) {
                    case "arbol":
                        ticket.agregarProductoAlTicket(new Producto_Arbol(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getFloat("altura"),
                                rs.getInt("producto_ticket.cantidad")));
                        break;
                    case "flor":
                        ticket.agregarProductoAlTicket(new Producto_Flor(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getString("color"),
                                rs.getInt("producto_ticket.cantidad")));
                        break;
                    case "decoracion":
                        ticket.agregarProductoAlTicket(new Producto_Decoracion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                Material.valueOf(rs.getString("material")),
                                rs.getInt("producto_ticket.cantidad")));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
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

    //Para borrar definitivamente el producto en base de datos y que no quede a 0.
    public void eliminarProductoDefinitivo (int id){

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            PreparedStatement preparedStatement = conn.prepareStatement(QueriesSQL.DELETE_PRODUCTO);
            preparedStatement.setInt(1, id);

        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
    }

    @Override
    public HashMap<Integer, Producto> consultarProductosFiltrando(String tipo) {
        HashMap<Integer, Producto> productos = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QueriesSQL.GET_PRODUCTOS +
                    "WHERE producto.tipo = \"" + tipo + "\"");
            productos = generaMapaProducto(rs);
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return productos;
    }

    @Override
    public float consultarValorTotalStock() {
        float valorTotal = 0;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(precio * cantidad) AS sumaTotal FROM producto");
            if (rs.next()) {
                valorTotal = rs.getFloat("sumaTotal");
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return valorTotal;
    }

    @Override
    public float consultarValorTotalTickets() {
        float valorTotal = 0;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QueriesSQL.CONSULTAR_VALOR_TOTAL_TICKETS);
            if (rs.next()) {
                valorTotal = rs.getFloat("sumaTotal");
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return valorTotal;
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

    private int generarSiguienteId(String table) {
        int id = 1;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM " + table);
            if (rs.next()) {
                id = rs.getInt("MAX(id)");
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return id;
    }

    private HashMap<Integer, Producto> generaMapaProducto(ResultSet rs) {
        HashMap<Integer, Producto> productos = new HashMap<>();
        try {
            while (rs.next()) {
                switch (rs.getString("tipo").toLowerCase()) {
                    case "arbol":
                        productos.put(rs.getInt("id"), new Producto_Arbol(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getFloat("altura"),
                                rs.getInt("cantidad")));
                        break;
                    case "flor":
                        productos.put(rs.getInt("id"), new Producto_Flor(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                rs.getString("color"),
                                rs.getInt("cantidad")));
                        break;
                    case "decoracion":
                        productos.put(rs.getInt("id"), new Producto_Decoracion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getFloat("precio"),
                                Material.valueOf(rs.getString("material")),
                                rs.getInt("cantidad")));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Hubo un error al acceder a los datos. Intenta nuevamente.");
            e.getMessage();
        }
        return productos;
    }

}
