package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

public class ReporteVMensualResponse extends Response {
	
	private List<ProveedoresVenta> proveedores = new ArrayList<ProveedoresVenta>();
	private List<PorcentajeSemanal> porcentajePorPagos = new ArrayList<PorcentajeSemanal>();
	
	public List<ProveedoresVenta> getProveedores() {
		return proveedores;
	}
	public void setProveedores(List<ProveedoresVenta> proveedores) {
		this.proveedores = proveedores;
	}
	public List<PorcentajeSemanal> getPorcentajePorPagos() {
		return porcentajePorPagos;
	}
	public void setPorcentajePorPagos(List<PorcentajeSemanal> porcentajePorPagos) {
		this.porcentajePorPagos = porcentajePorPagos;
	}
	@Override
	public String toString() {
		return "ReporteVMensualResponse [proveedores=" + proveedores.size() + ", porcentajePorPagos=" + porcentajePorPagos.size()
				+ ", getCodError()=" + getCodError() + ", getMsjError()=" + getMsjError() + "]";
	}
}
