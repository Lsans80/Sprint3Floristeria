package org.example.n1Exe1.cliente;

import org.example.n1Exe1.entidad.Floristeria;
import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Producto_Arbol;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AplicacionFloristeriaTest {

    static Floristeria floristeria;

    @Test
    public void crearArbolTest (){

        floristeria = Floristeria.getInstancia();

        Producto arbol = new Producto_Arbol("pino", 5,3,2);
        HashMap<Integer, Producto> productos = new HashMap<>();
        productos.put(arbol.getProductoID(),arbol);
        assertEquals(arbol, productos.get(arbol.getProductoID()));

    }

}
