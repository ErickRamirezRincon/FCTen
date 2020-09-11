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

import mx.com.fincomun.tenderos.bean.ReporteCMensualResponse;
import mx.com.fincomun.tenderos.bean.ReporteRequest;
import mx.com.fincomun.tenderos.bean.ReporteVCMesResponse;
import mx.com.fincomun.tenderos.bean.ReporteVDiaResponse;
import mx.com.fincomun.tenderos.bean.ReporteVMensualResponse;
import mx.com.fincomun.tenderos.bean.ReporteVSemanaResponse;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.ReporteService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class ReporteController {
	
private static Logger log = Logger.getLogger(ReporteController.class);
	
	@Autowired
	private ReporteService reporteService;
	
	@Autowired
	private ValidaTokenService validaTokenService;

	@RequestMapping(value = "/restful/reporte/venta/compra/mes", method = RequestMethod.POST)
	public @ResponseBody String reporteVCMes(@RequestBody String requestString) {
		ReporteVCMesResponse response = null;
		log.info("ServicioRest [/restful/reporte/venta/compra/mes] .... ");
		
		ReporteRequest request = converJsonToReporte(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateReporte(request);
				if(error == null){
					try {
						response = reporteService.getReporteMes(request);
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ReporteVCMesResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ReporteVCMesResponse();
						response.setCodError(Constantes.CODIGO_ERROR);
						response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
					}
				} else {
					response = new ReporteVCMesResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response = new ReporteVCMesResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ReporteVCMesResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getReporteVCMesResponse(response);
	}
	
	@RequestMapping(value = "/restful/reporte/venta/dia", method = RequestMethod.POST)
	public @ResponseBody String reporteVDia(@RequestBody String requestString){
		ReporteVDiaResponse response = null;
		log.info("ServicioRest [/restful/reporte/venta/dia] .... ");
		
		ReporteRequest request = converJsonToReporte(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateReporteVDia(request);
				if(error == null){
					try {
						response = reporteService.getReporteVentaDia(request);
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ReporteVDiaResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ReporteVDiaResponse();
						response.setCodError(Constantes.CODIGO_ERROR);
						response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
					}
				} else {
					response = new ReporteVDiaResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response = new ReporteVDiaResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ReporteVDiaResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getReporteVDiaResponse(response);
	}
	
	@RequestMapping(value = "/restful/reporte/venta/semana", method = RequestMethod.POST)
	public @ResponseBody String reporteVSemana(@RequestBody String requestString){
		ReporteVSemanaResponse response = null;
		log.info("ServicioRest [/restful/reporte/venta/semana] .... ");
		
		ReporteRequest request = converJsonToReporte(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateReporte(request);
				if(error == null){
					try {
						response = reporteService.getReporteVentaSemana(request);
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ReporteVSemanaResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ReporteVSemanaResponse();
						response.setCodError(Constantes.CODIGO_ERROR);
						response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
					}
				} else {
					response = new ReporteVSemanaResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response = new ReporteVSemanaResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}	
		} else {
			response = new ReporteVSemanaResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getReporteVSemanaResponse(response);
	}
	
	@RequestMapping(value = "/restful/reporte/venta/mes", method = RequestMethod.POST)
	public @ResponseBody String reporteVMensual(@RequestBody String requestString){
		ReporteVMensualResponse response = null;
		log.info("ServicioRest [/restful/reporte/venta/mes] .... ");
		
		ReporteRequest request = converJsonToReporte(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateReporte(request);
				if(error == null){
					try {
						response = reporteService.getReporteVentaMensual(request);
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ReporteVMensualResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ReporteVMensualResponse();
						response.setCodError(Constantes.CODIGO_ERROR);
						response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
					}
				} else {
					response = new ReporteVMensualResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response = new ReporteVMensualResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ReporteVMensualResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getReporteVMensualResponse(response);
	}
	
	@RequestMapping(value = "/restful/reporte/compras/proveedor", method = RequestMethod.POST)
	public @ResponseBody String reporteCMensual(@RequestBody String requestString){
		ReporteCMensualResponse response = null;
		log.info("ServicioRest [/restful/reporte/compras/proveedor] .... ");
		
		ReporteRequest request = converJsonToReporte(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateReporte(request);
				if(error == null){
					try {
						response = reporteService.getReporteCompraMensual(request);
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ReporteCMensualResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ReporteCMensualResponse();
						response.setCodError(Constantes.CODIGO_ERROR);
						response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_DATOS);
					}
				} else {
					response = new ReporteCMensualResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response = new ReporteCMensualResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ReporteCMensualResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getReporteCMensualResponse(response);
	}
	
	private String validateReporte(ReporteRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request ReporteVCMes: [ "+request.toString()+" ]");
			if(request.getIdUsuario()== 0){
				error = "Id Usuario es vacio";
			} else if(request.getMes() == 0){
				error = "Mes es vacio";
			}
		}
		return error;
	}
	
	private String validateReporteVDia(ReporteRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request ReporteVCMes: [ "+request.toString()+" ]");
			if(request.getIdUsuario() == 0){
				error = "Id Usuario es vacio";
			} else if(request.getFecha().trim().length() == 0){
				error = "Fecha es vacio";
			} else if(!Util.validateFormatoDate(request.getFecha())){
				error = "El fomato de la fecha es invalido, formato valido dd/mm/yyyy";
			}
		}
		return error;
	}
	
	private ReporteRequest converJsonToReporte(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		ReporteRequest reporteRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			reporteRequest = mapper.readValue(request, ReporteRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return reporteRequest;
	}
	
	private String getReporteVCMesResponse(ReporteVCMesResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
	
	private String getReporteVDiaResponse(ReporteVDiaResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
	
	private String getReporteVSemanaResponse(ReporteVSemanaResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
	
	private String getReporteVMensualResponse(ReporteVMensualResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
	
	private String getReporteCMensualResponse(ReporteCMensualResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
}