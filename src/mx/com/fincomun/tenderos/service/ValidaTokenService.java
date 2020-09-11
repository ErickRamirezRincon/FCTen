package mx.com.fincomun.tenderos.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.util.Constantes;

@Service
public class ValidaTokenService {

	private static Logger log = Logger.getLogger(ValidaTokenService.class);
	
	public ValidaTokenRespose validaToken(String token) {
		log.info("Validando Token: ["+token+"]");
		ValidaTokenRespose validaTokenRespose = new ValidaTokenRespose();
		ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Authorization", token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        
        try {
        	log.info("Enviando peticion a ["+Constantes.VALIDA_SERVICE_TOKEN+"]");
            String response = restTemplate.postForObject(Constantes.VALIDA_SERVICE_TOKEN, request, String.class);
        	
        	log.info("Response ValidaToken POST: ["+response+"]");
			validaTokenRespose = mapper.readValue(response, ValidaTokenRespose.class);
			
		} catch (JsonParseException e) {
			log.error("Error al convertir JSON: "+e.getCause());
			validaTokenRespose.setCodigo(-1);
			validaTokenRespose.setMensaje(Constantes.MSJ_ERROR_TOKEN);
		} catch (JsonMappingException e) {
			log.error("Error al convertir JSON: "+e.getCause());
			validaTokenRespose.setCodigo(-1);
			validaTokenRespose.setMensaje(Constantes.MSJ_ERROR_TOKEN);
		} catch (IOException e) {
			log.error("Error al convertir JSON: "+e.getCause());
			validaTokenRespose.setCodigo(-1);
			validaTokenRespose.setMensaje(Constantes.MSJ_ERROR_TOKEN);
		} catch(Exception e){
			log.error("Error: "+e.getMessage());
			validaTokenRespose.setCodigo(-1);
			validaTokenRespose.setMensaje(Constantes.MSJ_ERROR_TOKEN);
		}
        log.info("Response ValidaToken: "+validaTokenRespose.toString());
        
        return validaTokenRespose;
    }
	
	
}
