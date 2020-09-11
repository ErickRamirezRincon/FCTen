package mx.com.fincomun.tenderos.bean;

import mx.com.fincomun.tenderos.util.Util;

public class ReporteRequest extends Request{
	
	private String idUsuario;
	private int mes;
	private String fecha;
	private String fechaInicio;
	private String fechaFin;
	
	public long getIdUsuario() {
		return Util.convertStringToLong(idUsuario);
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "ReporteRequest [idUsuario=" + idUsuario + ", mes=" + mes + ", fecha=" + fecha + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", getToken()=" + getTokenJwt() + "]";
	}
}
