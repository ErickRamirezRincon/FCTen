package mx.com.fincomun.tenderos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TenderosProperties {
	
	private static Logger log = Logger.getLogger(TenderosProperties.class);

    public static Properties cargaConfiguracion(String archPropiedades) {
        Properties propiedades = null;
        propiedades = new Properties();
        try {
            File f = new File(archPropiedades);
            if (!f.exists()) {
                log.info("No se encuentra el archivo de propiedades ");
                return null;
            }

            FileInputStream flujoEntrada = new FileInputStream(archPropiedades);
            try {
                propiedades.load(flujoEntrada);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            log.info("NO se encuentra el archivo de propiedades ");
            e.printStackTrace();
            return null;
        }
        return propiedades;
    }

}
