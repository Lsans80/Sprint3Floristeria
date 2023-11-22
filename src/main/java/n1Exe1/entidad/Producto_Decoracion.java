package n1Exe1.entidad;

import n1Exe1.herramienta.Material;

public class Producto_Decoracion extends Producto {
	
	private Enum<Material> decoracionMaterial;
	
	public Producto_Decoracion(String productoNombre, float productoPrecio, Enum<Material> decoracionMaterial) {
		super(productoNombre, productoPrecio);
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