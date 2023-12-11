package org.example.n2Exe1MySQL.persistencia;

public class QueriesSQL {

        public static final String AGREGAR_TICKET = "INSERT INTO ticket (id, fecha) VALUES(?,?)";
        public static final String AGREGAR_PRODUCTO_TICKET = "INSERT INTO producto_ticket (ticketId, productoId, cantidad) VALUES(?,?,?)";
        public static final String LISTAR_TICKETS = "SELECT t.id, t.fecha, p.nombre, pt.cantidad from t3n2Floristeria.ticket t left join t3n2Floristeria.producto_ticket pt on t.id = pt.ticketid left join t3n2Floristeria.producto p on pt.productoid = p.id;";

        

}
