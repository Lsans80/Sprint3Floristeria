package org.example.n2Exe1MySQL.entidad;


import org.example.n2Exe1MySQL.persistencia.BaseDeDatos;

import java.util.HashMap;

public class Floristeria {
	private static Floristeria instancia = null;
	private String nombre;
	private BaseDeDatos baseDeDatos;

	private Floristeria() {
		this.baseDeDatos = BaseDeDatos.instanciar();
	}

	public static Floristeria getInstancia() {

		if (instancia == null) {

			return new Floristeria();
		}
		return instancia;
	}

	// Getters y Setters.

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BaseDeDatos getBaseDeDatos() {
		return baseDeDatos;
	}

	public void setBaseDeDatos(BaseDeDatos baseDeDatos) {
		this.baseDeDatos = baseDeDatos;
	}

	// Métodos propios.

	public void addProducto(Producto producto) {
		baseDeDatos.agregarProducto(producto);
	}
	
	public void addTicket(Ticket ticket) {
		baseDeDatos.agregarTicketDb(ticket);
	}

	public Producto buscarProducto(int productoId) {
		return baseDeDatos.leerProducto(productoId);
	}
	
	public int nextProductoID () {
		return baseDeDatos.getNextProductoId();
	}
	public int nextTicketID () {
		return baseDeDatos.getNextTicketId();
	}

	public String eliminarProducto(int productoID, int cantidad) {
		String response;
		if (existeProducto(productoID)){
		Producto productoEliminado = baseDeDatos.eliminarProducto(productoID, cantidad);
			response = productoEliminado + " ha sido eliminado.";
		} else {
			response = "El producto no se ha encontrado.";
		}
		return response;
	}

	public HashMap<Integer, Producto> getListaProductos() {
		return baseDeDatos.getProductos();
	}

	public HashMap<Integer, Producto> getListaProductosPorTipo (String tipo){
		return baseDeDatos.listarProductosFiltrando(producto -> producto.getProductoTipo().equalsIgnoreCase(tipo));
	}

	public HashMap<Integer, Ticket> getListaTickets() {
		return baseDeDatos.getTickets();
	}

	public float valorTotal() {
		return baseDeDatos.getValorTotalStock();
	}
	public float valorVentas() {
		return baseDeDatos.getValorTotalTickets();
	}
	public boolean existeProducto(int productoID) {
		return baseDeDatos.getProductos().containsKey(productoID) &&
				baseDeDatos.getProductos().get(productoID).getProductoCantidad() > 0;
	}
	public boolean existeProducto(int productoID, int cantidad) {
		return baseDeDatos.getProductos().containsKey(productoID) &&
				baseDeDatos.getProductos().get(productoID).getProductoCantidad() > cantidad;
	}
	public void finalizar() {
		baseDeDatos.save();
	}
	public void load() {
		baseDeDatos.load();
	}
}
