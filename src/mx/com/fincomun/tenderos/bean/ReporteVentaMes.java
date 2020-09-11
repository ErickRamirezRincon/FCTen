package mx.com.fincomun.tenderos.bean;

public class ReporteVentaMes {
	
	private float porcientoVenta;
	private float totalVenta;
	
	public float getPorcientoVenta() {
		return porcientoVenta;
	}
	public void setPorcientoVenta(float porcientoVenta) {
		this.porcientoVenta = porcientoVenta;
	}
	public float getTotalVenta() {
		return totalVenta;
	}
	public void setTotalVenta(float totalVenta) {
		this.totalVenta = totalVenta;
	}
	@Override
	public String toString() {
		return "ReporteVentaMes [porcientoVenta=" + porcientoVenta + ", totalVenta=" + totalVenta + "]";
	}
}
