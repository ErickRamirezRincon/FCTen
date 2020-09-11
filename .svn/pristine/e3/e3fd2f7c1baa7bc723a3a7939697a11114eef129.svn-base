package mx.com.fincomun.tenderos.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.fincomun.tenderos.service.ProductoService;

@Component("ArticuloTask")
public class ArticuloTask {

	private static Logger log = Logger.getLogger(ArticuloTask.class);
	
	@Autowired
	private ProductoService productoService;
	
	public void initActuzalizaStock(){
		log.info("Actualizando Stock de articulos...");
		try {
			productoService.actualizacionCierreMes();
		} catch (Exception e) {
			log.error("Ocurrio un error al realizar actualizacion de stock en productos.");
		}
	}
	
}
