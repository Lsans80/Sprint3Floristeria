package org.example.n2Exe1MySQL.cliente;

import org.example.n2Exe1MySQL.entidad.Producto;
import org.example.n2Exe1MySQL.persistencia.MySQLDB;

import java.util.HashMap;

public class Main {

    public static void main (String[] args){

        //AplicacionFloristeria.start();
        //AplicacionFloristeria.finalizar();

        MySQLDB baseDeDatos = MySQLDB.instanciar();
        HashMap<Integer, Producto> productos = baseDeDatos.getProductos();

        System.out.println(productos);
    }
}
