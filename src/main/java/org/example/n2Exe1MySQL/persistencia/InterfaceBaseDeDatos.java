package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.entidad.Ticket;

import java.sql.ResultSet;
import java.util.HashMap;

public interface InterfaceBaseDeDatos {

    HashMap<Integer, Producto> getProductos();
    HashMap<Integer, Ticket> getTickets();
    String getTicketsDB();
    void agregarProducto(Producto producto);
    Ticket agregarTicket(Ticket ticket);
    Producto leerProducto(int id);
    Ticket leerTicket(int id);
    Producto eliminarProducto(int id, int cantidad);
    HashMap<Integer, Producto> listarProductosFiltrando(String tipo);

    float getValorTotalStock();
    float getValorTotalTickets();
    int getNextProductoId();
    int getNextTicketId();

}
