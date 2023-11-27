package n1Exe1.persistencia;

import n1Exe1.entidad.Producto;
import n1Exe1.entidad.Ticket;

import java.util.HashMap;

public class BaseDeDatos {
    HashMap<Integer, Producto> stock;
    HashMap<Integer, Ticket> tickets;

    public BaseDeDatos () {
        stock = new HashMap<Integer, Producto>();
        tickets = new HashMap<Integer, Ticket>();
    }
    public HashMap<Integer, Producto> getStock() {
        return stock;
    }
    public HashMap<Integer, Ticket> getTickets() {
        return tickets;
    }
    public void agregarProducto(Producto producto) {
        stock.compute(producto.getProductoID(), (id, existingProducto) -> {
            if (existingProducto != null) {
                producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
                return producto;
            } else {
                return producto;
            }
        });
    }
    public void agregarTicket(Ticket ticket) {
        tickets.put(ticket.getTicketID(), ticket);
    }
    public Producto leerProducto(int id) {
        return stock.get(id);
    }
    public Ticket leerTicket(int id) {
        return tickets.get(id);
    }
    public void eliminarProducto(int id) {
        stock.remove(id);
    }
    public void eliminarTicket(int id) {
        tickets.remove(id);
    }

    //TODO Double?
    public float getValorTotalStock() {
        return (float) stock.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }
    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::getTicketTotal).sum();

    }

}
