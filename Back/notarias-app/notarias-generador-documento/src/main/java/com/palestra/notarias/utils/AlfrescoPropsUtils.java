package com.palestra.notarias.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que carga valores de configuracion de algresco desde
 * el archivo src/main/resources/alfresco.properties
 * @author sofia
 *
 */
public class AlfrescoPropsUtils {
	
	private static Properties prop;
    
    static{
        InputStream is = null;
        try {
            prop = new Properties();
            is = AlfrescoPropsUtils.class.getClassLoader().getResourceAsStream("alfresco.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }

}
