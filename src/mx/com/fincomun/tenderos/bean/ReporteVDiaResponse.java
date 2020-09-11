package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

public class ReporteVDiaResponse extends Response {
	
	List<ReporteVentaDia> venta = new ArrayList<ReporteVentaDia>();

	public List<ReporteVentaDia> getVenta() {
		return venta;
	}

	public void setVenta(List<ReporteVentaDia> venta) {
		this.venta = venta;
	}

	@Override
	public String toString() {
		return "ReporteVDiaResponse [venta size=" + venta.size() + ", getCodError()=" + getCodError() + ", getMsjError()="
				+ getMsjError() + "]";
	}
}
