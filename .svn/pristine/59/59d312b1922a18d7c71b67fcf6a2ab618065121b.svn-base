package mx.com.fincomun.tenderos.bean;

import java.util.ArrayList;
import java.util.List;

import mx.com.fincomun.tenderos.util.Util;

public class VentaPorArticuloRequest extends Request{
	
	private String codigoQR;
	private float montoTotal;
	private int idTipoPago;
	private String idUsuario;
	private List<ProductoVentaCompra> listaProductos = new ArrayList<ProductoVentaCompra>();
	
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
	public List<ProductoVentaCompra> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(List<ProductoVentaCompra> listaProductos) {
		this.listaProductos = listaProductos;
	}
	@Override
	public String toString() {
		return "VentaPorArticuloRequest [codigoQR=" + codigoQR + ", montoTotal=" + montoTotal + ", idTipoPago="
				+ idTipoPago + ", idUsuario=" + idUsuario + ", listaProductos=" + listaProductos + ", getToken()="
				+ getTokenJwt() + "]";
	}
}