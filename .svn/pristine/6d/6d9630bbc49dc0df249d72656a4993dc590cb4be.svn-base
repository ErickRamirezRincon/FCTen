package mx.com.fincomun.tenderos.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.fincomun.tenderos.bean.ProveedorRequest;
import mx.com.fincomun.tenderos.bean.ProveedoresResponse;
import mx.com.fincomun.tenderos.bean.Response;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.ProveedorService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class ProveedorController {
	
	private static Logger log = Logger.getLogger(ProveedorController.class);
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private ValidaTokenService validaTokenService;
	
	@RequestMapping(value = "/restful/proveedor/alta", method = RequestMethod.POST)
	public @ResponseBody String altaProveedor(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/proveedor/alta] .... ");
		
		ProveedorRequest request = converJsonToProveedor(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestAlta(request);
				if(error == null){
					int isInsert;
					try {
						isInsert = proveedorService.altaProveedor(request);
						if(isInsert == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isInsert == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_PROVEEDOR_EXISTE);
						} else if(isInsert == -2){
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
	
	@RequestMapping(value = "/restful/proveedor/actualiza", method = RequestMethod.POST)
	public @ResponseBody String actualizaProveedor(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/proveedor/actualiza] .... ");
		
		ProveedorRequest request = converJsonToProveedor(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestModifica(request);
				if(error == null){
					boolean isUpdate;
					try {
						isUpdate = proveedorService.modificaProveedor(request);
						if(isUpdate){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
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
	
	@RequestMapping(value = "/restful/proveedor/elimina", method = RequestMethod.POST)
	public @ResponseBody String eliminaProveedor(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/proveedor/elimina] .... ");
		
		ProveedorRequest request = converJsonToProveedor(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestElimina(request);
				if(error == null){
					boolean isDelete;
					try {
						isDelete = proveedorService.eliminaProveedor(request);
						if(isDelete){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
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
	
	@RequestMapping(value = "/restful/proveedor/lista/desc", method = RequestMethod.POST)
	public @ResponseBody String getProveedores(@RequestBody String requestString) {
		ProveedoresResponse response = null;	
		log.info("ServicioRest [/restful/proveedor/lista/desc] .... ");
		
		ProveedorRequest request = converJsonToProveedor(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				log.info("IdUsuario: "+request.toString());
				
				if(request.getIdUsuario() != 0){
					try {
						response = proveedorService.getProveedores(request.getIdUsuario());
						if(response != null && response.getProveedores().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProveedoresResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PROVEEDORES);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProveedoresResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProveedoresResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProveedoresResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProveedoresResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProveedoresResponse(response);
	}
	
	private String validateRequestElimina(ProveedorRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Elimina: [ "+request.toString()+" ]");
			if(request.getIdProveedor() == 0){
				error = "Id Proveedor es vacio";
			}
		}
		return error;
	}
	
	private String validateRequestModifica(ProveedorRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Modifica: [ "+request.toString()+" ]");
			
			if(request.getIdProveedor() == 0){
				error = "Id Proveedor es vacio";
			} else {
				error = validateRequest(request);
			}
		}
		return error;
	}
	
	private String validateRequestAlta(ProveedorRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Alta: [ "+request.toString()+" ]");
			
			if(request.getNombreEmpresa() == null || request.getNombreEmpresa().length() == 0){
				error = "Nombre empresa es vacio";
			} else if(request.getIdUsuario() == 0){
				error = "IdUsuario es vacio";
			} else {
				error = validateRequest(request);
			}
		}
		return error;
	}
	
	private String validateRequest(ProveedorRequest request){
		String error = null;
		if(request != null){
			if(request.getNombreProveedor() == null || request.getNombreProveedor().length() == 0){
				error = "El nombre de proveedor es vacio";
			} 
			
			/*else if(request.getNumeroContacto() == null || request.getNumeroContacto().length() == 0){
				error = "El numero de contacto es vacio";
			} else if(request.getCorreo() == null || request.getCorreo().length() == 0){
				error = "Correo es vacio";
			} */
			
			if(request.getCorreo() != null && request.getCorreo().length() > 0){
				if(!Util.isEmail(request.getCorreo())){
					error = "El formato del correo es invalido";
				}
			}
			
			if(request.getNumeroContacto() != null && request.getNumeroContacto().length() > 0){
				if(!Util.isNumerico(request.getNumeroContacto())){
					error = "El numero de contacto debe ser numerico";
				}
			}
		}
		return error;
	}
	
	private ProveedorRequest converJsonToProveedor(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		ProveedorRequest proveedorRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			proveedorRequest = mapper.readValue(request, ProveedorRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return proveedorRequest;
	}
	
	private String getProveedoresResponse(ProveedoresResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}

}
