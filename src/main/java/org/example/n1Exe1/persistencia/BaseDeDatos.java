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
    private HashMap<Integer, Producto> stock;
    private HashMap<Integer, Ticket> tickets;
    private static BaseDeDatos instancia;
    private int nextProductoId;
    private int nextTicketId;
	//private final String FILE_NOT_FOUND_MSG = "File not found";


    private BaseDeDatos () {
        stock = new HashMap<>();
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
    
    public HashMap<Integer, Producto> listarTicketsProductosVendidos(int id) {
        return tickets.get(id).getProductosVendidos();
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
    
    public void agregarProductoTicket2(int productoID, int ticketID, int cantidad) {
    	Producto p = leerProducto(productoID).clonar();
        tickets.get(ticketID).getProductosVendidos().compute(p.getProductoID(), (id, existingProducto) -> {
            if (existingProducto != null) {
            setCantidadProductoTicket(productoID, ticketID, cantidad + existingProducto.getProductoCantidad());
            }
            return p;
        });
    }
    
    public void agregarProductoTicket(int productoID, int ticketID) {
    	Producto p = this.leerProducto(productoID);
    	leerTicket(ticketID).agregarProductoAlTicket(productoID, p.clonar());
    }
    
    public Producto leerProducto(int id) {
        return stock.get(id);
    }
    public Ticket leerTicket(int id) {
        return tickets.get(id);
    }
    public Producto eliminarProducto(int id, int cantidad) {
    	Producto p = leerProducto(id);
    	if (p.getProductoCantidad() <= cantidad) {
    		p.resetProductoCantidad();
    		stock.remove(id);
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
        for (Integer key : stock.keySet()) {
            if (maxKey == 0 || stock.get(key).getProductoID() > stock.get(maxKey).getProductoID()) {
                maxKey = key;
            }
        }
        
        return maxKey;
    }
    
    public int maximoIDTickets () {
    	Integer maxKey = 0;
        for (Integer key : tickets.keySet()) {
            if (maxKey == 0 || tickets.get(key).getTicketID() > tickets.get(maxKey).getTicketID()) {
                maxKey = key;
            }
        }
        
        return maxKey;
    }

    //Funcion única para filtros personalizados desde la aplicación(?)
    public HashMap<Integer, Producto> listarProductosFiltrando(Predicate<Producto> predicate) {
       // return (HashMap<Integer, Producto>) stock.values().stream().filter(predicate).collect(Collectors.toMap(Producto::getProductoID, producto -> producto));
        return (HashMap<Integer, Producto>) stock.entrySet().stream().filter(entry -> predicate.test(entry.getValue())).collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    }
    public float getValorTotalStock() {
        return (float) stock.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
    }
    
    public int getTotalCantidadStock() {
        return stock.values().stream().mapToInt(producto -> producto.getProductoCantidad()).sum();
    }
    
    
    public float getValorTotalTickets() {
        return (float) tickets.values().stream().mapToDouble(Ticket::calcularValorTotalDelTicket).sum();
    }
    
    public boolean existeProducto(int productoID) {
    	return listarProductos().containsKey(productoID);
    }
    
    public boolean existeProductoCantidad(int productoID) {
		return listarProductos().get(productoID).getProductoCantidad() > 0;
	}
    
	public boolean existeProductoCantidadVsCantidadEnTicket(int productoID, int cantidadProductoEnTicket) {
		return listarProductos().get(productoID).getProductoCantidad() >= cantidadProductoEnTicket;
	}

    
    //TODO Pulir excepciones, casteo, etc.
 
    public int getNextProductoId() {
    	nextProductoId++;
    	return nextProductoId;
	}

	public int getNextTicketId() {
		nextTicketId++;
		return nextTicketId;
	}

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
        nextProductoId = maximoIDStock() +1;
        nextTicketId = maximoIDTickets() +1;
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
       //nextProductoId = maximoIDStock() +1;
        //nextTicketId = maximoIDTickets() +1;
		
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
