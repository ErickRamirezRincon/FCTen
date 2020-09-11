package mx.com.fincomun.tenderos.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.TipoPago;
import mx.com.fincomun.tenderos.bean.TipoPagoResponse;
import mx.com.fincomun.tenderos.dao.CatalogoDao;

@Service
public class CatalogoService {
	
	@Autowired
	private CatalogoDao catalogoDao;
	
	private static Logger log = Logger.getLogger(CatalogoService.class);
	
	public TipoPagoResponse getTipoPagos(){
		TipoPagoResponse response = null;
		List<TipoPago> tiposPagos = catalogoDao.getListProductos();
		
		if(tiposPagos != null && tiposPagos.size() > 0){
			response = new TipoPagoResponse();
			response.setTipoPagos(tiposPagos);
			log.info("Se encontraron ["+tiposPagos.size()+"] Tipos de Pago.");
		}
		
		return response;
	}

}
