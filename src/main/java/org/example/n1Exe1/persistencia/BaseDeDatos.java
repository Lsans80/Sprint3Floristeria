package org.example.n1Exe1.persistencia;

import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Ticket;

import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseDeDatos {
    private HashMap<Integer, Producto> stock;
    private HashMap<Integer, Ticket> tickets;
    private static BaseDeDatos instancia;

    private BaseDeDatos () {
        stock = new HashMap<>();
        tickets = new HashMap<>();
        load();
    }

    public static BaseDeDatos instanciar() {
        if (instancia == null) {
            instancia = new BaseDeDatos();
        }
        return instancia;
    }
   

    public HashMap<Integer, Ticket> getTickets() {
		return tickets;
	}
    

	public HashMap<Integer, Producto> getStock() {
		return stock;
	}

	//TODO Estandarizar el nombre a "getStock()"?
    public HashMap<Integer, Producto> listarProductos() {
        return stock;
    }
    //TODO Estandarizar el nombre a "getTickets()"?
    public HashMap<Integer, Ticket> listarTickets() {
        return tickets;
    }
    public void agregarProducto(Producto producto) {
        stock.compute(producto.getProductoID(), (id, existingProducto) -> {
            if (existingProducto != null) {
                producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
            }
            return producto;
        });
    }
    public Ticket agregarTicket(Ticket ticket) {
       return tickets.put(ticket.getTicketID(), ticket);
    }
    public Producto leerProducto(int id) {
        return stock.get(id);
    }
    public Ticket leerTicket(int id) {
        return tickets.get(id);
    }
    public Producto eliminarProducto(int id) {
        Producto p = leerProducto(id);
        stock.remove(id);
        return p;
    }
    public Ticket eliminarTicket(int id) {
        Ticket t = leerTicket(id);
        tickets.remove(id);
        return t;
    }

    //Funcion única para filtros personalizados desde la aplicación(?)
    public HashMap<Integer, Producto> listarProductosFiltrando(Predicate<Producto> predicate) {
       // return (HashMap<Integer, Producto>) stock.values().stream().filter(predicate).collect(Collectors.toMap(Producto::getProductoID, producto -> producto));
        return (HashMap<Integer, Producto>) stock.entrySet().stream().filter(entry -> predicate.test(entry.getValue())).collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    }
    public float getValorTotalStock() {
        return (float) stock.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }
    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::getTicketTotal).sum();
    }
    //TODO Pulir excepciones, casteo, etc.
 
    @SuppressWarnings("unchecked")
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
    //TODO Pulir excepciones
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
