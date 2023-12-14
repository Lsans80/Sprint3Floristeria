package org.example.n1Exe1Txt.entidad;

import org.example.n1Exe1Txt.herramienta.Material;

public class Producto_Decoracion extends Producto {
	
	private static final long serialVersionUID = 1L;
	private Enum<Material> decoracionMaterial;
	
	public Producto_Decoracion(int productoID, String productoNombre, float productoPrecio, Enum<Material> decoracionMaterial, int productoCantidad) {
		super(productoID, productoNombre, productoPrecio, productoCantidad);
		this.decoracionMaterial = decoracionMaterial;
		super.setProductoTipo("Decoracion");
	}

	public Enum<Material> getDecoracionMaterial() {
		return decoracionMaterial;
	}

	public void setDecoracionMaterial(Enum<Material> decoracionMaterial) {
		this.decoracionMaterial = decoracionMaterial;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Material=" + decoracionMaterial + "]";
	}

}