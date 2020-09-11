package mx.com.fincomun.tenderos.bean;

import mx.com.fincomun.tenderos.util.Util;

public class VentaDirectaRequest extends Request{
	
	private float montoTotal;
	private int idTipoPago;
	private String idUsuario;

	public float getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}
	public int getIdTipoPago() {
		return idTipoPago;
	}
	public void setIdTipoPago(int idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	public long getIdUsuario() {
		return Util.convertStringToLong(idUsuario);
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	@Override
	public String toString() {
		return "VentaDirectaRequest [montoTotal=" + montoTotal + ", idTipoPago=" + idTipoPago + ", idUsuario="
				+ idUsuario + ", getToken()=" + getTokenJwt() + "]";
	}
}