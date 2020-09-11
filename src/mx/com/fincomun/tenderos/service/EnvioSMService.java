/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.fincomun.tenderos.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import mx.com.fincomun.tenderos.bean.EnvioSMSBean;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.TelefonoUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author desarrollo5
 */
@Component
public class EnvioSMService {
    
    private static final Logger log = Logger.getLogger(EnvioSMService.class);
    
    
//primero es el número de celular para SMS y el token es elnumero de 4 digitos.
    /**
     * @param numeroTelefono telefono del cliente
     * @param token token a enviar al cliente
     * @return estatus del envio
     */
    public boolean msjTokenMX(EnvioSMSBean datos) {
        log.info("EnvioSMS.msjTokenMX.class");
        String region = Constantes.SMS.REGION_MX;  //->52
        //String mensaje = Constantes.SMS.MSJ_TOKEN; //-> Tu token de acceso es {0}...
        String cadena="";
        if (datos != null) {
            cadena=("DATOS DE VENTA:"+"\n");
            cadena=cadena+("*Nombre: "+datos.getNombre()+"\n");
            cadena=cadena+("*Fecha: "+datos.getFecha()+"\n");
            cadena=cadena+("*Total: "+datos.getMonto()+"\n");
            log.info("Mensaje a enviar:\n" + cadena);
            return enviarSms(region, String.valueOf(datos.getCelular()), cadena);
        } else {
            log.error("Error en el mensaje a enviar");
            return false;
        }
    }
    
    /**
     * Realiza el envio de mensajes a traves de smsmasivos.com.mx     *
     * @param region clave de la region
     * @param numeroTelefono numero telefonico
     * @param mensaje contenido del mensaje
     */
    private boolean enviarSms(String region, String numeroTelefono, String mensaje) {
        log.info("EnvioSMS.enviarSMS.class");
        String urlRequest = Constantes.SMS.URL_REQUEST_MX;
        String apiKey = Constantes.SMS.API_KEY_MX;
        boolean isExito = false;
        mensaje = TelefonoUtils.validaMensaje(mensaje);
        if (TelefonoUtils.validaRegion(region) && TelefonoUtils.validaTelefono(numeroTelefono) && mensaje != null) {
            try {
                String urlParametros = "apikey=" + URLEncoder.encode(apiKey, "UTF-8")
                        + "&mensaje=" + URLEncoder.encode(mensaje, "UTF-8")
                        + "&numcelular=" + URLEncoder.encode(numeroTelefono, "UTF-8")
                        + "&numregion=" + URLEncoder.encode(region, "UTF-8");
                log.info("urlParametros:\n" + urlParametros);
                URL url;
                URLConnection conexion = null;
                //////////
                // carga archivo con servidores certificados
                // /opt/oracle/product/jdk1.6.0_121/jre/lib/security QA
                // /opt/oracle/product/java/jdk1.6.0_121/jre/lib/security des
                //InputStream fileCertificadosConfianza = new FileInputStream(new File("/opt/oracle/product/java/jdk1.6.0_121/jre/lib/security/cacerts"));
                InputStream fileCertificadosConfianza = new FileInputStream(new File(Constantes.listaServers));
                KeyStore ksCertificadosConfianza = KeyStore.getInstance(KeyStore.getDefaultType());
                ksCertificadosConfianza.load(fileCertificadosConfianza, "changeit".toCharArray());
                fileCertificadosConfianza.close();
                // se coloca el contenido en un contenedor de confianza
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ksCertificadosConfianza);
                // se crea SSL con los certificados de confianza
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);
                SSLSocketFactory sslSocketFactory = context.getSocketFactory();

                //////////
                try {
                    //-----------------------crear conexion----------------------------
                    log.info("Creando conexion:\n");
                    url = new URL(null, urlRequest, new sun.net.www.protocol.https.Handler());
                    conexion = url.openConnection();
                    ((HttpsURLConnection) conexion).setSSLSocketFactory(sslSocketFactory);
                    
                    //conexion.setRequestMethod("POST");
                    conexion.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
                    conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
                    conexion.setRequestProperty("Content-Language", "en-US");
                    conexion.setUseCaches(false);
                    conexion.setDoInput(true);
                    conexion.setDoOutput(true);
                    conexion.setDefaultUseCaches(false);
                    
                    conexion.connect();
                    log.info("Conexion Creada:\n");
                    //--------------------Enviar Peticion------------------------------
                    log.info("Envio de parametros\n");                    
                    DataOutputStream wr = new DataOutputStream(conexion.getOutputStream());
                    wr.writeBytes(urlParametros);
                    wr.flush();
                    wr.close();
                    log.info("Parametros enviados\n"+urlParametros);
                    //-----------------------Obtener Respuesta-------------------------
                    log.info("Se obtiene la respuesta\n");
                    InputStream is = conexion.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    StringBuilder responsee = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        responsee.append(line);
                        responsee.append('\r');
                    }
                    rd.close();
                    String respuesta = responsee.toString();
                    log.info("Respuesta: "+respuesta);
                    //-------------Decodificar JSON con libreria org.json--------------
                    JSONObject jsonObject = new JSONObject(respuesta);
                    if (jsonObject.getString("estatus").equals("ok")) {
                        log.info("Mensaje respuesta ok: " + jsonObject.getString("mensaje"));
                        log.info("Referencia: " + jsonObject.getString("referencia"));
                        isExito = true;
                    } else {
                        log.info("Mensaje respuesta error: " + jsonObject.getString("mensaje"));
                        log.info("Codigo error: " + jsonObject.getString("codigo"));
                    }
                    //-----------------------------------------------------------------
                } catch (Exception e) {
                    log.error("Exception", e);
                } finally {
                    if (conexion != null) {
                        //conexion.disconnect();
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.error("UnsupportedEncodingException", e);
            } catch (Exception e) {
                log.error("Exception", e);
            }
        }
        return isExito;
    }
}
