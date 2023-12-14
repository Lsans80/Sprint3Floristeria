package org.example.n2Exe1MySQL.persistencia;

public class QueriesSQL {

        public static final String AGREGAR_TICKET = "INSERT INTO ticket (id, fecha) VALUES(?,?)";
        public static final String AGREGAR_PRODUCTO_TICKET =
                "INSERT INTO producto_ticket (ticketId, productoId, cantidad) VALUES(?,?,?)";

        public static final String CONSULTAR_PRODUCTOS_TICKET =
                "SELECT * FROM producto_ticket " +
                "INNER JOIN producto ON producto_ticket.productoId = producto.id " +
                "LEFT JOIN arbol ON producto.id = arbol.id " +
                "LEFT JOIN flor ON producto.id = flor.id " +
                "LEFT JOIN decoracion ON producto.id = decoracion.id " +
                "WHERE producto_ticket.ticketId = ";

        public static final String CONSULTAR_VALOR_TOTAL_TICKETS =
                "SELECT SUM(precio * producto_ticket.cantidad) AS sumaTotal FROM producto_ticket " +
                "INNER JOIN producto ON producto_ticket.productoId = producto.id " +
                "LEFT JOIN arbol ON producto.id = arbol.id " +
                "LEFT JOIN flor ON producto.id = flor.id " +
                "LEFT JOIN decoracion ON producto.id = decoracion.id ";
        public static final String LISTAR_TICKETS = "";

        public static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = (?)";
        public static final String GET_PRODUCTOS = "SELECT * FROM producto " +
                "LEFT JOIN arbol ON producto.id = arbol.id " +
                "LEFT JOIN flor ON producto.id = flor.id " +
                "LEFT JOIN decoracion ON producto.id = decoracion.id ";




}
