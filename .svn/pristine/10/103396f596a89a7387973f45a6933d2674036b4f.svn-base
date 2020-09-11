package mx.com.fincomun.tenderos.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.Producto;
import mx.com.fincomun.tenderos.bean.ProductoInventario;
import mx.com.fincomun.tenderos.bean.ProductoInventarioResponse;
import mx.com.fincomun.tenderos.bean.ProductoRequest;
import mx.com.fincomun.tenderos.bean.ProductoResponse;
import mx.com.fincomun.tenderos.dao.ProductoDao;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.Util;

@Service
public class ProductoService {
	
	@Autowired
	private ProductoDao productoDao;
	
	private static Logger log = Logger.getLogger(ProductoService.class);
	
	public int altaProducto(ProductoRequest request) throws Exception {
		int error = 0;
		log.info("Producto: ["+request.toString()+"]");
		
		if(!productoExiste(request.getNombre(), request.getIdUsuario())){
			if(!productoDao.insertProducto(request)){
				error = -2; 
			}
		} else {
			error = -1;
		}
		log.info("Alta Producto: ["+error+"]");
		return error;
	}
	
	public boolean actualizacionProducto(ProductoRequest request) throws Exception {
		boolean isModifica = false;
		log.info("Producto: ["+request.toString()+"]");
		isModifica = productoDao.updateProducto(request);
		log.info("Modifica Producto: ["+isModifica+"]");
		return isModifica;
	}
	
	public boolean actualizacionCierreMes() throws Exception {
		boolean isUpdate = false;
		
		String fechaActual = Util.getFechaDDMMYY(new Date());
		String fechaFinMes = Util.getFechaFinMesActual();
		
		log.info("Fecha Actual: ["+fechaActual+"]");
		log.info("Fecha Fin Mes: ["+fechaFinMes+"]");
		
		if(fechaActual.equalsIgnoreCase(fechaFinMes)){
			log.info("Realizando actualizacion de productos por cierre de mes. ");
			isUpdate = productoDao.updateProductoCierreMes();
		} else {
			log.info("Las Fechas son diferentes. ");
		}
		log.info("Actualizacion de Cierre de Mes ["+isUpdate+"]");
		return isUpdate;
	}
	
	public boolean eliminaProducto(ProductoRequest request) throws Exception {
		boolean isElimina = false;
		log.info("Producto: ["+request.toString()+"]");
		isElimina = productoDao.deleteProducto(request);
		log.info("Elimina Producto: ["+isElimina+"]");
		return isElimina;
	}
	
	public ProductoResponse buscarPorNombre(String nombre, long idUsuario) throws Exception {
		ProductoResponse response = null;
		log.info("Buscando producto por Nombre ["+nombre+"] IdUsuario ["+idUsuario+"]");
		Producto producto = productoDao.getPorNombre(nombre.toUpperCase(), idUsuario);
		if(producto != null){
			response = new ProductoResponse();
			response.setProducto(producto);
			log.info("Resultado busqueda: ["+response.toString()+"]");
		} else {
			log.info("No se encontraron resultados.");
		}
		
		return response;
	}
	
	public ProductoResponse buscarPorQR(String codigoQR, long idUsuario) throws Exception {
		ProductoResponse response = null;
		log.info("Buscando producto por QR ["+codigoQR+"] IdUsuario ["+idUsuario+"]");
		
		List<Producto> productos = productoDao.listProductos(idUsuario);
		if(productos != null && productos.size() > 0){
			for(Producto bean : productos){
				if(bean.getCodigoQR().toUpperCase().equals(codigoQR.toUpperCase())){
					response = new ProductoResponse();
					response.setProducto(bean);
					log.info("Encontrado en busqueda: ["+bean.toString()+"]");
					break;
				} else {
					log.info("No se encontraron resultados.");
				}
			}
			
		} 
		return response;
	}
	
	public ProductoInventarioResponse getProductosPorFiltro(int filtro, long idUsuario) throws Exception {
		ProductoInventarioResponse response = null;
		List<ProductoInventario> productos = null;
		
		switch (filtro) {
		case 1:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_NOMBRE_ASC_BD, idUsuario);
			break;
		case 2:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_NOMBRE_DESC_BD, idUsuario);
			break;
		case 3:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_VENTA_ASC_BD, idUsuario);
			break;
		case 4:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_VENTA_DESC_BD, idUsuario);
			break;
		case 5:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_ASC_BD, idUsuario);
			break;
		case 6:
			productos = productoDao.getListProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_DESC_BD, idUsuario);
			break;
		}
		
		if(productos != null && productos.size() > 0){
			response = new ProductoInventarioResponse();
			response.setProductos(productos);
			log.info("Se encontraron ["+productos.size()+"] productos.");
		}
		
		return response;
	}
	
	private boolean productoExiste(String nombre, long idUsuario){
		boolean isExiste = true;
		Producto producto = productoDao.getPorNombre(nombre.toUpperCase(), idUsuario);
		if(producto == null){
			isExiste = false;
		}
		return isExiste;
	}

}
