package org.example.n2Exe1MySQL.entidad;

public class Producto_Arbol extends Producto {
	
	private static final long serialVersionUID = 1L;
	private float arbolAltura;

	public Producto_Arbol(int productoID, String productoNombre, float productoPrecio, float arbolAltura, int productoCantidad) {
		super(productoID, productoNombre, productoPrecio, productoCantidad);
		this.arbolAltura = arbolAltura;
		super.setProductoTipo("Arbol");
		
	}

	public float getArbolAltura() {
		return arbolAltura;
	}

	public void setArbolAltura(float arbolAltura) {
		this.arbolAltura = arbolAltura;
	}

	@Override
	public String toString() {
		return super.toString() + ", Altura=" + arbolAltura + "]";
	}

}