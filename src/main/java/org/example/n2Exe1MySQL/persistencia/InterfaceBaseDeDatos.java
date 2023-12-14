package org.example.n2Exe1MySQL.persistencia;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.entidad.Ticket;
import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;

import java.util.HashMap;

public interface InterfaceBaseDeDatos {


    void agregarProducto(Producto producto);
    Ticket agregarTicket(Ticket ticket);
    void actualizarCantidadProducto(int id, int nuevaCantidad);
    HashMap<Integer, Producto> consultarProductos();
    HashMap<Integer, Ticket> consultarTickets();
    Producto consultarProducto(int id);
    Ticket consultarTicket(int id);
    HashMap<Integer, Producto> consultarProductosFiltrando(String tipo);
    float consultarValorTotalStock();
    float consultarValorTotalTickets();
    Producto eliminarProducto(int id, int cantidad) throws CantidadExcedida;
    int obtenerSiguienteProductoId();
    int obtenerSiguienteTicketId();

}
