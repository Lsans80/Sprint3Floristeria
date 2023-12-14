package cliente;

import org.example.n1Exe1Txt.entidad.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.example.n1Exe1Txt.herramienta.Material.PLASTICO;
import static org.junit.jupiter.api.Assertions.*;

public class AplicacionFloristeriaTest {

    Floristeria floristeria = Floristeria.getInstancia();
    HashMap<Integer, Producto> productos = new HashMap<>();
    //La Key de productos es la misma que producto.getProductoID().

    @Test
    @DisplayName("Verificar crear arbol")
    public void crearArbolTest() {

        Producto arbol = new Producto_Arbol(floristeria.nextProductoID(),"pino", 3, 3, 3);
        floristeria.addProducto(arbol);
        assertTrue(floristeria.getListaProductos().containsValue(arbol));
    }

    @Test
    @DisplayName("Verificar crear flor")
    public void crearFlorTest() {

        Producto flor = new Producto_Flor(floristeria.nextProductoID(), "gardenia", 5, "rojo", 2);
        floristeria.addProducto(flor);
        assertTrue(floristeria.getListaProductos().containsValue(flor));
    }

    @Test
    @DisplayName("Verificar crear decoración")
    public void crearDecoracionTest() {

        Producto decoracion = new Producto_Decoracion(floristeria.nextProductoID(),"silla", 5, PLASTICO, 2);
        floristeria.addProducto(decoracion);
        assertTrue(floristeria.getListaProductos().containsValue(decoracion));
    }

    @Test
    @DisplayName("Verificar valorTotalStock")
    public void getValorTotalStockTest (){
        double valorTotalStock = productos.values().stream().mapToDouble(
                producto -> producto.getProductoPrecio() * producto.getProductoCantidad()).sum();
        assertEquals(valorTotalStock,floristeria.valorTotal());
    }

}

