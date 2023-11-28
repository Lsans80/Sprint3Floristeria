package org.example.n1Exe1;

import org.example.n1Exe1.herramienta.*;
import org.example.n1Exe1.entidad.*;
import org.example.n1Exe1.persistencia.BaseDeDatos;
import java.util.*;


public class AplicacionFloristeria {

    static Scanner sc = new Scanner(System.in);

    static BaseDeDatos baseDeDatos = BaseDeDatos.instanciar();

    public static void start (){

        Floristeria floristeria = Floristeria.getInstancia();
        floristeria.setNombre("FeetLand");
        load();
        
        boolean salir = false;

        do{
            switch(menu()){
                case 1: crearProducto(floristeria);
                    break;
                case 2: eliminarProducto(floristeria);
                    break;
                case 3: listarProductos(floristeria);
                    break;
                case 4: valorTotalStock(floristeria);
                    break;
                case 5: System.out.println(crearTicket(floristeria));
                	break;
                case 6: floristeria.printTicketsHistory();
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
        final byte MAXIMO = 6;

        do{
            System.out.println("\n****FeetLand****");
            System.out.println("1. Agregar producto.");
            System.out.println("2. Eliminar producto.");
            System.out.println("3. Listar productos.");
            System.out.println("4. Consultar valor stock total.");
            System.out.println("5. Crear Ticket.");
            System.out.println("6. Lista historial Tickets.");
            System.out.println("0. Salir de la aplicación.\n");
            opcion = sc.nextByte();
            sc.nextLine();

            if(opcion < MINIMO || opcion > MAXIMO){
                System.out.println("Escoge una opción válida.");
            }

        }while(opcion < MINIMO || opcion > MAXIMO);

        return opcion;
    }

    public static void crearProducto(Floristeria floristeria){

        int opcion = Input.inputInt("Dime que producto deseas crear: \n1.Arbol.\n2.Flor.\n3.Decoración.");

        switch (opcion){
            case 1:
                crearArbol(floristeria);
                break;

            case 2:
                crearFlor(floristeria);
                break;

            case 3:
                crearDecoracion(floristeria);
                break;
        }
    }

    public static void crearArbol(Floristeria floristeria){

        String nombre = Input.inputString("Dime el nombre del arbol:");
        float precio = Input.inputFloat("Dime el precio;");
        float altura = Input.inputFloat("Dime la altura:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        Producto_Arbol arbol = new Producto_Arbol (nombre,precio,altura,cantidad);
        floristeria.addProducto(arbol);
    }

    public static void crearFlor (Floristeria floristeria){

        String nombre = Input.inputString("Dime el nombre de la flor:");
        float precio = Input.inputFloat("Dime el precio;");
        String color = Input.inputString("Dime el color:");
        int cantidad = Input.inputInt("Dime la cantidad:");
        Producto_Flor flor = new Producto_Flor(nombre,precio,color,cantidad);
        floristeria.addProducto(flor);
    }

    public static void crearDecoracion (Floristeria floristeria){

        String nombre = Input.inputString("Dime el tipo de decoración:");
        float precio = Input.inputFloat("Dime el precio;");
        String material = Input.inputString("Dime el material (madera o plastico)");
        int cantidad = Input.inputInt("Dime la cantidad:");

        Material decoracionMaterial = Material.valueOf(material.toUpperCase());

        Producto_Decoracion decoracion = new Producto_Decoracion(nombre,precio,decoracionMaterial,cantidad);
        floristeria.addProducto(decoracion);
    }

    public static void eliminarProducto(Floristeria floristeria){

        int id = Input.inputInt("Introduce el número del ID de producto que quieres eliminar:");
        floristeria.eliminarProducto(id);
    }

    public static void listarProductos(Floristeria floristeria){

        HashMap<Integer, Producto> stockArbol = baseDeDatos.listarProductosFiltrando(
                producto -> producto.getProductoTipo().equalsIgnoreCase("Arbol"));
        HashMap<Integer, Producto> stockFlor = baseDeDatos.listarProductosFiltrando(
                producto -> producto.getProductoTipo().equalsIgnoreCase("Flor"));
        HashMap<Integer, Producto> stockDecoracion = baseDeDatos.listarProductosFiltrando(
                producto -> producto.getProductoTipo().equalsIgnoreCase("Decoracion"));

        System.out.println("\nStock por tipo de producto:");
        consultarArbol(stockArbol);
        consultarFlor(stockFlor);
        consultarDecoracion(stockDecoracion);

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

    public static void valorTotalStock (Floristeria floristeria){

        float valorTotal = floristeria.valorTotal();

        String formattedValue = String.format("%.2f", valorTotal);

        System.out.println("El valor total del stock es de " + formattedValue + " Euros.");
    }
    
    public static Ticket crearTicket (Floristeria floristeria) {
    	String stop;
    	floristeria.agregarTicket();
    	Ticket ticket = floristeria.getTicket().get(floristeria.getTicket().size());
    	do {
    		floristeria.agregarProductoTicket(Input.inputInt("Id Producto: "), 
    			ticket.getTicketID());
    		stop = Input.inputString("Deseas agregar otro producto? ");	
    	} while (stop.equalsIgnoreCase("si"));
    	
    	return ticket;
    }

    public static void finalizar(){

        Floristeria.getInstancia().finalizar();
    }
    
    public static void load(){

        Floristeria.getInstancia().load();
    }

}
