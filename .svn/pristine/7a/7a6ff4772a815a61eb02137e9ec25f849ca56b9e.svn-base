package mx.com.fincomun.tenderos.bean;

import mx.com.fincomun.tenderos.util.Util;

public class ProductoRequest extends Request{
	
	private int idProducto;
	private String nombre;
	private float precioVenta;
	private float precioCosto;
	private String imagen;
	private String codigoQR;
	private int stock;
	private int idProveedor;
	private String idUsuario;
	private int stockInicial;
	
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}
	public float getPrecioCosto() {
		return precioCosto;
	}
	public void setPrecioCosto(float precioCosto) {
		this.precioCosto = precioCosto;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getCodigoQR() {
		return codigoQR;
	}
	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public long getIdUsuario() {
		return Util.convertStringToLong(idUsuario);
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getStockInicial() {
		return stockInicial;
	}
	public void setStockInicial(int stockInicial) {
		this.stockInicial = stockInicial;
	}
	@Override
	public String toString() {
		return "ProductoRequest [idProducto=" + idProducto + ", nombre=" + nombre + ", precioVenta=" + precioVenta
				+ ", precioCosto=" + precioCosto + ", imagen=" + imagen + ", codigoQR=" + codigoQR + ", stock=" + stock
				+ ", idProveedor=" + idProveedor + ", idUsuario=" + idUsuario + ", stockInicial=" + stockInicial
				+ ", getToken()=" + getTokenJwt() + "]";
	}
}