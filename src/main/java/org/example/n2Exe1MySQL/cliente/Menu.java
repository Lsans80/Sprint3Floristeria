package org.example.n2Exe1MySQL.cliente;

import org.example.n2Exe1MySQL.entidad.Floristeria;
import org.example.n2Exe1MySQL.herramienta.Input;

import static org.example.n2Exe1MySQL.cliente.AplicacionFloristeria.*;

public class Menu {

    private static Floristeria floristeria = Floristeria.getInstancia();
    public static void ejecutarMenu(){

        boolean salir = false;

        do{
            switch(menu()){
                case 1: crearProducto();
                    break;
                case 2: eliminarProducto();
                    break;
                case 3: consultarProductos();
                    break;
                case 4: consultarValorTotalStock();
                    break;
                case 5: crearTicket();
                    break;
                case 6: consultarHistorialTickets();
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
}
