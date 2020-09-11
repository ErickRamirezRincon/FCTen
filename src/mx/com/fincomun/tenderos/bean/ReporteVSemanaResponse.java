package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

public class ReporteVSemanaResponse extends Response{
	
	
	private List<ReporteVSemana> venta = new ArrayList<ReporteVSemana>();
	private List<PorcentajeSemanal> porcentajePorPagos = new ArrayList<PorcentajeSemanal>();
	public List<ReporteVSemana> getVenta() {
		return venta;
	}
	public void setVenta(List<ReporteVSemana> venta) {
		this.venta = venta;
	}
	public List<PorcentajeSemanal> getPorcentajePorPagos() {
		return porcentajePorPagos;
	}
	public void setPorcentajePorPagos(List<PorcentajeSemanal> porcentajePorPagos) {
		this.porcentajePorPagos = porcentajePorPagos;
	}
	@Override
	public String toString() {
		return "ReporteVSemanaResponse [venta=" + venta.size() + ", porcentajePorPagos=" + porcentajePorPagos.size()
				+ ", getCodError()=" + getCodError() + ", getMsjError()=" + getMsjError() + "]";
	}
	
	
	
	
}
