package n1Exe1.entidad;

public class Producto_Flor extends Producto {
	
	private String florColor;
	
	public Producto_Flor(String productoNombre, float productoPrecio, String florColor) {
		super(productoNombre, productoPrecio);
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