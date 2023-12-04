package cliente;

import org.example.n1Exe1.entidad.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.example.n1Exe1.herramienta.Material.PLASTICO;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AplicacionFloristeriaTest {

    static Floristeria floristeria = Floristeria.getInstancia();
    static HashMap<Integer, Producto> productos = new HashMap<>();
    //La Key de productos es la misma que producto.getProductoID().

    @Test
    @DisplayName("Verificar crear arbol")
    public void crearArbolTest() {

        Producto arbol = new Producto_Arbol(floristeria.nextProductoID(),"pino", 5, 3, 2);
        productos.put(arbol.getProductoID(), arbol);
        assertEquals(arbol, productos.get(arbol.getProductoID()));
    }

    @Test
    @DisplayName("Verificar crear flor")
    public void crearFlorTest() {

        Producto flor = new Producto_Flor(floristeria.nextProductoID(), "gardenia", 5, "rojo", 2);
        productos.put(flor.getProductoID(), flor);
        assertEquals(flor, productos.get(flor.getProductoID()));
    }

    @Test
    @DisplayName("Verificar crear decoración")
    public void crearDecoracionTest() {

        Producto decoracion = new Producto_Decoracion(floristeria.nextProductoID(),"silla", 5, PLASTICO, 2);
        productos.put(decoracion.getProductoID(), decoracion);
        assertEquals(decoracion, productos.get(decoracion.getProductoID()));
    }

}

