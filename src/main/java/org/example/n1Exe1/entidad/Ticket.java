package org.example.n1Exe1.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ticket implements Serializable {

	private int ticketID;
	private List<Producto> productosVendidos;
	private float ticketTotal = 0.0F;
	private int proximoID = 1;

	public Ticket() {
		this.ticketID = proximoID;
		productosVendidos = new ArrayList<>();
		proximoID++;
	}

	public List<Producto> getProductosVendidos() {
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

	public List<Producto> agregarProductoAlTicket(Producto producto) {
		productosVendidos.add(producto);
		return productosVendidos;
	}

	public List<Producto> removerProductoDelTicket(Producto producto) {
		productosVendidos.add(producto);
		return productosVendidos;
	}

	public float calcularValorTotalDelTicket() {
		return ticketTotal = (float) productosVendidos.stream().mapToDouble(Producto::getProductoPrecio).sum();
	}

	@Override
	public String toString() {
		return "Ticket [ID= " + ticketID + ", productos=" + productosVendidos + ", Total="
				+ calcularValorTotalDelTicket() + "]";
	}

}
