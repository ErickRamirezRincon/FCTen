package mx.com.fincomun.tenderos.bean;

public class ProductoInventario extends Producto {
	
	private float precioCosto;
	private String nombreEmpresa;
	private int idProveedor;
	private int stock;
	private int stockInicial;

	public float getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(float precioCosto) {
		this.precioCosto = precioCosto;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStockInicial() {
		return stockInicial;
	}

	public void setStockInicial(int stockInicial) {
		this.stockInicial = stockInicial;
	}

	@Override
	public String toString() {
		return "ProductoInventario [precioCosto=" + precioCosto + ", nombreEmpresa=" + nombreEmpresa + ", idProveedor="
				+ idProveedor + ", stock=" + stock + ", stockInicial=" + stockInicial + "]";
	}
}