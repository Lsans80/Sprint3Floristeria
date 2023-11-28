package org.example.n1Exe1.persistencia;

import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Ticket;

import java.io.*;
import java.util.HashMap;

public class BaseDeDatos {
    private HashMap<Integer, Producto> stock;
    private HashMap<Integer, Ticket> tickets;

    private BaseDeDatos instancia;

    private BaseDeDatos () {
        stock = new HashMap<Integer, Producto>();
        tickets = new HashMap<Integer, Ticket>();
        load();
    }

    public BaseDeDatos instanciar() {
        if (instancia == null) {
            instancia = new BaseDeDatos();
        }
        return instancia;
    }
    public HashMap<Integer, Producto> listarProductos() {
        return stock;
    }
    public HashMap<Integer, Ticket> listarTickets() {
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

    public float getValorTotalStock() {
        return (float) stock.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }
    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::getTicketTotal).sum();
    }

    public void load() {
        File database = new File ("database.txt");
        if (database.exists()) {
            try (FileInputStream fis = new FileInputStream(database);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                stock = (HashMap<Integer, Producto>) ois.readObject();
            } catch (FileNotFoundException x) {
                System.err.format("FileNotFoundException: %s%n", x);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            } catch (ClassNotFoundException x) {
                System.err.format("ClassNotFoundException: %s%n", x);
            }
        }
    }
    public void save() {
        try (FileOutputStream fos = new FileOutputStream("database.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(stock);
        } catch (FileNotFoundException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        } catch (IOException x){
            System.err.format("IOException: %s%n", x);
        }
    }

}
