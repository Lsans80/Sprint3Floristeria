package org.example.n2Exe1MySQL.entidad;


import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;
import org.example.n2Exe1MySQL.excepcion.ProductoNoExiste;
import org.example.n2Exe1MySQL.persistencia.InterfaceBaseDeDatos;
import org.example.n2Exe1MySQL.persistencia.MongoDB;
import org.example.n2Exe1MySQL.persistencia.MySQLDB;

import java.util.HashMap;

public class Floristeria {
	private static Floristeria instancia = null;
	private String nombre;
	private InterfaceBaseDeDatos baseDeDatos;

	private Floristeria() {
		this.baseDeDatos = MySQLDB.instanciar();
		nombre = "Landful";
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

	public InterfaceBaseDeDatos getBaseDeDatos() {
		return baseDeDatos;
	}

	public void setBaseDeDatos(InterfaceBaseDeDatos baseDeDatos) {
		this.baseDeDatos = baseDeDatos;
	}

	// Métodos propios.

	public void agregarProducto(Producto producto) {
		baseDeDatos.agregarProducto(producto);
	}

	public void agregarCantidadProducto(int id, int nuevaCantidad){
		baseDeDatos.actualizarCantidadProducto(id, nuevaCantidad);
	}
	public void agregarTicket(Ticket ticket) {
		baseDeDatos.agregarTicket(ticket);
	}

	public Producto consultarProducto(int productoId) {
		return baseDeDatos.consultarProducto(productoId);
	}
	
	public int consultarSiguienteProductoID() {
		return baseDeDatos.obtenerSiguienteProductoId();
	}
	public int consultarSiguienteTicketID() {
		return baseDeDatos.obtenerSiguienteTicketId();
	}

	public String eliminarProducto(int productoID, int cantidad) throws CantidadExcedida, ProductoNoExiste {
		String response;
		if (existeProducto(productoID, 0)){
		Producto productoEliminado = baseDeDatos.eliminarProducto(productoID, cantidad);
			response = productoEliminado + " ha sido eliminado.";
		} else {
			throw new ProductoNoExiste("El id de producto no existe en inventario. Consulta la lista de productos disponibles.");
		}
		return response;
	}
	public HashMap<Integer, Producto> consultarListaProductosPorTipo(String tipo){
		return baseDeDatos.consultarProductosFiltrando(tipo);
	}
	public HashMap<Integer, Ticket> consultarListaTickets() {
		return baseDeDatos.consultarTickets();
	}
	public float consultarValorTotalInventario() {
		return baseDeDatos.consultarValorTotalStock();
	}
	public float consultarValorTotalVentas() {
		return baseDeDatos.consultarValorTotalTickets();
	}
	public boolean existeProducto(int productoID, int cantidadMinima) throws ProductoNoExiste {
		boolean returnValue;
		Producto producto = baseDeDatos.consultarProducto(productoID);
		if (producto != null) {
			returnValue = producto.getProductoCantidad() > cantidadMinima;
		} else {
			throw new ProductoNoExiste("El id de producto no existe en inventario. Consulta la lista de productos disponibles.");
		}
		return returnValue;
	}

}
