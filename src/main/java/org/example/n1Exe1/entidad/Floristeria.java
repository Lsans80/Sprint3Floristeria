package org.example.n1Exe1.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Floristeria {
    Scanner sc = new Scanner(System.in);

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

    public Floristeria instanciar (String nombre, float valorTotalStock){

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
    
    public Producto_Arbol buscarArbol (){ //Busqueda por nombre por ahora.

        System.out.println("Dime el nombre del arbol que quieres buscar:");
        String nombreArbol = sc.nextLine();

        //Input.inputString("Dime el nombre del arbol que quieres buscar:");

        Producto_Arbol arbolBuscado = null;

        for (Producto p: productos){

            if(p instanceof Producto_Arbol && p.getProductoNombre().equalsIgnoreCase(nombreArbol)){
                arbolBuscado = (Producto_Arbol) p;
            }
        }
        return arbolBuscado;
    }

    public Producto_Arbol agregarArbol (){

        System.out.println("Nombre del arbol:");
        String nombre = sc.nextLine();
        System.out.println("Precio del arbol:");
        float precio = sc.nextFloat();
        System.out.println("Altura del arbol:");
        float altura = sc.nextInt();
        sc.nextLine();

        Producto_Arbol arbol = new Producto_Arbol (nombre,precio,altura, 1);
        productos.add(arbol);
        System.out.println(arbol + " agregado a la floristeria.");
        //PersistanceManager.agregarTxt(arbol);

        return arbol;
    }

    public void eliminarArbol (){

        Producto_Arbol arbolBuscado = buscarArbol();

        if (arbolBuscado == null){
            System.out.println("El arbol no se ha encontrado.");

        } else {
            System.out.println(arbolBuscado + " ha sido eliminado.");
            productos.remove(arbolBuscado);

        }

    }


}
