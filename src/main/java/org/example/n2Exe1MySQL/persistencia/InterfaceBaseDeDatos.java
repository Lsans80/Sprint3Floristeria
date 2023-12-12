package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.entidad.Ticket;
import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;

import java.util.HashMap;

public interface InterfaceBaseDeDatos {

    HashMap<Integer, Producto> consultarProductos();
    HashMap<Integer, Ticket> consultarTickets();
    void agregarProducto(Producto producto);
    void actualizarCantidadProducto(int id, int nuevaCantidad);
    Ticket agregarTicket(Ticket ticket);
    Producto consultarProducto(int id);
    Ticket consultarTicket(int id);
    Producto eliminarProducto(int id, int cantidad) throws CantidadExcedida;
    HashMap<Integer, Producto> consultarProductosFiltrando(String tipo);

    float consultarValorTotalStock();
    float consultarValorTotalTickets();
    int obtenerSiguienteProductoId();
    int obtenerSiguienteTicketId();

}
