package org.example.n1Exe1Txt.entidad;

import java.io.Serial;
import java.io.Serializable;

public abstract class Producto implements Serializable, Cloneable {

	@Serial
	private static final long serialVersionUID = 1L;
	private int productoID;
	private String productoNombre;
	private float productoPrecio;
	private String productoTipo;
	private int productoCantidad;
	
	public Producto(int productoID, String productoNombre, float productoPrecio, int cantidad) {
		this.productoID = productoID;
		this.productoNombre = productoNombre;
		this.productoPrecio = productoPrecio;
		this.productoCantidad = cantidad;

	}
	
	public int getProductoID() {
		return productoID;
	}

	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public float getProductoPrecio() {
		return productoPrecio;
	}

	public void setProductoPrecio(float productoPrecio) {
		this.productoPrecio = productoPrecio;
	}

	public String getProductoTipo() {
		return productoTipo;
	}

	public void setProductoTipo(String productoTipo) {
		this.productoTipo = productoTipo;
	}

	public int getProductoCantidad() {
		return productoCantidad;
	}
	public void setProductoCantidad(int productoCantidad) {
		this.productoCantidad = productoCantidad;
	}
	
	/*public static int getProximoID() {
		return proximoID;
	}

	public static void setProximoID(int proximoID) {
		Producto.proximoID = proximoID;
	}*/

	public void reducirProductoCantidad(int cantidad) {
		productoCantidad = productoCantidad - cantidad;
	}
	
	public void reducirProductoCantidadUnidad() {
		productoCantidad--;
	}
	
	public void incrementarProductoCantidadUnidad() {
		productoCantidad++;
	}
	
	public void resetProductoCantidad() {
		productoCantidad = 0;
	}
	
	public Producto clonar () {
		Producto pcopia = (Producto) this.clone();
		return pcopia;
	}
	
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Producto))
			return false;
		Producto other = (Producto) obj;
		if (productoID != other.productoID)
			return false;
		if (productoNombre == null) {
			if (other.productoNombre != null)
				return false;
		} else if (!productoNombre.equals(other.productoNombre))
			return false;
		if (Float.floatToIntBits(productoPrecio) != Float.floatToIntBits(other.productoPrecio))
			return false;
		if (productoTipo == null) {
			if (other.productoTipo != null)
				return false;
		} else if (!productoTipo.equals(other.productoTipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [ID= " + productoID + ", Nombre=" + productoNombre + ", Precio="
				+ productoPrecio + ", Tipo=" + productoTipo + ", Cantidad=" + productoCantidad + " ";
	}


}