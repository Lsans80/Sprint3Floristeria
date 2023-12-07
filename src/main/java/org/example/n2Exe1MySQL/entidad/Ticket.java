package org.example.n2Exe1MySQL.entidad;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.HashMap;

import org.example.n2Exe1MySQL.herramienta.ConnectionFloristeria;
import org.example.n2Exe1MySQL.herramienta.QueryFloristeria;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private LocalDate ticketDate;
	private HashMap<Integer, Producto> productosVendidos;
	private float ticketTotal = 0.0F;
	ConnectionFloristeria floristeriaDbConnection;

	public Ticket(int ticketID) {
		this.id = ticketID;
        floristeriaDbConnection = new ConnectionFloristeria();
		ticketDate = LocalDate.now();
		productosVendidos = new HashMap<>();
		ticketTotal = calcularValorTotalDelTicket();
	}

	public HashMap<Integer, Producto> getProductosVendidos() {
		return productosVendidos;
	}

	public double getTicketTotal() {
		return ticketTotal;
	}

	public void setTicketTotal(float ticketTotal) {
		this.ticketTotal = ticketTotal;
	}

	public int getTicketID() {
		return id;
	}

	public LocalDate getTicketDate() {
		return ticketDate;
	}

	public HashMap<Integer, Producto> agregarProductoAlTicket(Producto producto) {
		productosVendidos.compute(producto.getProductoID(), (id, existingProducto) -> {
			if (existingProducto != null) {
				producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
			}
			return producto;
		});
		return productosVendidos;
	}
	
	public void agregarProductoAlTicketDb(Producto producto) {
    	PreparedStatement insertProductOnProductTicketDB;
    	try (Connection conexiondb = floristeriaDbConnection.getConnection()) {
    		insertProductOnProductTicketDB = conexiondb.prepareStatement(QueryFloristeria.AGREGAR_PRODUCTO_TICKET);
    		insertProductOnProductTicketDB.setInt(1, this.getTicketID());
    		insertProductOnProductTicketDB.setInt(2, producto.getProductoID());
    		insertProductOnProductTicketDB.setInt(3, producto.getProductoCantidad());
    		insertProductOnProductTicketDB.execute();
		} catch (Exception ex) {
			System.out.println("No hay conexion.");
			ex.printStackTrace();
		}
    	
    	productosVendidos.compute(producto.getProductoID(), (id, existingProducto) -> {
			if (existingProducto != null) {
				producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
			}
			return producto;
		});
		
	}

	public HashMap<Integer, Producto> removerProductoDelTicket(int productoID, Producto producto) {
		productosVendidos.remove(productoID, producto);
		return productosVendidos;
	}

	public float calcularValorTotalDelTicket() {		
		return (float) productosVendidos.values().stream().mapToDouble(producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
	}

	@Override
	public String toString() {
		return "Ticket [ID= " + id + ", Date= " + ticketDate +  ", productos= " + productosVendidos + ", Total= "
				+ calcularValorTotalDelTicket() + "]";
	}

}
