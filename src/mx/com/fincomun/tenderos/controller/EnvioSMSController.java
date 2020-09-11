/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.fincomun.tenderos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.fincomun.tenderos.bean.EnvioSMSBean;
import mx.com.fincomun.tenderos.bean.Response;
import mx.com.fincomun.tenderos.bean.ResponseSMSBean;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.EnvioSMService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ErickRam
 */
@Controller
public class EnvioSMSController {

    @Autowired
    private ValidaTokenService validaTokenService;

    @Autowired
    EnvioSMService SMService;

    private static Logger log = Logger.getLogger(EnvioSMSController.class);

    @RequestMapping(value = "/restful/envioSMS", method = RequestMethod.POST)
    public @ResponseBody String EnvioSMS(@RequestBody String requestString) throws Exception {
        ResponseSMSBean response = new ResponseSMSBean();
        log.info("ServicioRest [/restful/EnviaSMS] .... ");
        EnvioSMSBean bean = SMSJSONForm(requestString);
        log.info("Peticion Desencriptada: \n" + bean);
        
        ValidaTokenRespose validacionT = validaTokenService.validaToken(bean.getTokenJwt());               
        
        if (validacionT.getCodigo() == 0) {
            response.setMensaje(validaDatosSMS(bean));
            log.info("Validacion de datos: " + response.getMensaje());
            if (response.getMensaje() == null) {
                log.info("Datos a enviar: " + bean.toString());
                boolean respuestaMensaje = SMService.msjTokenMX(bean);
                if (respuestaMensaje) {
                    response.setCodigo(0);
                    response.setMensaje("Success");
                } else {
                    response.setCodigo(-1);
                }
            }else {
                response.setCodigo(-2);
            }
            log.info("finish");
        }else{
            response.setCodigo(-1);
            response.setMensaje("token expiro");
        }
        
        log.info("Respuesta: \n"+response);
        String respuesta=StringEncrypt.encrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV, response.toString());
        log.info("Respuesta Encruptada: \n"+respuesta);
        return respuesta;
    }

    public EnvioSMSBean SMSJSONForm(String encryptPeticion) {
        EnvioSMSBean bean = null;
        ObjectMapper mapper = new ObjectMapper();
        log.info("Peticion Encriptada: " + encryptPeticion);
        try {
            String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV, encryptPeticion);
            bean = mapper.readValue(request, EnvioSMSBean.class);

        } catch (Exception e) {
            log.info("Error al convertir la peticion");
        }
        return bean;
    }

    public String validaDatosSMS(EnvioSMSBean bean) {
        String valida = null;

        if (bean.getCelular().length() < 10) {
            valida= "Error en el telefono";
        } else if (bean.getFecha().isEmpty()) {
            valida="Error en la fecha";
        } else if (bean.getMonto() <= 0) {
            valida="El monto debe ser mayor a cero";
        } else if (String.valueOf(bean.getMonto()).isEmpty()) {
            valida="Monto no puede ir vacio";
        }else if (bean.getNombre().isEmpty() || bean.getNombre().equals("")){
            valida="Error en el nombre";
        }

        return valida;
    }
}
