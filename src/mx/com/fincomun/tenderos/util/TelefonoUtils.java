/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.fincomun.tenderos.util;

import org.apache.commons.lang3.StringUtils;;
import org.apache.log4j.Logger;

/**
 *
 * @author ErickRam
 */
public class TelefonoUtils {
    private static final Logger log = Logger.getLogger(TelefonoUtils.class);
    
    public static String validaMensaje(String mensaje) {
        String mensajeResult = null;
        if (mensaje != null && mensaje.length() <= 160) {
            mensajeResult = StringUtils.stripAccents(mensaje);
            log.info("mensaje.length(): " + mensajeResult.length());
            log.info("mensaje valido: \n" + mensajeResult);
        } else {
            log.error("mensaje no valido");
        }
        return mensajeResult;
    }
    
    public static boolean validaRegion(String region) {
        if ( (region != null)  && (region.length() == 2) && (StringUtils.isNumeric(region))  ) {
            log.info("region valida: " + region);
            return true;
        } else {
            log.error("region no valida");
            return false;
        }
    }
    
    public static boolean validaTelefono(String numeroTelefonoString) {
        if (    (numeroTelefonoString != null)  &&  (numeroTelefonoString.length() == 10)
                && (StringUtils.isNumeric(numeroTelefonoString))    ) {
            log.info("telefono valido: " + numeroTelefonoString);
            return true;
        } else {
            log.error("telefono no valido");
            return false;
        }
    }
}
