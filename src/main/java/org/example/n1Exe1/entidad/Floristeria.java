package org.example.n1Exe1.entidad;


import org.example.n1Exe1.herramienta.Input;
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

	public void eliminarProducto(int productoID, int cantidad) {

		Producto productoEliminado = baseDeDatos.eliminarProducto(productoID, cantidad);
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

	public HashMap<Integer, Producto> getListaProductosPorTipo (String tipo){

		return baseDeDatos.listarProductosFiltrando(producto -> producto.getProductoTipo().equalsIgnoreCase(tipo));
	}

	public void getListaProductosCantidad() { //TODO

		baseDeDatos.getStock();
	}

	public int agregarTicket() {
		Ticket ticket = new Ticket();
		baseDeDatos.agregarTicket(ticket);
		return ticket.getTicketID();
	}

	public void agregarProductoTicket(int productoId, int ticketID, int cantidad) {
		baseDeDatos.leerTicket(ticketID).agregarProductoAlTicket(productoId, baseDeDatos.leerProducto(productoId));
		
	}
	
	public void setCantidadProductoTicket(int productoId, int ticketID, int cantidad) {
		baseDeDatos.leerTicket(ticketID).getProductosVendidos().get(productoId).setProductoCantidad(cantidad);
	}
	
	public void reducirCantidadStockProducto (int productoId, int cantidad) {
		baseDeDatos.reducirCantidadProducto(productoId, cantidad);
	}
	
	public Ticket crearTicket() {
		int ticketID = agregarTicket();
		int productID;
		int cantidad;
		boolean si;
		do {
			productID = Input.inputInt("Id Producto para agregar: ");
			cantidad = Input.inputInt("Cantidad: ");
			if (existeProducto(productID)){
				agregarProductoTicket(productID, ticketID, cantidad);
				reducirCantidadStockProducto(productID, cantidad);
				setCantidadProductoTicket(productID, ticketID, cantidad);
			} else {
				System.err.println("No existe el producto");
			}
			si = Input.inputSiNo("Deseas agregar otro producto? s/n");
		} while (si || baseDeDatos.leerTicket(ticketID).getProductosVendidos().isEmpty()); //TODO

		return baseDeDatos.leerTicket(ticketID);
	}

	public HashMap<Integer, Ticket> getListaTickets() {
		return baseDeDatos.listarTickets();
	}

	public float valorTotal() {
		return baseDeDatos.getValorTotalStock();

	}
	
	public float valorVentas() {
		return baseDeDatos.getValorTotalTickets();

	}
	
	public boolean existeProducto(int productoID) {
		return baseDeDatos.listarProductos().containsKey(productoID) && 
				baseDeDatos.listarProductos().get(productoID).getProductoCantidad() > 0;
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
