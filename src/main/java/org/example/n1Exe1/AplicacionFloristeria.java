package n1Exe1;

import n1Exe1.entidad.Producto_Arbol;
import n1Exe1.entidad.Producto_Decoracion;
import n1Exe1.entidad.Producto_Flor;
import n1Exe1.herramienta.Input;
import n1Exe1.herramienta.Material;

import java.util.Scanner;

public class AplicacionFloristeria {

    static Scanner sc = new Scanner(System.in);

    public static void start (){

        Floristeria floristeria = Floristeria.getInstancia("FeetLand", 40.0f);

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
                case 0: System.out.println("Gracias por utilizar nuestra floristería.");
                    salir = true;
                    break;
            }
        }while(!salir);
    }

    public static byte menu(){
        byte opcion;
        final byte MINIMO = 0;
        final byte MAXIMO = 4;

        do{
            System.out.println("\n****FeetLand****");
            System.out.println("1. Agregar producto.");
            System.out.println("2. Eliminar producto.");
            System.out.println("3. Listar productos.");
            System.out.println("4. Consultar valor stock total.");
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
                String nombre = Input.inputString("Dime el nombre del arbol:");
                float precio = Input.inputFloat("Dime el precio;");
                float altura = Input.inputFloat("Dime la altura:");
                Producto_Arbol arbol = new Producto_Arbol (nombre,precio,altura);
                floristeria.addProducto(arbol);
                break;

            case 2:
                nombre = Input.inputString("Dime el nombre de la flor:");
                precio = Input.inputFloat("Dime el precio;");
                String color = Input.inputString("Dime el color:");
                Producto_Flor flor = new Producto_Flor(nombre,precio,color);
                floristeria.addProducto(flor);
                break;

            case 3:
                nombre = Input.inputString("Dime el tipo de decoración:");
                precio = Input.inputFloat("Dime el precio;");
                String material = Input.inputString("Dime el material (madera o plastico)");
                Material decoracionMaterial = Material.valueOf(material.toUpperCase());

                Producto_Decoracion decoracion = new Producto_Decoracion(nombre,precio,decoracionMaterial);
                floristeria.addProducto(decoracion);
                break;
        }
    }

    public static void eliminarProducto(Floristeria floristeria){

        int id = Input.inputInt("Introduce el número del ID de producto que quieres eliminar:");
        floristeria.eliminarProducto(id);
    }

    public static void listarProductos(Floristeria floristeria){
        System.out.println(floristeria.getProductos());
    }

    public static void valorTotalStock (Floristeria floristeria){

        float valorTotal = floristeria.getValorTotalStock();

        String formattedValue = String.format("%.2f", valorTotal);

        System.out.println("El valor total del stock es de " + valorTotal + " Euros.");
    }

    public static void finalizar (){

    }

}
