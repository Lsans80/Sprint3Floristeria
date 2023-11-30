package org.example.n1Exe1.cliente;

import org.example.n1Exe1.herramienta.*;
import org.example.n1Exe1.entidad.*;
import org.example.n1Exe1.persistencia.BaseDeDatos;
import java.util.*;


public class AplicacionFloristeria {

    static Scanner sc = new Scanner(System.in);

    static BaseDeDatos baseDeDatos = BaseDeDatos.instanciar();

    static Floristeria floristeria;
    

    public static void start (){

        floristeria = Floristeria.getInstancia();
        floristeria.setNombre("FeetLand");
        load();
        
        boolean salir = false;

        do{
            switch(menu()){
                case 1: crearProducto();
                    break;
                case 2: eliminarProducto(Input.inputInt("ID de producto: "), Input.inputInt("Cantidad a retirar: "));
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
            System.out.println("\n****FeetLand****");
            System.out.println("1. Agregar producto.");
            System.out.println("2. Eliminar producto.");
            System.out.println("3. Listar productos.");
            System.out.println("4. Consultar valor stock total.");
            System.out.println("5. Crear Ticket.");
            System.out.println("6. Lista historial Tickets.");
            System.out.println("7. Totalizar ventas.");
            System.out.println("0. Salir de la aplicación.\n");
            opcion = sc.nextByte();
            sc.nextLine();

            if(opcion < MINIMO || opcion > MAXIMO){
                System.out.println("Escoge una opción válida.");
            }

        }while(opcion < MINIMO || opcion > MAXIMO);

        return opcion;
    }

    public static void crearProducto(){

        int opcion = Input.inputInt("Dime que producto deseas crear: \n1.Arbol.\n2.Flor.\n3.Decoración.");

        switch (opcion){
            case 1:
                crearArbol();
                break;

            case 2:
                crearFlor();
                break;

            case 3:
                crearDecoracion();
                break;
        }
    }

    public static void crearArbol(){

        String nombre = Input.inputString("Dime el nombre del arbol:");
        float precio = Input.inputFloat("Dime el precio;");
        float altura = Input.inputFloat("Dime la altura:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        Producto_Arbol arbol = new Producto_Arbol (floristeria.nextProductoID(), nombre, precio, altura,cantidad);
        floristeria.addProducto(arbol);
    }

    public static void crearFlor (){

        String nombre = Input.inputString("Dime el nombre de la flor:");
        float precio = Input.inputFloat("Dime el precio;");
        String color = Input.inputString("Dime el color:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        Producto_Flor flor = new Producto_Flor(floristeria.nextProductoID(), nombre, precio, color, cantidad);
        floristeria.addProducto(flor);
    }

    public static void crearDecoracion (){

        Floristeria.getInstancia();

        String nombre = Input.inputString("Dime el tipo de decoración:");
        float precio = Input.inputFloat("Dime el precio;");
        String material = Input.inputString("Dime el material (madera o plastico)");
        int cantidad = Input.inputInt("Dime la cantidad:");

        Material decoracionMaterial = Material.valueOf(material.toUpperCase());

        Producto_Decoracion decoracion = new Producto_Decoracion(floristeria.nextProductoID(), nombre, precio, decoracionMaterial, cantidad);
        floristeria.addProducto(decoracion);
    }

    public static void eliminarProducto (int id, int cantidad){

        floristeria.eliminarProducto(id, cantidad);
    }

    public static void listarProductos(){

        System.out.println("\nStock por tipo de producto:");
        consultarArbol(floristeria.getListaProductosPorTipo("arbol"));
        consultarFlor(floristeria.getListaProductosPorTipo("flor"));
        consultarDecoracion(floristeria.getListaProductosPorTipo("decoracion"));

    }

    public static void consultarArbol (HashMap<Integer, Producto> stockArbol){

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

    public static void consultarFlor (HashMap<Integer, Producto> stockFlor){

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

    public static void consultarDecoracion (HashMap<Integer,Producto> stockDecoracion){

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
    
    public static void crearTicket () {
    	
		System.out.println(floristeria.crearTicket());
 
    }
    
    public static void listarHistorialTickets () {
    	
    	floristeria.getListaTickets().entrySet().forEach(ticket-> System.out.println(ticket));
 
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
