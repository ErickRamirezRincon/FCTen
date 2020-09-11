package mx.com.fincomun.tenderos.bean;

public class ProductosTopVendidos extends Producto {
	
	private String nombreEmpresa;

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	@Override
	public String toString() {
		return "ProductosTopVendidos [nombreEmpresa=" + nombreEmpresa + ", getIdProducto()=" + getIdProducto()
				+ ", getNombre()=" + getNombre() + ", getPrecioVenta()=" + getPrecioVenta() + ", getImagen()="
				+ getImagen() + ", getCodigoQR()=" + getCodigoQR() + "]";
	}
}