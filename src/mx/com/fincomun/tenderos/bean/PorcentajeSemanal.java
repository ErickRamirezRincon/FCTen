package mx.com.fincomun.tenderos.bean;

public class PorcentajeSemanal {
	
	private int idTipoPago;
	private String nombrePago;
	private float porcentaje;
	
	public int getIdTipoPago() {
		return idTipoPago;
	}
	public void setIdTipoPago(int idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	public String getNombrePago() {
		return nombrePago;
	}
	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	@Override
	public String toString() {
		return "PorcentajeSemanal [idTipoPago=" + idTipoPago + ", nombrePago=" + nombrePago + ", porcentaje="
				+ porcentaje + "]";
	}
}