package org.example.n1Exe1Txt.entidad;


import org.example.n1Exe1Txt.persistencia.BaseDeDatos;
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
		baseDeDatos.agregarTicket(ticket);
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
/*
	public String crearTicket() {
		int ticketID = agregarTicketVacio();
		String respuesta;
		if (ticketID == -1) {
			respuesta = "No stock.";
		} else {
			int productoID;
			int cantidadProductoEnTicket;
			boolean si;
			do {
				productoID = Input.inputInt("Id Producto para agregar: ");
				cantidadProductoEnTicket = Input.inputInt("Cantidad: ");
				if (existeProducto(productoID) && existeProductoCantidad(productoID)
						&& existeProductoCantidadVsCantidadEnTicket(productoID, cantidadProductoEnTicket)) {
					agregarProductoTicket(productoID, ticketID);
					reducirCantidadStockProducto(productoID, cantidadProductoEnTicket);
					setCantidadProductoTicket(productoID, ticketID, cantidadProductoEnTicket);
				} else {
					System.err.println("No existe el producto, o no hay suficiente en stock.");
				}
				if (!(baseDeDatos.getTotalCantidadStock() == 0)) {
					si = Input.inputSiNo("Deseas agregar otro producto/ o cambiar cantidad? s/n");
				} else {
					si = false;
				}
			} while (si);
			if (baseDeDatos.listarTicketsProductosVendidos(ticketID).isEmpty()) {
				baseDeDatos.eliminarTicket(ticketID);
				respuesta = "Ticket desechado.";
			} else {
				respuesta = "Ticket creado: " + baseDeDatos.leerTicket(ticketID);
			}
		}

		return respuesta;
	}

	private int agregarTicketVacio() {
		if (!(baseDeDatos.getTotalCantidadStock() <= 0)) {
			Ticket ticket = new Ticket(baseDeDatos.getNextTicketId());
			baseDeDatos.agregarTicket(ticket);
			return ticket.getTicketID();
		} else {
			return -1;
		}
	}


	private void agregarProductoTicket(int productoID, int ticketID) {
		baseDeDatos.agregarProductoTicket(productoID, ticketID);
	}
	private void setCantidadProductoTicket(int productoID, int ticketID, int cantidad) {
		baseDeDatos.setCantidadProductoTicket(productoID, ticketID, cantidad);
	}
	private void reducirCantidadStockProducto (int productoID, int cantidad) {
		baseDeDatos.eliminarProducto(productoID, cantidad);
	}
 */
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
	/*
	public boolean existeProductoCantidad(int productoID) {
		return baseDeDatos.existeProductoCantidad(productoID);
	}

	private boolean existeProductoCantidadVsCantidadEnTicket(int productoID, int cantidadProductoEnTicket) {
		return baseDeDatos.existeProductoCantidadVsCantidadEnTicket(productoID, cantidadProductoEnTicket);
	}
	 */
	public void finalizar() {
		baseDeDatos.save();
	}
	public void load() {
		baseDeDatos.load();
	}
}
