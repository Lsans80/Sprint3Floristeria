package n1Exe1.entidad;

public abstract class Producto {
	
	private int productoID;
	private String productoNombre;
	private float productoPrecio;
	private String productoTipo;
	private static int proximoID = 1;

	private int productoCantidad;

	
	public Producto(String productoNombre, float productoPrecio, int cantidad) {
		this.productoID = proximoID;
		this.productoNombre = productoNombre;
		this.productoPrecio = productoPrecio;
		this.productoCantidad = cantidad;
		proximoID++;
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
				+ productoPrecio + ", Tipo=" + productoTipo + " ";
	}


}