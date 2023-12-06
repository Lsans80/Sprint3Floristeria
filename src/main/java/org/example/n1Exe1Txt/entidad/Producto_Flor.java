package org.example.n1Exe1Txt.entidad;

public class Producto_Flor extends Producto {
	
	private static final long serialVersionUID = 1L;
	private String florColor;
	
	public Producto_Flor(int productoID, String productoNombre, float productoPrecio, String florColor, int productoCantidad) {
		super(productoID, productoNombre, productoPrecio, productoCantidad);
		this.florColor = florColor;
		super.setProductoTipo("Flor");
	}

	public String getFlorColor() {
		return florColor;
	}

	public void setFlorColor(String florColor) {
		this.florColor = florColor;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Color=" + florColor + "]";
	}

}