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

import mx.com.fincomun.tenderos.bean.ProductoInventarioResponse;
import mx.com.fincomun.tenderos.bean.ProductoRequest;
import mx.com.fincomun.tenderos.bean.ProductoResponse;
import mx.com.fincomun.tenderos.bean.Response;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.ProductoService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class ProductoController {
	
	private static Logger log = Logger.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ValidaTokenService validaTokenService;

	@RequestMapping(value = "/restful/producto/alta", method = RequestMethod.POST)
	public @ResponseBody String altaProducto(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/producto/alta] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestAlta(request);
				if(error == null){
					int isInsert;
					try {
						isInsert = productoService.altaProducto(request);
						if(isInsert == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isInsert == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_PRODUCTO_EXISTE);
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
	
	@RequestMapping(value = "/restful/producto/actualizacion", method = RequestMethod.POST)
	public @ResponseBody String actualizacionProducto(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/producto/actualizacion] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestActualizacion(request);
				if(error == null){
					boolean isInsert;
					try {
						isInsert = productoService.actualizacionProducto(request);
						if(isInsert){
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
			}else {
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/elimina", method = RequestMethod.POST)
	public @ResponseBody String eliminaProducto(@RequestBody String requestString) {
		Response response = new Response();	
		log.info("ServicioRest [/restful/producto/elimina] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestElimina(request);
				if(error == null){
					boolean isDelete;
					try {
						isDelete = productoService.eliminaProducto(request);
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
	
	@RequestMapping(value = "/restful/producto/buscarPorNombre", method = RequestMethod.POST)
	public @ResponseBody String buscarPorNombre(@RequestBody String requestString) {
		ProductoResponse response = null;
		log.info("ServicioRest [/restful/producto/buscarPorNombre] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				if(request != null){
					if(request.getNombre() != null && request.getNombre().trim().length() > 0 && request.getIdUsuario() != 0){
						try {
							response = productoService.buscarPorNombre(request.getNombre(), request.getIdUsuario());
							if(response != null){
								response.setCodError(Constantes.CODIGO_EXITO);
								response.setMsjError(Constantes.MSJ_EXITO);
							} else {
								response = new ProductoResponse();
								response.setCodError(Constantes.CODIGO_ERROR);
								response.setMsjError(Constantes.MSJ_ERROR_PRODUCTO_NO_EXISTE);
							}
						} catch (Exception e) {
							log.error("Error: "+e.getCause());
							response = new ProductoResponse();
							response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
							response.setMsjError(Constantes.MSJ_ERROR);
						}
					} else {
						response = new ProductoResponse();
						response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
						response.setMsjError("El nombre del producto es null");
					}
				}
			} else {
				response = new ProductoResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/buscarPorQR", method = RequestMethod.POST)
	public @ResponseBody String buscarPorQR(@RequestBody String requestString) {
		ProductoResponse response = null;	
		log.info("ServicioRest [/restful/producto/buscarPorQR] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getCodigoQR() != null && request.getCodigoQR().trim().length() > 0 && request.getIdUsuario() != 0){
					try {
						response = productoService.buscarPorQR(request.getCodigoQR(), request.getIdUsuario());
						if(response != null){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_PRODUCTO_NO_EXISTE);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El CodigoQR del producto es null");
				}
			} else {
				response = new ProductoResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/desc", method = RequestMethod.POST)
	public @ResponseBody String getListaArticulosDesc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/desc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_NOMBRE_DESC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/asc", method = RequestMethod.POST)
	public @ResponseBody String getListaArticulosAsc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/asc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_NOMBRE_ASC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}			
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/precio/venta/desc", method = RequestMethod.POST)
	public @ResponseBody String getArticulosPrecioVDesc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/precio/venta/desc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_VENTA_DESC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/precio/venta/asc", method = RequestMethod.POST)
	public @ResponseBody String getArticulosPrecioVAsc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/precio/venta/asc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_VENTA_ASC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/precio/compra/desc", method = RequestMethod.POST)
	public @ResponseBody String getArticulosPrecioCDesc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/precio/compra/desc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_DESC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	@RequestMapping(value = "/restful/producto/lista/precio/compra/asc", method = RequestMethod.POST)
	public @ResponseBody String getArticulosPrecioCAsc(@RequestBody String requestString) {
		ProductoInventarioResponse response = null;	
		log.info("ServicioRest [/restful/producto/lista/precio/compra/asc] .... ");
		
		ProductoRequest request = converJsonToProducto(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				if(request.getIdUsuario() != 0){
					try {
						response = productoService.getProductosPorFiltro(Constantes.BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_ASC, request.getIdUsuario());
						if(response != null && response.getProductos().size() > 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else {
							response = new ProductoInventarioResponse();
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_PRODUCTOS);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new ProductoInventarioResponse();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response = new ProductoInventarioResponse();
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError("El IdUsuario es null");
				}
			} else {
				response = new ProductoInventarioResponse();
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response = new ProductoInventarioResponse();
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return getProductoInventarioResponse(response);
	}
	
	private String validateRequestAlta(ProductoRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Alta: [ "+request.toString()+" ]");
			
			if(request.getCodigoQR() == null || request.getCodigoQR().length() == 0){
				error = "Codigo es vacio";
			} else if(request.getIdProveedor() == 0){
				error = "IdProveedor es null";
			} else if(request.getIdUsuario() == 0){
				error = "IdUsuario es null";
			} else {
				error = validateRequest(request);
			}
		}
		return error;
	}
	
	private String validateRequestActualizacion(ProductoRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Actualizacion: [ "+request.toString()+" ]");
			
			if(request.getIdProducto() == 0){
				error = "IdProducto es null";
			}else {
				error = validateRequest(request);
			}
		}
		return error;
	}
	
	private String validateRequestElimina(ProductoRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request Elimina: [ "+request.toString()+" ]");
			if(request.getIdProducto() == 0){
				error = "Id Producto es vacio";
			}
		}
		return error;
	}
	
	private String validateRequest(ProductoRequest request){
		String error = null;
		if(request != null){
			if(request.getNombre() == null || request.getNombre().length() == 0){
				error = "El nombre de producto es vacio";
			} else if(request.getPrecioCosto() == 0){
				error = "El Precio de compra es 0, ingresa un valor";
			} else if(request.getPrecioVenta() == 0){
				error = "El Precio de venta es 0, ingresa un valor";
			} else if(request.getStock() == 0){
				error = "El numero de articulos es 0, ingresa un valor";
			}
		}
		return error;
	}
	
	private ProductoRequest converJsonToProducto(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		ProductoRequest productoRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			productoRequest = mapper.readValue(request, ProductoRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return productoRequest;
	}
	
	private String getProductoResponse(ProductoResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
	
	private String getProductoInventarioResponse(ProductoInventarioResponse response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return Util.getEncrypt(jsonString);
	}
}
