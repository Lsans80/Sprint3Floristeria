package org.example.n3Exe1Mongo.entidad;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ticketID;
	private LocalDate ticketDate;
	private HashMap<Integer, Producto> productosVendidos;
	private float ticketTotal = 0.0F;
	//private static int proximoID = 1;

	public Ticket(int ticketID) {
		this.ticketID = ticketID;
		ticketDate = LocalDate.now();
		productosVendidos = new HashMap<>();
		ticketTotal = calcularValorTotalDelTicket();
		//proximoID++;
	}

	public LocalDate getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(LocalDate ticketDate) {
		this.ticketDate = ticketDate;
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
		return ticketID;
	}

	public HashMap<Integer, Producto> agregarProductoAlTicket(Producto producto) {
		productosVendidos.compute(producto.getProductoID(), (id, existingProducto) -> {
			if (existingProducto != null) {
				producto.setProductoCantidad(producto.getProductoCantidad() + existingProducto.getProductoCantidad());
			}
			return producto;
		});
		//productosVendidos.put(productoID, producto);
		return productosVendidos;
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
		return "Ticket [ID= " + ticketID + ", Date= " + ticketDate +  ", productos= " + productosVendidos + ", Total= "
				+ calcularValorTotalDelTicket() + "]";
	}

}
