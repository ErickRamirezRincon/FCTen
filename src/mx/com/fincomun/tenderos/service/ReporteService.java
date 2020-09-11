package mx.com.fincomun.tenderos.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.PedidoProveedor;
import mx.com.fincomun.tenderos.bean.PorcentajeSemanal;
import mx.com.fincomun.tenderos.bean.ProductoInventario;
import mx.com.fincomun.tenderos.bean.ProductosTopVendidos;
import mx.com.fincomun.tenderos.bean.ProveedoresVenta;
import mx.com.fincomun.tenderos.bean.ReporteCMensualResponse;
import mx.com.fincomun.tenderos.bean.ReporteCompraMes;
import mx.com.fincomun.tenderos.bean.ReporteRequest;
import mx.com.fincomun.tenderos.bean.ReporteVCMesResponse;
import mx.com.fincomun.tenderos.bean.ReporteVDiaResponse;
import mx.com.fincomun.tenderos.bean.ReporteVMensualResponse;
import mx.com.fincomun.tenderos.bean.ReporteVSemana;
import mx.com.fincomun.tenderos.bean.ReporteVSemanaResponse;
import mx.com.fincomun.tenderos.bean.ReporteVentaDia;
import mx.com.fincomun.tenderos.bean.ReporteVentaMes;
import mx.com.fincomun.tenderos.bean.VentaArticulos;
import mx.com.fincomun.tenderos.dao.ProductoDao;
import mx.com.fincomun.tenderos.dao.ReporteDao;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.Util;

@Service
public class ReporteService {
	
	@Autowired
	private ReporteDao reporteDao;
	
	@Autowired
	private ProductoDao productoDao;
	
	private static Logger log = Logger.getLogger(ReporteService.class);
	
	public ReporteVCMesResponse getReporteMes(ReporteRequest request) throws Exception {
		ReporteVCMesResponse response = new ReporteVCMesResponse();
		
		setFechasPorMes(request);
		
		ReporteVentaMes venta = reporteDao.getTotalVentasMes(request);
		if(venta != null){
			venta.setPorcientoVenta(getPorcentajeVenta(request));
			response.setVenta(venta);
		}
			
		ReporteCompraMes compra = reporteDao.getCompraTPMes(request);
		if(compra != null){
			compra.setPorcientoCompra(getPorcentajeCompra(request));
			response.setCompra(compra);
		}
			
		String listIds = reporteDao.idsTopVendidos(request);
		if(listIds != null && listIds.trim().length() > 0){
			listIds = quitarComa(listIds);
			log.info("Lista Ids: ["+listIds+"]");
			List<ProductosTopVendidos> listProductos = reporteDao.getProductosTopVendidos(listIds);
			if(listProductos != null && listProductos.size() > 0){
				response.setMasVendidos(listProductos);
			} 
		} 
		
		return response;
	}
	
	public ReporteVDiaResponse getReporteVentaDia(ReporteRequest request) throws Exception {
		ReporteVDiaResponse response = null;
		
		List<ReporteVentaDia> listaVentas = reporteDao.getVentasDia(request);
		if(listaVentas != null && listaVentas.size() > 0){
			response = new ReporteVDiaResponse();
			response.setVenta(listaVentas);
		}
		return response;
	}
	
	public ReporteVSemanaResponse getReporteVentaSemana(ReporteRequest request) throws Exception {
		int numSemana = 1;
		ReporteVSemanaResponse response = null;
		List<ReporteVSemana> reporteSemana = null;
		
		List<ReporteVSemana> semanas = getFechasSemana(request.getMes());
		if(semanas != null && semanas.size() > 0){
			reporteSemana = new ArrayList<ReporteVSemana>();
			for(ReporteVSemana bean : semanas){
				ReporteVSemana reporte = new ReporteVSemana();
				reporte.setFechasInicio(bean.getFechasInicio());
				reporte.setFechasFin(bean.getFechasFin());
				reporte.setSemana(numSemana);
				float monto = reporteDao.getVentaPorSemana(request.getIdUsuario(), bean.getFechasInicio(), bean.getFechasFin());
				log.info("Monto ["+monto+"] - Semana ["+numSemana+"]");
				reporte.setMonto(monto);
				reporteSemana.add(reporte);
				numSemana++;
			}
			
			request.setFechaInicio(Util.getFechaInicioMes(request.getMes()));
			request.setFechaFin( Util.getFechaFinMes(request.getMes()));
			
			List<PorcentajeSemanal> porcentajePorPagos = getPorcentajePorTipoPago(request);
			
//			List<PorcentajeSemanal> porcentajePorPagos = reporteDao.getPorcentajeMensual(request.getIdUsuario(), 
//					Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
//			
			if(reporteSemana != null && reporteSemana.size() > 0 && porcentajePorPagos != null && porcentajePorPagos.size() > 0){
				response = new ReporteVSemanaResponse();
				response.setVenta(reporteSemana);
				response.setPorcentajePorPagos(porcentajePorPagos);
				log.info("Response: "+response.toString());
			}
		}
		return response;
	}
	
	public ReporteVMensualResponse getReporteVentaMensual(ReporteRequest request) throws Exception {
		ReporteVMensualResponse response= null;
		List<ProveedoresVenta> listaVentas = reporteDao.getListVentas(request.getIdUsuario(), 
				Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
		
		List<ProveedoresVenta> listaVentasDirectas = reporteDao.getListVentasDirectas(request.getIdUsuario(), 
				Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
		
		
		if(listaVentasDirectas != null && listaVentasDirectas.size() > 0){
			for(ProveedoresVenta bean : listaVentasDirectas){
				listaVentas.add(bean);
			}
		}
		
		if(listaVentas != null && listaVentas.size() > 0){
			response = new ReporteVMensualResponse();
			response.setProveedores(listaVentas);
			
			request.setFechaInicio(Util.getFechaInicioMes(request.getMes()));
			request.setFechaFin( Util.getFechaFinMes(request.getMes()));
			
			List<PorcentajeSemanal> porcentajePorPagos = getPorcentajePorTipoPago(request);
			
//			List<PorcentajeSemanal> porcentajePorPagos = reporteDao.getPorcentajeMensual(request.getIdUsuario(), 
//					Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
			if(porcentajePorPagos != null && porcentajePorPagos.size() > 0){
				response.setPorcentajePorPagos(porcentajePorPagos);
				log.info("Response: "+response.toString());
			}
		}
		return response;
	}
	
	public ReporteCMensualResponse getReporteCompraMensual(ReporteRequest request) throws Exception  {
		ReporteCMensualResponse response= null;
		List<ProveedoresVenta> listaVentas = reporteDao.getListCompras(request.getIdUsuario(), 
				Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
		List<ProveedoresVenta> listaVentasDirectas = reporteDao.getListComprasDirectas(request.getIdUsuario(), 
				Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
		
		if(listaVentasDirectas != null && listaVentasDirectas.size() > 0){
			for(ProveedoresVenta bean : listaVentasDirectas){
				listaVentas.add(bean);
			}
		}
		
		if(listaVentas != null && listaVentas.size() > 0){
			response = new ReporteCMensualResponse();
			response.setProveedores(listaVentas);
			float totalCompras = reporteDao.getTotalCompras(request.getIdUsuario(), 
					Util.getFechaInicioMes(request.getMes()), Util.getFechaFinMes(request.getMes()));
			if(totalCompras != 0){
				response.setTotalVentas(totalCompras);
			}
		}
		return response;
	}
	
	private List<ReporteVSemana> getFechasSemana(int mes){
		List<ReporteVSemana> listaSemanas = new ArrayList<ReporteVSemana>();
		mes = mes -1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");  
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		int semanasMes = calendar.get(Calendar.WEEK_OF_MONTH);
		
		for(int i=0; i<semanasMes;i++){
			ReporteVSemana semana = new ReporteVSemana();
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, mes);
			calendar.set(Calendar.WEEK_OF_MONTH, i);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			semana.setFechasInicio(sdf.format(calendar.getTime()));
			
			int j = i + 1;
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, mes);
			calendar.set(Calendar.WEEK_OF_MONTH, j);
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			semana.setFechasFin(sdf.format(calendar.getTime()));
			listaSemanas.add(semana);
		}
		return listaSemanas;
	}
	
	private void setFechasPorMes(ReporteRequest request){
		int mes = request.getMes() - 1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");  
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.MONTH,mes);
		log.info("Fecha Reportes:" + sdf.format(calendar.getTime()));

		calendar.set(Calendar.DAY_OF_MONTH,1);
		request.setFechaInicio(sdf.format(calendar.getTime()));
		
		int ultimoDiaMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,ultimoDiaMes);
		
		request.setFechaFin(sdf.format(calendar.getTime()));
	}
	
	private String quitarComa(String text){
		return text.substring(0,text.length()-1);
	}
	
	private float getPorcentajeVenta(ReporteRequest request) throws Exception {
		float porcentaje = 0;
		float totalVentas = 0;
		float totalInventario = 0;
		List<VentaArticulos> listVentas = reporteDao.getVentaPorUsuario(request);
		if(listVentas != null && listVentas.size() > 0){
			for(VentaArticulos bean : listVentas){
				 ProductoInventario articulo = productoDao.getPorId(bean.getArticuloId());
				 if(articulo != null){
					 float precioTotal = bean.getNumeroArticulos() * articulo.getPrecioVenta();
					 totalVentas = totalVentas + (precioTotal);
				 }
			}
		}
		
		totalInventario = reporteDao.getTotalInventarioVenta(request.getIdUsuario());
		porcentaje = (100 * totalVentas) / totalInventario;
		
		return porcentaje;
	}
	
	private float getPorcentajeCompra(ReporteRequest request) throws Exception {
		float porcentaje = 0;
		float totalcompras = 0;
		float totalInventario = 0;
		List<PedidoProveedor> listCompras = reporteDao.getComprasPorUsuario(request);
		if(listCompras != null && listCompras.size() > 0){
			for(PedidoProveedor bean : listCompras){
				 ProductoInventario articulo = productoDao.getPorId(bean.getArticuloId());
				 if(articulo != null){
					 float precioTotal = bean.getNumeroArticulos() * articulo.getPrecioCosto();
					 totalcompras = totalcompras + (precioTotal);
				 }
			}
		}
		
		totalInventario = reporteDao.getTotalInventarioCosto(request.getIdUsuario());
		porcentaje = (100 * totalcompras) / totalInventario;
		
		return porcentaje;
	}
	
	private List<PorcentajeSemanal> getPorcentajePorTipoPago(ReporteRequest request) throws Exception {
		List<PorcentajeSemanal> lista = new ArrayList<PorcentajeSemanal>();
		float totalVenta1 = 0;
		float totalVenta2 = 0;
		float totalVenta3 = 0;
		float totalInventario = 0;
		
		List<VentaArticulos> listVentas = reporteDao.getTipoPagoPorUsuario(request);
		if(listVentas != null && listVentas.size() > 0){
			for(VentaArticulos bean : listVentas){
				ProductoInventario articulo = productoDao.getPorId(bean.getArticuloId());
				if(articulo != null){
					if(bean.getTipoPagoId() == 1){
						float precioTotal = bean.getNumeroArticulos() * articulo.getPrecioVenta();
						totalVenta1 = totalVenta1 + (precioTotal);
					} else if(bean.getTipoPagoId() == 2){
						float precioTotal = bean.getNumeroArticulos() * articulo.getPrecioVenta();
						totalVenta2 = totalVenta2 + (precioTotal);
					} else if(bean.getTipoPagoId() == 3){
						float precioTotal = bean.getNumeroArticulos() * articulo.getPrecioVenta();
						totalVenta3 = totalVenta3 + (precioTotal);
					}
					
				}
			}
		}
		totalInventario = reporteDao.getTotalInventarioVenta(request.getIdUsuario());
		
		PorcentajeSemanal porcentaje = new PorcentajeSemanal();
		porcentaje.setIdTipoPago(1);
		porcentaje.setNombrePago(Constantes.TIPO_PAGO_EFECTIVO);
		porcentaje.setPorcentaje((100 * totalVenta1) / totalInventario);
		lista.add(porcentaje);
		
		porcentaje = new PorcentajeSemanal();
		porcentaje.setIdTipoPago(2);
		porcentaje.setNombrePago(Constantes.TIPO_PAGO_TARJETA);
		porcentaje.setPorcentaje((100 * totalVenta2) / totalInventario);
		lista.add(porcentaje);
		
		porcentaje = new PorcentajeSemanal();
		porcentaje.setIdTipoPago(3);
		porcentaje.setNombrePago(Constantes.TIPO_PAGO_CODI);
		porcentaje.setPorcentaje((100 * totalVenta3) / totalInventario);
		lista.add(porcentaje);
		
		return lista;
	}
	

}
