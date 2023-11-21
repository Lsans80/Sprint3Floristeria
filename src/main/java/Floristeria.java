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
        this.productos = new List<Producto>();
        this.tickets = new List<Ticket>();
    }

    public Floristeria instanciar (String nombre, float valorTotalStock){

        if (instancia == null){

            return new Floristeria(nombre,valorTotalStock);
        }
        return instancia;
    }

    public Arbol agregarArbol (Arbol arbol){

        System.out.println("Nombre del arbol:");
        String nombre = sc.nextLine();
        System.out.println("Precio del arbol:");
        float precio = sc.nextFloat();
        System.out.println("Cantidad:");
        int cantidad = sc.nextInt();

        Arbol arbol = new Arbol (nombre,precio,cantidad);
        productos.add(arbol);

        return arbol;
    }


}
