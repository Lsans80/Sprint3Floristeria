package org.example.n1Exe1.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ticket implements Serializable {

	private int ticketID;
	private HashMap<Integer, Producto> productosVendidos;
	private float ticketTotal = 0.0F;
	private int proximoID = 1;

	public Ticket() {
		this.ticketID = proximoID;
		productosVendidos = new HashMap<>();
		proximoID++;
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

	public HashMap<Integer, Producto> agregarProductoAlTicket(int productoID, Producto producto) {
		productosVendidos.put(productoID, producto);
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
		return "Ticket [ID= " + ticketID + ", productos=" + productosVendidos + ", Total="
				+ calcularValorTotalDelTicket() + "]";
	}

}
