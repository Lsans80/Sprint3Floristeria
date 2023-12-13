package org.example.n1Exe1Txt.persistencia;

import org.example.n1Exe1Txt.entidad.Producto;
import org.example.n1Exe1Txt.entidad.Ticket;
//import org.example.n1Exe1.herramienta.SerDeSerObjectJson;

import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseDeDatos {
    private HashMap<Integer, Producto> productos;
    private HashMap<Integer, Ticket> tickets;
    private static BaseDeDatos instancia;
    private int nextProductoId;
    private int nextTicketId;
    //private final String FILE_NOT_FOUND_MSG = "File not found";

    private BaseDeDatos() {
        productos = new HashMap<>();
        tickets = new HashMap<>();
        load();
        //loadJsonFileToStock();
    }

    public static BaseDeDatos instanciar() {
        if (instancia == null) {
            instancia = new BaseDeDatos();
        }
        return instancia;
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public HashMap<Integer, Ticket> getTickets() {
        return tickets;
    }

    public HashMap<Integer, Producto> listarTicketsProductosVendidos(int id) {
        return tickets.get(id).getProductosVendidos();
    }

    public void agregarProducto(Producto producto) {
        productos.compute(producto.getProductoID(), (id, existingProducto) -> {
            if (existingProducto != null) {
                producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
            }
            return producto;
        });
    }

    public Ticket agregarTicket(Ticket ticket) {
        return tickets.put(ticket.getTicketID(), ticket);
    }

    public void agregarProductoTicket(int productoID, int ticketID) {
        Producto p = this.leerProducto(productoID);
        tickets.get(ticketID).agregarProductoAlTicket(p.clonar());
    }

    public Producto leerProducto(int id) {
        return productos.get(id);
    }

    public Ticket leerTicket(int id) {
        return tickets.get(id);
    }

    public Producto eliminarProducto(int id, int cantidad) {
        Producto p = leerProducto(id);
        if (p.getProductoCantidad() <= cantidad) {
            p.resetProductoCantidad();
            productos.remove(id);
        } else {
            p.reducirProductoCantidad(cantidad);
        }
        return p;
    }

    public void setCantidadProductoTicket(int productoID, int ticketID, int cantidad) {
        leerTicket(ticketID).getProductosVendidos().get(productoID).setProductoCantidad(cantidad);
/*
        Producto producto = tickets.get(ticketID).getProductosVendidos().get(productoID);
        int cantidadActual = producto.getProductoCantidad();
        producto.setProductoCantidad(cantidadActual + cantidadAnadida);

 */
    }

    public Ticket eliminarTicket(int id) {
        Ticket t = leerTicket(id);
        tickets.remove(id);
        return t;
    }

    public int maximoIDProductos() {
        Integer maxKey = 0;
        for (Integer key : productos.keySet()) {
            if (maxKey == 0 || productos.get(key).getProductoID() > productos.get(maxKey).getProductoID()) {
                maxKey = key;
            }
        }
        return maxKey;
    }

    public int maximoIDTickets() {
        Integer maxKey = 0;
        for (Integer key : tickets.keySet()) {
            if (maxKey == 0 || tickets.get(key).getTicketID() > tickets.get(maxKey).getTicketID()) {
                maxKey = key;
            }
        }
        return maxKey;
    }

    public HashMap<Integer, Producto> listarProductosFiltrando(Predicate<Producto> predicate) {
        return (HashMap<Integer, Producto>) productos.entrySet().stream().
                filter(entry -> predicate.test(entry.getValue())).collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    }

    public float getValorTotalStock() {
        return (float) productos.values().stream()
                .mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }

    public int getTotalCantidadStock() {
        return productos.values().stream().mapToInt(Producto::getProductoCantidad).sum();
    }

    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::calcularValorTotalDelTicket).sum();
    }

    public boolean existeProducto(int productoID) {
        return productos.containsKey(productoID);
    }

    public boolean existeProductoCantidad(int productoID) {
        return productos.get(productoID).getProductoCantidad() > 0;
    }

    public boolean existeProductoCantidadVsCantidadEnTicket(int productoID, int cantidadProductoEnTicket) {
        return productos.get(productoID).getProductoCantidad() >= cantidadProductoEnTicket;
    }
    
    public int getNextProductoId() {
        nextProductoId++;
        return nextProductoId;
    }

    public int getNextTicketId() {
        nextTicketId++;
        return nextTicketId;
    }

    public void load() {
        loadProductos();
        loadTickets();
        nextProductoId = maximoIDProductos();
        nextTicketId = maximoIDTickets();
    }

    @SuppressWarnings("unchecked")
    private void loadTickets() {
        File database = new File("ticketsDB.txt");
        if (database.exists()) {
            try (FileInputStream fis = new FileInputStream(database);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                tickets = (HashMap<Integer, Ticket>) ois.readObject();
            } catch (FileNotFoundException x) {
                System.err.format("FileNotFoundException: %s%n", x);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            } catch (ClassNotFoundException x) {
                System.err.format("ClassNotFoundException: %s%n", x);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadProductos() {
        File database = new File("productosDB.txt");
        if (database.exists()) {
            try (FileInputStream fis = new FileInputStream(database);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                productos = (HashMap<Integer, Producto>) ois.readObject();
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
        try (FileOutputStream fos = new FileOutputStream("productosDB.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(productos);
        } catch (FileNotFoundException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        try (FileOutputStream fos = new FileOutputStream("ticketsDB.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(tickets);
        } catch (FileNotFoundException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
	/*@SuppressWarnings("unchecked")
	public void loadJsonFileToStock () {
		File database = new File ("bdJson.txt");
		if (database.exists()) {
			try (FileReader input = new FileReader(database);
			BufferedReader buffer = new BufferedReader(input);) {
	
				String json = buffer.readLine();
				
				stock = SerDeSerObjectJson.deserialize(json, HashMap.class);
			} catch (IOException event) {
				System.out.println(FILE_NOT_FOUND_MSG);
				
			}
		}
		
	}
    
	public void saveStockJsonToFile() {
		try (FileWriter output = new FileWriter("bdJson.txt", true);
				BufferedWriter buffer = new BufferedWriter(output)){
			
			String json = SerDeSerObjectJson.serialize(stock);
		
			buffer.write(json);
		} catch (IOException event) {
			System.out.println(FILE_NOT_FOUND_MSG);
		}
	}*/

}
