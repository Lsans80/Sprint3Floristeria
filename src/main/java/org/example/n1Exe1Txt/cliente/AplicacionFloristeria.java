package org.example.n1Exe1Txt.cliente;

import org.example.n1Exe1Txt.herramienta.*;
import org.example.n1Exe1Txt.entidad.*;
import java.util.*;


public class AplicacionFloristeria {
    private static Floristeria floristeria;
    public static void start (){

        floristeria = Floristeria.getInstancia();
        floristeria.setNombre("Landful");
        load();

        boolean salir = false;

        do{
            switch(menu()){
                case 1: crearProducto();
                    break;
                case 2: eliminarProducto();
                    break;
                case 3: listarProductos();
                    break;
                case 4: valorTotalStock();
                    break;
                case 5: crearTicket();
                	break;
                case 6: listarHistorialTickets();
            		break;
                case 7: imprimirValorTotalDeVentas();
            		break;
                case 0: System.out.println("Gracias por utilizar nuestra floristería.");
                    salir = true;
                    break;
            }
        }while(!salir);
    }
    public static byte menu(){
        byte opcion;
        final byte MINIMO = 0;
        final byte MAXIMO = 7;

        do{
            opcion = Input.inputByte("\n****" + floristeria.getNombre().toUpperCase() + "****\n"
                                            + "\n1. Agregar producto."
                                            + "\n2. Eliminar producto."
                                            + "\n3. Listar productos."
                                            + "\n4. Consultar valor stock total."
                                            + "\n5. Crear Ticket."
                                            + "\n6. Lista historial Tickets."
                                            + "\n7. Totalizar ventas."
                                            + "\n0. Salir de la aplicación.\n");

            if(opcion < MINIMO || opcion > MAXIMO){
                System.err.println("Escoge una opción válida.");
            }

        }while(opcion < MINIMO || opcion > MAXIMO);

        return opcion;
    }

    public static void crearProducto(){
        int opcion = Input.inputInt("Dime que producto deseas crear: \n1.Arbol.\n2.Flor.\n3.Decoración.");
        switch (opcion){
            case 1:
                floristeria.addProducto(crearArbol());
                break;
            case 2:
                floristeria.addProducto(crearFlor());
                break;
            case 3:
                floristeria.addProducto(crearDecoracion());
                break;
        }
    }

    public static Producto_Arbol crearArbol() {
        String nombre = Input.inputString("Dime el nombre del árbol:");
        float precio = Input.inputFloat("Dime el precio:");
        float altura = Input.inputFloat("Dime la altura:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Arbol(floristeria.nextProductoID(), nombre, precio, altura, cantidad);
    }

    public static Producto_Flor crearFlor() {
        String nombre = Input.inputString("Dime el nombre de la flor:");
        float precio = Input.inputFloat("Dime el precio:");
        String color = Input.inputString("Dime el color:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Flor(floristeria.nextProductoID(), nombre, precio, color, cantidad);
    }

    public static Producto_Decoracion crearDecoracion() {
        String nombre = Input.inputString("Dime el tipo de decoración:");
        float precio = Input.inputFloat("Dime el precio:");
        Material material = Input.inputEnum("Dime el material (madera o plastico)");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Decoracion(floristeria.nextProductoID(), nombre, precio, material, cantidad);
    }

    public static void eliminarProducto (){
        int id = Input.inputInt("ID de producto: ");
        int cantidad = Input.inputInt("Cantidad a retirar: ");
        floristeria.eliminarProducto(id, cantidad);
    }
    public static void listarProductos(){
        System.out.println("\nStock por tipo de producto:");
        consultarArbol(floristeria.getListaProductosPorTipo("arbol"));
        consultarFlor(floristeria.getListaProductosPorTipo("flor"));
        consultarDecoracion(floristeria.getListaProductosPorTipo("decoracion"));
    }
    private static void consultarArbol (HashMap<Integer, Producto> stockArbol){
        System.out.println("***ARBOL***:\n");
        stockArbol.values().forEach(producto -> {
                Producto_Arbol productoArbol = (Producto_Arbol) producto;
                System.out.println("ID: " + productoArbol.getProductoID()
                        + " | Cantidad: " + productoArbol.getProductoCantidad()
                        + " | Nombre: " + productoArbol.getProductoNombre()
                        + " | Altura: " + productoArbol.getArbolAltura()
                        + " | Precio: " + productoArbol.getProductoPrecio());

        });
    }
    private static void consultarFlor (HashMap<Integer, Producto> stockFlor){
        System.out.println("\n***FLOR***:\n");
        stockFlor.values().forEach(producto -> {
                Producto_Flor productoFlor = (Producto_Flor) producto;
                System.out.println("ID: " + productoFlor.getProductoID()
                        + " | Cantidad: " + productoFlor.getProductoCantidad()
                        + " | Nombre: " + productoFlor.getProductoNombre()
                        + " | Color: " + productoFlor.getFlorColor()
                        + " | Precio: " + productoFlor.getProductoPrecio());
        });
    }
    private static void consultarDecoracion (HashMap<Integer,Producto> stockDecoracion){
        System.out.println("\n***DECORACION***:\n");
        stockDecoracion.values().forEach(producto -> {
                Producto_Decoracion productoDecoracion = (Producto_Decoracion) producto;
                System.out.println("ID: " + productoDecoracion.getProductoID()
                        + " | Cantidad: " + productoDecoracion.getProductoCantidad()
                        + " | Nombre: " + productoDecoracion.getProductoNombre()
                        + " | Material: " + productoDecoracion.getDecoracionMaterial()
                        + " | Precio: " + productoDecoracion.getProductoPrecio());

        });
    }
    public static void valorTotalStock (){
        float valorTotal = floristeria.valorTotal();
        String formattedValue = String.format("%.2f", valorTotal);
        System.out.println("El valor total del stock es de " + formattedValue + " Euros.");
    }

    public static void crearTicket() {
        Ticket ticket = new Ticket(floristeria.nextTicketID());
        int productoID;
        int cantidadProductoEnTicket;
        boolean si;
        do {
            productoID = Input.inputInt("Id Producto para agregar: ");
            cantidadProductoEnTicket = Input.inputInt("Cantidad: ");
            if (floristeria.existeProducto(productoID, cantidadProductoEnTicket)) {
                Producto productoAAgregar = floristeria.buscarProducto(productoID).clonar();
                productoAAgregar.setProductoCantidad(cantidadProductoEnTicket);
                ticket.agregarProductoAlTicket(productoAAgregar);
                floristeria.eliminarProducto(productoID, cantidadProductoEnTicket);
                floristeria.addTicket(ticket);
            } else {
                System.err.println("No existe el producto, o no hay suficiente en stock.");
            }
            si = Input.inputSiNo("Deseas agregar otro producto/ o cambiar cantidad? s/n");
        } while (si);
    }
    
    public static void listarHistorialTickets () {
    	floristeria.getListaTickets().entrySet().forEach(System.out::println);
    }
    
    public static void imprimirValorTotalDeVentas() {
    	System.out.println("El valor total del ventas es de " + floristeria.valorVentas());
    }

    public static void finalizar(){
        Floristeria.getInstancia().finalizar();
    }
    
    public static void load(){
        Floristeria.getInstancia().load();
    }

}
