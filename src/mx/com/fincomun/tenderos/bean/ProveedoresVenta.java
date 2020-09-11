package mx.com.fincomun.tenderos.bean;

public class ProveedoresVenta {

	private float totalPedido;
	private String nombreEmpresa;
	private String nombreProveedor;
	
	public float getTotalPedido() {
		return totalPedido;
	}
	public void setTotalPedido(float totalPedido) {
		this.totalPedido = totalPedido;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	@Override
	public String toString() {
		return "ProveedoresVenta [totalPedido=" + totalPedido + ", nombreEmpresa=" + nombreEmpresa
				+ ", nombreProveedor=" + nombreProveedor + "]";
	}
}