package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.entidad.Ticket;
import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;

import java.util.HashMap;

public interface InterfaceBaseDeDatos {

    HashMap<Integer, Producto> getProductos();
    HashMap<Integer, Ticket> getTickets();
    void agregarProducto(Producto producto);
    void setCantidadProducto(int id, int nuevaCantidad);
    Ticket agregarTicket(Ticket ticket);
    Producto leerProducto(int id);
    Ticket leerTicket(int id);
    Producto eliminarProducto(int id, int cantidad) throws CantidadExcedida;
    HashMap<Integer, Producto> listarProductosFiltrando(String tipo);

    float getValorTotalStock();
    float getValorTotalTickets();
    int getNextProductoId();
    int getNextTicketId();

}
