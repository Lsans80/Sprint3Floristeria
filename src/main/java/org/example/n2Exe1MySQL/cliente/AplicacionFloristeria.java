package org.example.n2Exe1MySQL.cliente;

import org.example.n2Exe1MySQL.entidad.*;
import org.example.n2Exe1MySQL.excepcion.CantidadExcedida;
import org.example.n2Exe1MySQL.excepcion.ProductoNoExiste;
import org.example.n2Exe1MySQL.herramienta.Input;
import org.example.n2Exe1MySQL.herramienta.Material;

import java.util.HashMap;

public class AplicacionFloristeria {

    public static Floristeria floristeria;
    public static void start (){

        floristeria = Floristeria.getInstancia();
        floristeria.setNombre("Landful");
        //load();
        Menu.ejecutarMenu();
    }

    public static void crearProducto(){
        int opcion = Input.inputInt("1.Añadir cantidad a un producto existente.\n2.Crear un producto.");
        switch (opcion){
            case 1:
                agregarCantidadProducto();
                break;
            case 2:
                agregarProducto();
                break;
        }
    }

    public static void agregarCantidadProducto (){
        int idProducto = Input.inputInt("Id del producto:");
        int cantidad = Input.inputInt("Cantidad a añadir:");
        Producto producto = floristeria.consultarProducto(idProducto);

        if(producto == null){
            System.out.println("El producto no existe.");
        } else {
            floristeria.agregarCantidadProducto(idProducto, producto.getProductoCantidad() + cantidad);
        }
    }

    public static void agregarProducto (){

        int opcion2 = Input.inputInt("Dime que producto deseas crear: \n1.Arbol.\n2.Flor.\n3.Decoración.");

        switch (opcion2){
            case 1:
                floristeria.agregarProducto(crearArbol());
                break;
            case 2:
                floristeria.agregarProducto(crearFlor());
                break;
            case 3:
                floristeria.agregarProducto(crearDecoracion());
                break;
        }

    }

    public static Producto_Arbol crearArbol() {
        String nombre = Input.inputString("Dime el nombre del árbol:");
        float precio = Input.inputFloat("Dime el precio:");
        float altura = Input.inputFloat("Dime la altura:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Arbol(floristeria.consultarSiguienteProductoID(), nombre, precio, altura, cantidad);
    }

    public static Producto_Flor crearFlor() {
        String nombre = Input.inputString("Dime el nombre de la flor:");
        float precio = Input.inputFloat("Dime el precio:");
        String color = Input.inputString("Dime el color:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Flor(floristeria.consultarSiguienteProductoID(), nombre, precio, color, cantidad);
    }

    public static Producto_Decoracion crearDecoracion() {
        String nombre = Input.inputString("Dime el tipo de decoración:");
        float precio = Input.inputFloat("Dime el precio:");
        Material material = Input.inputEnum("Dime el material (madera o plastico)");
        int cantidad = Input.inputInt("Dime la cantidad:");
        return new Producto_Decoracion(floristeria.consultarSiguienteProductoID(), nombre, precio, material, cantidad);
    }

    public static void eliminarProducto (){
        int id = Input.inputInt("ID de producto: ");
        int cantidad = Input.inputInt("Cantidad a retirar: ");
        try {
			floristeria.eliminarProducto(id, cantidad);
		} catch (CantidadExcedida | ProductoNoExiste e) {
			System.out.println(e.getMessage());
		}
    }
    public static void consultarProductos(){
        System.out.println("\nStock por tipo de producto:");
        consultarArbol(floristeria.consultarListaProductosPorTipo("arbol"));
        consultarFlor(floristeria.consultarListaProductosPorTipo("flor"));
        consultarDecoracion(floristeria.consultarListaProductosPorTipo("decoracion"));
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
    public static void consultarValorTotalStock(){
        float valorTotal = floristeria.consultarValorTotalInventario();
        String formattedValue = String.format("%.2f", valorTotal);
        System.out.println("El valor total del stock es de " + formattedValue + " Euros.");
    }

    public static void crearTicket() {
        Ticket ticket = new Ticket(floristeria.consultarSiguienteTicketID());
        agregarProductosTicket(ticket);
        floristeria.agregarTicket(ticket);
    }

    private static void agregarProductosTicket(Ticket ticket) {
        int productoID;
        int cantidadProductoEnTicket;
        boolean si;
        do {
            productoID = Input.inputInt("Id Producto para agregar: ");
            cantidadProductoEnTicket = Input.inputInt("Cantidad: ");
            try {
				if (floristeria.existeProducto(productoID, cantidadProductoEnTicket)) {
				    Producto productoAAgregar = floristeria.consultarProducto(productoID).clonar();
				    productoAAgregar.setProductoCantidad(cantidadProductoEnTicket);
				    ticket.agregarProductoAlTicket(productoAAgregar);
				    try {
						floristeria.eliminarProducto(productoID, cantidadProductoEnTicket);
					} catch (CantidadExcedida e) {
						System.out.println(e.getMessage());
					}
				} else {
				    System.err.println("No existe el producto, o no hay suficiente en stock.");
				}
			} catch (ProductoNoExiste e) {
				System.out.println(e.getMessage());
			}
            si = Input.inputSiNo("Deseas agregar otro producto/ o cambiar cantidad? s/n");
        } while (si);
    }
    
    public static void consultarHistorialTickets() {
    	floristeria.consultarListaTickets().entrySet().forEach(System.out::println);
    }
    
    public static void imprimirValorTotalDeVentas() {
    	System.out.println("El valor total del ventas es de " + floristeria.consultarValorTotalVentas());
    }

    /*public static void finalizar(){
        Floristeria.getInstancia().finalizar();
    }
    
    public static void load(){
        Floristeria.getInstancia().load();
    }*/

}
