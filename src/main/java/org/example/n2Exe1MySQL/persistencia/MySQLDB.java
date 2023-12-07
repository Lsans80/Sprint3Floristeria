package org.example.n2Exe1MySQL.persistencia;

import org.example.n1Exe1Txt.herramienta.Input;
import org.example.n2Exe1MySQL.entidad.*;
import org.example.n2Exe1MySQL.herramienta.Material;

import java.sql.*;
import java.util.HashMap;

public class MySQLDB implements InterfaceBaseDeDatos{
    private final String CONNECTION_URL; //= "jdbc:mysql://localhost/t3n2floristeria?user=root&password=MySQL_897300";
    private static MySQLDB instancia;

    private int nextProductoId;
    private int nextTicketId;

    private MySQLDB() {

        String usuario = Input.inputString("Dime tu usuario MySQL:");
        String password = Input.inputString("Dime tu password MySQL:");
        CONNECTION_URL = "jdbc:mysql://localhost/t3n2floristeria?user="+usuario+"&password="+password+"";
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
            while (rs.next()) {
                switch(rs.getString("tipo").toLowerCase()) {
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

    @Override
    public HashMap<Integer, Ticket> getTickets() {
        return null;
    }

    @Override
    public void agregarProducto(Producto producto) {

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL) ) {
            Statement stmt = conn.createStatement();

            String insertarProducto = String.format("INSERT INTO producto VALUES (%d, '%s', %f, '%s', %d)",
                    producto.getProductoID(), producto.getProductoNombre(),
                    producto.getProductoPrecio(), producto.getProductoTipo(),
                    producto.getProductoCantidad());

            stmt.executeUpdate(insertarProducto);

            if (producto instanceof Producto_Arbol){
                String insertarArbol = String.format("INSERT into arbol VALUES (%f)",
                        ((Producto_Arbol) producto).getArbolAltura());
                stmt.executeUpdate(insertarArbol);

            } else if (producto instanceof Producto_Flor){
                String insertarFlor = String.format("INSERT into flor VALUES ('%d')",
                        ((Producto_Flor) producto).getFlorColor());
                stmt.executeUpdate(insertarFlor);

            } else {
                String insertarDecoracion = String.format("INSERT into decoracion VALUES ('%d')",
                        ((Producto_Decoracion) producto).getDecoracionMaterial());
                stmt.executeUpdate(insertarDecoracion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Ticket agregarTicket(Ticket ticket) {
        return null;
    }

    @Override
    public Producto leerProducto(int id) {
        return null;
    }

    @Override
    public Ticket leerTicket(int id) {
        return null;
    }

    @Override
    public Producto eliminarProducto(int id, int cantidad) {
        return null;
    }

    @Override
    public HashMap<Integer, Producto> listarProductosFiltrando(String tipo) {
        return null;
    }

    @Override
    public float getValorTotalStock() {
        return 0;
    }

    @Override
    public float getValorTotalTickets() {
        return 0;
    }

    @Override
    public int getNextProductoId() {
        return 0;
    }

    @Override
    public int getNextTicketId() {
        return 0;
    }

}
