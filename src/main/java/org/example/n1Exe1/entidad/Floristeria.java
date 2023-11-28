package org.example.n1Exe1.entidad;


import org.example.n1Exe1.persistencia.BaseDeDatos;


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

	public Producto buscarProducto(int productoId) {

		Producto productoBuscado = baseDeDatos.leerProducto(productoId);

		return productoBuscado;
	}

	public void eliminarProducto(int productoId) {

		Producto productoEliminado = baseDeDatos.eliminarProducto(productoId);
		if (productoEliminado == null) {
			System.out.println("El producto no se ha encontrado.");
		} else {
			System.out.println(productoEliminado + " ha sido eliminado.");
		}

	}

	public HashMap<Integer, Producto> getListaProductos() {

		HashMap<Integer, Producto> stock = baseDeDatos.listarProductos();
		return stock;
	}

	public void getListaProductosCantidad() { //TODO

		baseDeDatos.getStock();
	}

	public Ticket agregarTicket() {

		return baseDeDatos.agregarTicket(new Ticket());
	}

	public void agregarProductoTicket(int productoId, int ticketID) {
		baseDeDatos.getTickets().get(ticketID).agregarProductoAlTicket(baseDeDatos.getStock().get(productoId));
	}

	public HashMap<Integer, Ticket> getTicket() {
		return baseDeDatos.getTickets();
	}

	public void printTicketsHistory() {
		System.out.println(baseDeDatos.getTickets());
	}

	public float valorTotal() {
		return baseDeDatos.getValorTotalStock();

	}

	public void finalizar() {
		baseDeDatos.save();
		// baseDeDatos.saveTickets(); TODO
	}

	public void load() {
		baseDeDatos.load();
		// baseDeDatos.loadTickets(); TODO
	}

}
