package org.example.n1Exe1.entidad;

import org.example.n1Exe1.entidad.Producto;
import org.example.n1Exe1.entidad.Producto_Arbol;
import org.example.n1Exe1.entidad.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Floristeria {

    private static Floristeria instancia = null;
    private String nombre;
    private float valorTotalStock;
    private List<Producto> productos;
    private List<Ticket> tickets;

    private Floristeria(String nombre, float valorTotalStock) {
        this.nombre = nombre;
        this.valorTotalStock = valorTotalStock;
        this.productos = new ArrayList<Producto>();
        this.tickets = new ArrayList<Ticket>();
    }

    public static Floristeria getInstancia (String nombre, float valorTotalStock){

        if (instancia == null){

            return new Floristeria(nombre,valorTotalStock);
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

    public float getValorTotalStock() {
        return valorTotalStock;
    }

    public void setValorTotalStock(float valorTotalStock) {
        this.valorTotalStock = valorTotalStock;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    //Métodos propios.

    public void addProducto (Producto producto){
        productos.add(producto);
    }
    
    public Producto buscarProducto (int productoId, List<Producto> productos){ //Busqueda por id.

        Producto productoBuscado = null;

        for (Producto p: productos){

            if(p.getProductoID() == productoId){
                productoBuscado = (Producto) p;
            }
        }
        return productoBuscado;
    }

    public void eliminarProducto (int productoId){

        Producto productoEliminado = buscarProducto(productoId, productos);

        if (productoEliminado == null){
            System.out.println("El producto no se ha encontrado.");

        } else {
            System.out.println(productoEliminado + " ha sido eliminado.");
            productos.remove(productoEliminado);

        }

    }

    public void valorTotal (List<Producto> productos){

        for (Producto p : productos){

            valorTotalStock += p.getProductoPrecio();
        }

    }

}
