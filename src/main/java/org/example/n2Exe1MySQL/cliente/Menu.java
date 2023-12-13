package org.example.n2Exe1MySQL.cliente;

import org.example.n2Exe1MySQL.herramienta.Input;

import static org.example.n2Exe1MySQL.cliente.AplicacionFloristeria.*;

public class Menu {

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
}
