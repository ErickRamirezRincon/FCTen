package mx.com.fincomun.tenderos.bean;

public class ReporteCompraMes {
	
	private float porcientoCompra;
	private float totalCompra;
	
	public float getPorcientoCompra() {
		return porcientoCompra;
	}
	public void setPorcientoCompra(float porcientoCompra) {
		this.porcientoCompra = porcientoCompra;
	}
	public float getTotalCompra() {
		return totalCompra;
	}
	public void setTotalCompra(float totalCompra) {
		this.totalCompra = totalCompra;
	}
	@Override
	public String toString() {
		return "ReporteCompraMes [porcientoCompra=" + porcientoCompra + ", totalCompra=" + totalCompra + "]";
	}
}
