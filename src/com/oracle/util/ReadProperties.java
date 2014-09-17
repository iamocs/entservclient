package com.oracle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    private Properties prop;

    public static void main(String[] args) {

    }

    public ReadProperties(String propertiesFileName) throws IOException {
        prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        prop.load(inputStream);

        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath");
        }
    }
    
    public ReadProperties() throws IOException {
        prop = new Properties();
        
        String  propertiesFileName = "config.properties";
        
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        prop.load(inputStream);

        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath");
        }
    }

    public String getPropertyValue(String propertyName){
        if (prop.isEmpty()){
            return null;
        } else {
            return prop.getProperty(propertyName);
        }
    }
}
