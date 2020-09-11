package mx.com.fincomun.tenderos.bean;

public class ProductoResponse extends Response {
	
	private Producto producto;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "ProductoResponse [producto=" + getProducto().toString() + ", getCodError()=" + getCodError() + ", getMsjError()="
				+ getMsjError() + "]";
	}
}
