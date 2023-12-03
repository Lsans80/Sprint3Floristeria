package org.example.n1Exe1.persistencia;

import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Ticket;
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


    private BaseDeDatos () {
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
   

    public HashMap<Integer, Ticket> getTickets() {
		return tickets;
	}
    

	public HashMap<Integer, Producto> getProductos() {
		return productos;
	}

    //TODO Estandarizar el nombre a "getTickets()"?
    public HashMap<Integer, Ticket> listarTickets() {
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
    	leerTicket(ticketID).agregarProductoAlTicket(productoID, p.clonar());
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
    		reducirCantidadProducto(id, cantidad);
    	}
        return p;
    }
    public Producto reducirCantidadProducto(int id, int cantidad) {
    	Producto p = leerProducto(id);
    	p.reducirProductoCantidad(cantidad);
    	return p;
    }
    public void setCantidadProductoTicket(int productoID, int ticketID, int cantidad) {
    	leerTicket(ticketID).getProductosVendidos().get(productoID).setProductoCantidad(cantidad);
    }
    public Ticket eliminarTicket(int id) {
        Ticket t = leerTicket(id);
        tickets.remove(id);
        return t;
    }
    public int maximoIDStock () {
    	Integer maxKey = 0;
        for (Integer key : productos.keySet()) {
            if (maxKey == 0 || productos.get(key).getProductoID() > productos.get(maxKey).getProductoID()) {
                maxKey = key;
            }
        }
        return maxKey;
    }
    //Funcion única para filtros personalizados desde la aplicación(?)
    public HashMap<Integer, Producto> listarProductosFiltrando(Predicate<Producto> predicate) {
       // return (HashMap<Integer, Producto>) stock.values().stream().filter(predicate).collect(Collectors.toMap(Producto::getProductoID, producto -> producto));
        return (HashMap<Integer, Producto>) productos.entrySet().stream().filter(entry -> predicate.test(entry.getValue())).collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    }
    public float getValorTotalStock() {
        return (float) productos.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }
    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::getTicketTotal).sum();
    }
    //TODO Pulir excepciones, casteo, etc.
    public int getNextProductoId() {
        nextProductoId++;
		return nextProductoId;
	}
	public int getNextTicketId() {
		return nextTicketId;
	}
	@SuppressWarnings("unchecked")
	public void load() {
        File database = new File ("database.txt");
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
        nextProductoId = maximoIDStock();
    }
    //TODO Pulir excepciones
    public void save() {
        try (FileOutputStream fos = new FileOutputStream("database.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(productos);
        } catch (FileNotFoundException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        } catch (IOException x){
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
