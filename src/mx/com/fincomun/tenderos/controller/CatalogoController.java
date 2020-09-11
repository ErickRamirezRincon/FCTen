package mx.com.fincomun.tenderos.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.fincomun.tenderos.bean.Request;
import mx.com.fincomun.tenderos.bean.TipoPagoResponse;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.CatalogoService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class CatalogoController {
	
	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private ValidaTokenService validaTokenService;
	
	private static Logger log = Logger.getLogger(CatalogoController.class);

	@RequestMapping(value = "/restful/catalogo/pago", method = RequestMethod.POST)
	public @ResponseBody String getCatalogoTipoPago(String requestString) {
		TipoPagoResponse response = null;	
		log.info("ServicioRest [/restful/catalogo/pago] .... ");
		
		Request request = converJsonToRequest(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0) {
				response = catalogoService.getTipoPagos();
				if(response != null){
					response.setCodError(Constantes.CODIGO_EXITO);
					response.setMsjError(Constantes.MSJ_EXITO);
				} else {
					response = new TipoPagoResponse();
					response.setCodError(Constantes.CODIGO_ERROR);
					response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_TIPOS_PAGO);
				}
			} else {
				response = new TipoPagoResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new TipoPagoResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
		
	private Request converJsonToRequest(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		Request request = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String requestDecrypt = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			request = mapper.readValue(requestDecrypt, Request.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return request;
	}

}
