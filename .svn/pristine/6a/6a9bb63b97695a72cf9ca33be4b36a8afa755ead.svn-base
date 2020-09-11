package mx.com.fincomun.tenderos.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.fincomun.tenderos.bean.Response;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.bean.VentaDirectaRequest;
import mx.com.fincomun.tenderos.bean.VentaPorArticuloRequest;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.service.VentasService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class VentasController {
	
	private static Logger log = Logger.getLogger(VentasController.class);
	
	@Autowired
	private VentasService ventasService;
	
	@Autowired
	private ValidaTokenService validaTokenService;

	@RequestMapping(value = "/restful/venta/directa", method = RequestMethod.POST)
	public @ResponseBody String ventaDirecta(@RequestBody String requestString) {
		Response response = new Response(); 
		log.info("ServicioRest [/restful/venta/directa] .... ");
		
		VentaDirectaRequest request = converJsonToVentaDirecta(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateVentaDirecta(request);
				if(error == null){
					int isAltaVenta;
					try {
						isAltaVenta = ventasService.altaVentaDirecta(request);
						if(isAltaVenta == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isAltaVenta == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_TIPO_PAGO);
						} else if(isAltaVenta == -2){
							response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
							response.setMsjError(Constantes.MSJ_ERROR);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new Response();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	@RequestMapping(value = "/restful/venta/articulo", method = RequestMethod.POST)
	public @ResponseBody String ventaPorArticulo(@RequestBody String requestString) {
		Response response = new Response(); 
		log.info("ServicioRest [/restful/venta/articulo] .... ");
		
		VentaPorArticuloRequest request = converJsonToVentaArticulo(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateVentaPorArticulo(request);
				if(error == null){
					int isAltaVenta;
					try {
						isAltaVenta = ventasService.altaVentaPorArticulo(request);
						if(isAltaVenta == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isAltaVenta == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_TIPO_PAGO);
						} else if(isAltaVenta == -2){
							response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
							response.setMsjError(Constantes.MSJ_ERROR);
						} else if(isAltaVenta == -3){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_STOCK);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new Response();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	private String validateVentaDirecta(VentaDirectaRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request venta directa: [ "+request.toString()+" ]");
			if(request.getMontoTotal() == 0){
				error = "Monto total es 0";
			} else if(request.getIdTipoPago() == 0){
				error = "IdTipoPago es 0";
			} else if(request.getIdUsuario() == 0){
				error = "IdUsuario es 0";
			} 
		} 
		return error;
	}
	
	private String validateVentaPorArticulo(VentaPorArticuloRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request venta por articulo: [ "+request.toString()+" ]");
			if(request.getMontoTotal() == 0){
				error = "Monto total es 0";
			} else if(request.getIdTipoPago() == 0){
				error = "IdTipoPago es 0";
			} else if(request.getIdUsuario() == 0){
				error = "IdUsuario es 0";
			} else if(request.getCodigoQR() == null && request.getCodigoQR().trim().length() == 0){
				error = "CodigoQR es vacio";
			} else if(request.getListaProductos() == null || request.getListaProductos().size() == 0){
				error = "La lista de los productos es vacia";
			}
		} 
		return error;
	}
	
	private VentaPorArticuloRequest converJsonToVentaArticulo(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		VentaPorArticuloRequest ventaPorArticuloRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			ventaPorArticuloRequest = mapper.readValue(request, VentaPorArticuloRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return ventaPorArticuloRequest;
	}
	
	private VentaDirectaRequest converJsonToVentaDirecta(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		VentaDirectaRequest ventaDirectaRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			ventaDirectaRequest = mapper.readValue(request, VentaDirectaRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return ventaDirectaRequest;
	}
}
