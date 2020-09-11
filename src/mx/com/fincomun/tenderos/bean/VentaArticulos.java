package mx.com.fincomun.tenderos.bean;

public class VentaArticulos {
	
	private long id;
	private long ventasId;
	private long articuloId;
	private int numeroArticulos;
	private long tipoPagoId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVentasId() {
		return ventasId;
	}
	public void setVentasId(long ventasId) {
		this.ventasId = ventasId;
	}
	public long getArticuloId() {
		return articuloId;
	}
	public void setArticuloId(long articuloId) {
		this.articuloId = articuloId;
	}
	public int getNumeroArticulos() {
		return numeroArticulos;
	}
	public void setNumeroArticulos(int numeroArticulos) {
		this.numeroArticulos = numeroArticulos;
	}
	public long getTipoPagoId() {
		return tipoPagoId;
	}
	public void setTipoPagoId(long tipoPagoId) {
		this.tipoPagoId = tipoPagoId;
	}
}
