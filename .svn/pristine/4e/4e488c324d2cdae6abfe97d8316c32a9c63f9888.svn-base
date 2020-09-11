package mx.com.fincomun.tenderos.bean;

import mx.com.fincomun.tenderos.util.Util;

public class CompraDirectaRequest extends Request{
	
	private String codigoQR;
	private float montoTotal;
	private int idTipoPago;
	private String idUsuario;
	private String idProveedor;
	
	public String getCodigoQR() {
		return codigoQR;
	}
	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}
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
	public long getIdProveedor() {
		return Util.convertStringToLong(idProveedor);
	}
	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
	}
	@Override
	public String toString() {
		return "CompraDirectaRequest [codigoQR=" + codigoQR + ", montoTotal=" + montoTotal + ", idTipoPago="
				+ idTipoPago + ", idUsuario=" + idUsuario + ", idProveedor=" + idProveedor + ", getToken()="
				+ getTokenJwt() + "]";
	}
}
