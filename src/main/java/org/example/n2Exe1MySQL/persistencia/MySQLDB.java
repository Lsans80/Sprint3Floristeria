package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.entidad.Producto_Arbol;
import org.example.n2Exe1MySQL.entidad.Producto_Decoracion;
import org.example.n2Exe1MySQL.entidad.Producto_Flor;
import org.example.n2Exe1MySQL.herramienta.Material;

import java.sql.*;
import java.util.HashMap;

public class MySQLDB {
    private final String CONNECTION_URL; //= "jdbc:mysql://localhost/t3n2floristeria?user=root&password=MySQL_897300";
    private static MySQLDB instancia;

    private int nextProductoId;
    private int nextTicketId;

    private MySQLDB() {
        CONNECTION_URL = "jdbc:mysql://localhost/t3n2floristeria?user=root&password=MySQL_897300";
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

}
