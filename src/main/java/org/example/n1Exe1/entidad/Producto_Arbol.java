package org.example.n1Exe1.entidad;

public class Producto_Arbol extends Producto {
	
	private float arbolAltura;

	public Producto_Arbol(String productoNombre, float productoPrecio, float arbolAltura, int productoCantidad) {
		super(productoNombre, productoPrecio, productoCantidad);
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