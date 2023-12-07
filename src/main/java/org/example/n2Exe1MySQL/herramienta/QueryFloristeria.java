package org.example.n2Exe1MySQL.herramienta;

public class QueryFloristeria {
	
	public static final String AGREGAR_TICKET = "INSERT INTO ticket (id) VALUES(?)";
	public static final String AGREGAR_PRODUCTO_TICKET = "INSERT INTO producto_ticket (ticketId, productoId, cantidad) VALUES(?,?,?)";
	public static final String LISTAR_TICKETS = "";

}
