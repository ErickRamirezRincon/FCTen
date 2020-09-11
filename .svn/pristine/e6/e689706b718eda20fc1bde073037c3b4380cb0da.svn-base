package mx.com.fincomun.tenderos.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.TipoPago;
import mx.com.fincomun.tenderos.bean.VentaDirectaRequest;
import mx.com.fincomun.tenderos.bean.VentaPorArticuloRequest;
import mx.com.fincomun.tenderos.bean.ProductoInventario;
import mx.com.fincomun.tenderos.bean.ProductoRequest;
import mx.com.fincomun.tenderos.bean.ProductoVentaCompra;
import mx.com.fincomun.tenderos.dao.ProductoDao;
import mx.com.fincomun.tenderos.dao.TipoPagoDao;
import mx.com.fincomun.tenderos.dao.VentasDao;

@Service
public class VentasService {
	
	@Autowired
	private VentasDao ventasDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private TipoPagoDao tipoPagoDao;
	
	private static Logger log = Logger.getLogger(VentasService.class);
	
	public int altaVentaDirecta(VentaDirectaRequest request) throws Exception {
		int error = 0;
		if(validTipoPago(request.getIdTipoPago())){
			log.info("Venta: ["+request.toString()+"]");
			if(!ventasDao.insertVentaDirecta(request)){
				error = -2;
			}
		} else {
			error = -1;
		}
		return error;
	}
	
	public int altaVentaPorArticulo(VentaPorArticuloRequest request) throws Exception {
		int error = 0;
		int numInsert = 0;
		if(validTipoPago(request.getIdTipoPago())){
			if(validaStock(request)){
				log.info("Venta: ["+request.toString()+"]");
				int idVenta = ventasDao.insertVentaPorArticulo(request);
				log.info("IdVenta Generado: ["+idVenta+"]");
				if(idVenta != 0){
					for(ProductoVentaCompra producto: request.getListaProductos()){
						log.info("Producto: ["+producto.toString()+"]");
						ventasDao.insertVentaArticulos(idVenta, producto.getIdProducto(), producto.getNumeroProductos());
						
						restaStock(producto);
						
						numInsert ++;
					}
					log.info("Articulos insertados: ["+numInsert+"]");
				} else {
					error = -2;
				}
			} else {
				error = -3;
			}
		} else {
			error = -1;
		}
		return error;
	}
	
	private boolean validaStock(VentaPorArticuloRequest request){
		boolean isValid = false;
		int error = 0;
		for(ProductoVentaCompra producto: request.getListaProductos()){
			ProductoInventario productoInventario = productoDao.getPorId(producto.getIdProducto());
			if(productoInventario != null){
				if(producto.getNumeroProductos() > productoInventario.getStock()){
					error++;
				}
			}
		}
		if(error == 0){
			isValid = true;
		}
		log.info("ValidaStock:" +isValid);
		
		return isValid;
	}
	
	private void restaStock(ProductoVentaCompra producto){
		ProductoInventario productoInt = productoDao.getPorId(producto.getIdProducto());
		if(productoInt != null){
			int stockFix = productoInt.getStock() - producto.getNumeroProductos();
			log.info("Stock actual: "+stockFix);
			
			ProductoRequest request = new ProductoRequest();
			request.setIdProducto(productoInt.getIdProducto());
			request.setStock(stockFix);
			productoDao.updateProductoStock(request);
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

}
