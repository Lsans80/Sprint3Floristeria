package org.example.n2Exe1MySQL.persistencia;

public class QueriesSQL {

        public static final String AGREGAR_TICKET = "INSERT INTO ticket (id, fecha) VALUES(?,?)";
        public static final String AGREGAR_PRODUCTO_TICKET = "INSERT INTO producto_ticket (ticketId, productoId, cantidad) VALUES(?,?,?)";
        public static final String LISTAR_TICKETS = "";

        public static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = (?)";


}
