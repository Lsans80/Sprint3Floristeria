package org.example.n2Exe1MySQL.persistencia;

import org.example.n1Exe1Txt.herramienta.Input;
import org.example.n2Exe1MySQL.entidad.*;
import org.example.n2Exe1MySQL.herramienta.Material;

import java.sql.*;
import java.util.HashMap;
import java.util.Locale;

public class MySQLDB implements InterfaceBaseDeDatos{
    private final String CONNECTION_URL;
    private static MySQLDB instancia;
    private int nextProductoId;
    private int nextTicketId;

    private MySQLDB() {
        String usuario = Input.inputString("Dime tu usuario MySQL:");
        String password = Input.inputString("Dime tu password MySQL:");
        CONNECTION_URL = "jdbc:mysql://localhost/t3n2floristeria?user="+usuario+"&password="+password;
        nextProductoId = generateNextID("producto");
        nextTicketId = generateNextID("ticket");
    }
    public static MySQLDB instanciar() {
        if (instancia == null) {
            instancia = new MySQLDB();
        }
        return instancia;
    }

    public HashMap<Integer, Producto> getProductos() {
        HashMap<Integer, Producto> productos = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto " +
                    "LEFT JOIN arbol ON producto.id = arbol.id " +
                    "LEFT JOIN flor ON producto.id = flor.id " +
                    "LEFT JOIN decoracion ON producto.id = decoracion.id");
            productos = generaMapaProducto(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public HashMap<Integer, Ticket> getTickets() {
        return null;
    }
    
    public String getTicketsDB() {
    	Statement getTicketsResultSet;
    	ResultSet ticketsResultSet = null;
    	String result = "";
    	try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
    		getTicketsResultSet = conn.createStatement();
    		ticketsResultSet = getTicketsResultSet.executeQuery(QueriesSQL.LISTAR_TICKETS);
    		while (ticketsResultSet.next()) {
				result += ticketsResultSet.getString("Id") + " | " 
						+ "Fecha: " + ticketsResultSet.getString("Fecha") + " | "
						+ "Nombre: " + ticketsResultSet.getString("Nombre") + " | "
						+ "Cantidad: " + ticketsResultSet.getString("Cantidad") + " | ";
			}
			ticketsResultSet.close();
		} catch (Exception ex) {
			System.err.println("There is no connection");
			ex.printStackTrace();
		}
    	
    	return result;
    }

    @Override
    public void agregarProducto(Producto producto) {
    //TODO Habria que añadir una comprobacion: si producto no existe en la base de datos, crear; si el producto ya existe, sumar cantidades;
    // Para hacer update de la cantidad una vez sumada, se puede usar el metodo privado private void setCantidadProducto(int id, int nuevaCantidad)
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();

            String insertarProducto = String.format(Locale.US, "INSERT INTO producto VALUES (%d, '%s', %f, '%s', %d)",
                    producto.getProductoID(), producto.getProductoNombre(),
                    producto.getProductoPrecio(), producto.getProductoTipo(),
                    producto.getProductoCantidad());
            stmt.executeUpdate(insertarProducto);

            if (producto instanceof Producto_Arbol){
                String insertarArbol = String.format("INSERT into arbol VALUES (%f)",
                        ((Producto_Arbol) producto).getArbolAltura());
                stmt.executeUpdate(insertarArbol);

            } else if (producto instanceof Producto_Flor){
                String insertarFlor = String.format("INSERT into flor VALUES ('%s')",
                        ((Producto_Flor) producto).getFlorColor());
                stmt.executeUpdate(insertarFlor);

            } else {
                String insertarDecoracion = String.format("INSERT into decoracion VALUES ('%s')",
                        ((Producto_Decoracion) producto).getDecoracionMaterial());
                stmt.executeUpdate(insertarDecoracion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCantidadProducto(int id, int nuevaCantidad) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE producto SET cantidad = " + nuevaCantidad + " WHERE producto.id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
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

        } catch (SQLException ex) {
            System.err.println("There is no connection");
            ex.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public Producto leerProducto(int id) {
        Producto producto = null;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto " +
                    "LEFT JOIN arbol ON producto.id = arbol.id " +
                    "LEFT JOIN flor ON producto.id = flor.id " +
                    "LEFT JOIN decoracion ON producto.id = decoracion.id " +
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
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public Ticket leerTicket(int id) {
        return null;
    }

    @Override
    public Producto eliminarProducto(int id, int cantidadEliminar) {
        Producto producto = leerProducto(id);
        int cantidadActual = producto.getProductoCantidad();

        if (cantidadActual >= cantidadEliminar) {
            setCantidadProducto(id, cantidadActual - cantidadEliminar);
            producto.reducirProductoCantidad(cantidadEliminar);
        } else {
            //TODO Gestionar excepcion?
        }
        return producto;
    }

    @Override
    public HashMap<Integer, Producto> listarProductosFiltrando(String tipo) {
        HashMap<Integer, Producto> productos = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM producto " +
                    "LEFT JOIN arbol ON producto.id = arbol.id " +
                    "LEFT JOIN flor ON producto.id = flor.id " +
                    "LEFT JOIN decoracion ON producto.id = decoracion.id " +
                    "WHERE producto.tipo = \"" + tipo + "\"");
            productos = generaMapaProducto(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public float getValorTotalStock() {
        float valorTotal = 0;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(precio * cantidad) AS sumaTotal FROM producto");
            if (rs.next()) {
                valorTotal = rs.getFloat("sumaTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valorTotal;
    }

    @Override
    public float getValorTotalTickets() {
        return 0;
    }

    @Override
    public int getNextProductoId() {
        nextProductoId++;
        return nextProductoId;
    }

    @Override
    public int getNextTicketId() {
        nextTicketId++;
        return nextTicketId;
    }

    private int generateNextID(String table) {
        int id = 1;
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM " + table);
            if (rs.next()) {
                id = rs.getInt("MAX(id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return productos;
    }

}
