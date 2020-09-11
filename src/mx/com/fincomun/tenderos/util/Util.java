package mx.com.fincomun.tenderos.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.fincomun.tenderos.bean.Response;

public class Util {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static Logger log = Logger.getLogger(Util.class);
	
	private static  SimpleDateFormat dateFormat;
	
	public static boolean isEmail(String text){
		boolean isValid = false;
		if(text.matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$"))
			isValid = true;
		return isValid;
	}
	
	public static boolean isNumerico(String text){
		boolean isValid = false;
		if(text.matches("[0-9 ]*"))
			isValid = true;
		return isValid;
	}
	
	public static boolean isAlfanumerico(String text){
		boolean isValid = false;
		if(text.matches("[0-9a-zA-Z ]*"))
			isValid = true;
		return isValid;
	}
	
	public static boolean isDecimal(String text){
		boolean isValid = false;
		if(text.matches("[0-9]*\\.?[0-9]*"))
			isValid = true;
		return isValid;
	}
	
	public static String getFechaYYYYMMDD(Date date){
		dateFormat=new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}
	
	public static String getFechaDDMMYY(Date date){
		dateFormat = new SimpleDateFormat("dd-MM-yy"); 
		return dateFormat.format(date);
	}
	
	public static String getFechaDDMESYYYY(String text){
		String fechaFormat = "";
		if(text != null && text.trim().length() > 0){
			String[] fechaArray = text.split("-");
			String mes = mesText(Integer.parseInt(fechaArray[1]));
			
			fechaFormat = fechaArray[0]+" "+mes+" "+fechaArray[2];
		}
		return fechaFormat;
	}
	
	private static String mesText(int mes){
		String mesText = "";
		switch (mes) {
			case 1: mesText = "Enero"; break;
			case 2: mesText = "Febrero"; break;
			case 3: mesText = "Marzo"; break;
			case 4: mesText = "Abril"; break;
			case 5: mesText = "Mayo"; break;
			case 6: mesText = "Junio"; break;
			case 7: mesText = "Julio"; break;
			case 8: mesText = "Agosto"; break;
			case 9: mesText = "Septiembre"; break;
			case 10: mesText = "Octubre"; break;
			case 11: mesText = "Noviembre"; break;
			case 12: mesText = "Diciembre"; break;
		}
		return mesText;
	}
	
	public static String getFechaInicioMes(int mes){
		dateFormat = new SimpleDateFormat("dd-MM-yy");  
		mes = mes -1;
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return dateFormat.format(calendar.getTime());
	}
	
	
	public static String getFechaFinMes(int mes){
		dateFormat = new SimpleDateFormat("dd-MM-yy");  
		mes = mes -1;
		Calendar calendar = Calendar.getInstance(); 
		int ultimoDiaMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DAY_OF_MONTH,ultimoDiaMes);
		return dateFormat.format(calendar.getTime());
	}
	
	public static String getFechaFinMesActual(){
		dateFormat = new SimpleDateFormat("dd-MM-yy");  
		Calendar calendar = Calendar.getInstance(); 
		int ultimoDiaMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,ultimoDiaMes);
		return dateFormat.format(calendar.getTime());
	}
	
	public static boolean validateFormatoDate(String text){
		boolean isValid = false;
		if(text.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"))
			isValid = true;
		return isValid;
	}
	
	public static long convertStringToLong(String text){
		Long textLong = Long.parseLong(text);
		return textLong;
	}
	
	public static String converObjectToJson(Response response){
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			log.error("Error al convertir a JSON..."+response);
		}
		return jsonInString;
	}
	
	public static String getResponse(Response response){
		String jsonString = JSONValue.escape(Util.converObjectToJson(response));
		return getEncrypt(jsonString);
	}
	
	public static String getEncrypt(String jsonString){
		String responseEncrypt = null;
		log.info("Response: ["+jsonString+"]");
		try {
			responseEncrypt = StringEncrypt.encrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV, jsonString);
			log.info("ResponseEnrypt: ["+responseEncrypt+"]");
		} catch (Exception e) {
			log.error("Ocurrio un error al encriptar response: "+e.getCause());
		}
		return responseEncrypt;
	}
	
	public static void main(String...strings){
		try {
			String encrypt = StringEncrypt.encrypt("FINCOMUN07052019", "07052019FINCOMUN", "{\"nombre\":\"Donas\",\"precioVenta\":10.5,\"precioCosto\":8.4,\"stock\":3,\"idProducto\":2,\"tokenJwt\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJGaW5jb211bkpXVCIsInN1YiI6ImZ6ZDlxcmJKdmtnUVZ4Tit4SWMxcmptRERHTkZRd1BJSEJhSHUrWC8ra0NIUEtEczVwUjZ0cVVpZmFtTU02dmVCZjhrVW9kdzRadmVoNE5hSU9Majhqek9KeU5HeFZWMWdjTFNiS3FTTktwenFMZ1pyYlFyd0tua3kyUnkvbXVqVHg3NFcrK0NlZjd2VjFRaGR6TWNBUW4xVFVUS1NXcWY0a3pxQXp3bkdURT0iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNTY5NTE5NDk0LCJleHAiOjE1Njk1MjAzOTR9.ovv5LUqEVPbI_8hNT7FsRXjSbjHdBs1tXSNJd_wYngw4Xdj4UdHYZ4F_QLdArtl7_Vfw8LEIjfMsm3C-3QCu9A\"}");
			System.out.println("Encrypt: ["+encrypt+"]");
			
			String decrypt = StringEncrypt.decrypt("FINCOMUN07052019", "07052019FINCOMUN", encrypt);
			System.out.println("Decrypt: ["+decrypt+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}