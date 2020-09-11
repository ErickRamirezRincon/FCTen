package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

public class ReporteVCMesResponse extends Response{
	
	private ReporteVentaMes venta;
	private ReporteCompraMes compra;
	private List<ProductosTopVendidos> masVendidos = new ArrayList<ProductosTopVendidos>();
	public ReporteVentaMes getVenta() {
		return venta;
	}
	public void setVenta(ReporteVentaMes venta) {
		this.venta = venta;
	}
	public ReporteCompraMes getCompra() {
		return compra;
	}
	public void setCompra(ReporteCompraMes compra) {
		this.compra = compra;
	}
	public List<ProductosTopVendidos> getMasVendidos() {
		return masVendidos;
	}
	public void setMasVendidos(List<ProductosTopVendidos> masVendidos) {
		this.masVendidos = masVendidos;
	}
	@Override
	public String toString() {
		return "ReporteVCMesResponse [venta=" + venta.toString() + ", compra=" + compra.toString() + ", masVendidos size =" + masVendidos.size()
				+ ", getCodError()=" + getCodError() + ", getMsjError()=" + getMsjError() + "]";
	}
}