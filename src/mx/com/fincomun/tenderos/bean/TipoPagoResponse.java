package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

public class TipoPagoResponse extends Response {
	
	private List<TipoPago> tipoPagos = new ArrayList<TipoPago>();

	public List<TipoPago> getTipoPagos() {
		return tipoPagos;
	}
	
	public void setTipoPagos(List<TipoPago> tipoPagos) {
		this.tipoPagos = tipoPagos;
	}
	
	@Override
	public String toString() {
		return "TipoPagoResponse [tipoPago SIZE =" + tipoPagos.size() + ", getCodError()=" + getCodError() + ", getMsjError()="
				+ getMsjError() + "]";
	}	
}