package mx.com.fincomun.tenderos.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.CompraDirectaRequest;
import mx.com.fincomun.tenderos.bean.CompraRequest;
import mx.com.fincomun.tenderos.bean.ProductoInventario;
import mx.com.fincomun.tenderos.bean.ProductoRequest;
import mx.com.fincomun.tenderos.bean.ProductoVentaCompra;
import mx.com.fincomun.tenderos.bean.Proveedor;
import mx.com.fincomun.tenderos.bean.TipoPago;
import mx.com.fincomun.tenderos.dao.ComprasDao;
import mx.com.fincomun.tenderos.dao.ProductoDao;
import mx.com.fincomun.tenderos.dao.ProveedorDao;
import mx.com.fincomun.tenderos.dao.TipoPagoDao;

@Service
public class ComprasService {

	@Autowired
	private ComprasDao comprasDao;
	
	@Autowired
	private TipoPagoDao tipoPagoDao;
	
	@Autowired
	private ProveedorDao proveedorDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	private static Logger log = Logger.getLogger(ComprasService.class);
	
	public int altaCompra(CompraRequest request) throws Exception{
		int error = 0;
		int numInsert = 0;
		if(validTipoPago(request.getIdTipoPago())){
			if(validProveedor(request.getIdProveedor())){
				log.info("Compra: ["+request.toString()+"]");
				int idCompra = comprasDao.insertCompra(request);
				log.info("IdCompra Generado: ["+idCompra+"]");
				if(idCompra != 0){
					for(ProductoVentaCompra producto: request.getListaProductos()){
						log.info("Producto: ["+producto.toString()+"]");
						comprasDao.insertCompraArticulos(idCompra, producto.getIdProducto(), producto.getNumeroProductos());
						
						sumaStock(producto);
						
						numInsert ++;
					}
					log.info("Articulos insertados: ["+numInsert+"]");
				} else {
					error = -3;
				}
			} else {
				error = -2;
			}
		} else {
			error = -1;
		}
		return error;
	}
	
	public int altaCompraDirecta(CompraDirectaRequest request) throws Exception{
		int error = 0;
		if(validTipoPago(request.getIdTipoPago())){
			if(validProveedor(request.getIdProveedor())){
				log.info("Compra: ["+request.toString()+"]");
				int idCompra = comprasDao.insertCompraDirecta(request);
				log.info("IdCompra Generado: ["+idCompra+"]");
				if(idCompra == 0){
					error = -3;
				}
			} else {
				error = -2;
			}
		} else {
			error = -1;
		}
		return error;
	}
	
	private void sumaStock(ProductoVentaCompra producto){
		ProductoInventario productoInt = productoDao.getPorId(producto.getIdProducto());
		if(productoInt != null){
			int stockFix = productoInt.getStock() + producto.getNumeroProductos();
			int stockInicialFix = productoInt.getStockInicial() + producto.getNumeroProductos();
			log.info("Stock actual: "+stockFix);
			
			ProductoRequest request = new ProductoRequest();
			request.setIdProducto(productoInt.getIdProducto());
			request.setStock(stockFix);
			request.setStockInicial(stockInicialFix);
			productoDao.updateProductoStockInicial(request);
		}
	}
	
	private boolean validTipoPago(int idTipoPago){
		boolean isExist = false;
		TipoPago response = tipoPagoDao.buscarTipoPago(idTipoPago);
		if(response != null){
			isExist = true;
		}
		return isExist;
	}
	
	private boolean validProveedor(long idProveedor){
		boolean isExist = false;
		Proveedor response = proveedorDao.getProveedorById(idProveedor);
		if(response != null){
			isExist = true;
		}
		return isExist;
	}
}
