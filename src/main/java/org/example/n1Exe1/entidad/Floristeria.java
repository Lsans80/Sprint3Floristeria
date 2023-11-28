package org.example.n1Exe1.entidad;

import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Producto_Arbol;
import org.example.n1Exe1.entidad.Ticket;
import org.example.n1Exe1.persistencia.BaseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class Floristeria {

    private static Floristeria instancia = null;
    private String nombre;

    //private List<Producto> productos;
    //private List<Ticket> tickets;
    private BaseDeDatos baseDeDatos;

    private Floristeria() {
        this.baseDeDatos = BaseDeDatos.instanciar();
        //this.productos = new ArrayList<Producto>();
        //this.tickets = new ArrayList<Ticket>();
    }

    public static Floristeria getInstancia (){

        if (instancia == null){

            return new Floristeria();
        }
        return instancia;
    }

    //Getters y Setters.

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

    //Métodos propios.

    public void addProducto (Producto producto){
        baseDeDatos.agregarProducto(producto);
    }
    
    public Producto buscarProducto (int productoId){ //Busqueda por id.

        Producto productoBuscado = baseDeDatos.leerProducto(productoId);;

        return productoBuscado;
    }

    public void eliminarProducto (int productoId){

        /*Producto productoEliminado = baseDeDatos.eliminarProducto(productoId);

        if (productoEliminado == null){
            System.out.println("El producto no se ha encontrado.");

        } else {
            System.out.println(productoEliminado + " ha sido eliminado.");
            productos.remove(productoEliminado);

        }*/

    }

    public void getListaProductos (){

        //HashMap<Integer, Producto> stock = baseDeDatos.listarProductos();
    }

    public void getListaProductosCantidad(){

        //
    }

    public float valorTotal (){

        return baseDeDatos.getValorTotalStock();

    }
    public void finalizar(){

        //baseDeDatos.save();
    }

}
