package mx.com.fincomun.tenderos.bean;

public class TipoPago {
	
	private int idTipoPago;
	private String nombre;
	
	public int getIdTipoPago() {
		return idTipoPago;
	}
	public void setIdTipoPago(int idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "TipoPago [idTipoPago=" + idTipoPago + ", nombre=" + nombre + "]";
	}
}
