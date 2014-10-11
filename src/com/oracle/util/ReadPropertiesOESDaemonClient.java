/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.util;

import java.io.IOException;
import com.oracle.util.ReadProperties;

/**
 *
 * @author ruben
 */
public class ReadPropertiesOESDaemonClient {
    
    private static final String SYSTEM_CONFIG_PROPERTY_FILE = "com.oracle.oes.datapool.clietn.oessmclient.conf.file";
    private static final String RESOURCE_CONFIG_PROPERTY_FILE = "oesclient.properties";
    private static final String PROPERTY_REMOTE_CONNECTION_HOST_LABEL = "remote.connection.host";
    private static final String PROPERTY_REMOTE_CONNECTION_PORT_LABEL = "remote.connection.port";
    private static final String PROPERTY_REMOTE_CONNECTION_TIMEOUT = "remote.connection.timeout";
    private static final String PROPERTY_APPLICATION_RESOURCE_TYPE_LABEL = "application.resource.type";
    
    private String connHost;
    private String connPort;
    private String connTimeout;
    private String appResourceType;
    
    private ReadProperties prop;

    public ReadPropertiesOESDaemonClient() throws IOException {
        
        if (System.getProperty(SYSTEM_CONFIG_PROPERTY_FILE) != null){
            prop = new ReadProperties(true, System.getProperty(SYSTEM_CONFIG_PROPERTY_FILE));
        } else {
            prop = new ReadProperties(false, RESOURCE_CONFIG_PROPERTY_FILE);
        }
        
        connHost = prop.getPropertyValue(PROPERTY_REMOTE_CONNECTION_HOST_LABEL);
        connPort = prop.getPropertyValue(PROPERTY_REMOTE_CONNECTION_PORT_LABEL);
        connTimeout = prop.getPropertyValue(PROPERTY_REMOTE_CONNECTION_TIMEOUT);
        appResourceType = prop.getPropertyValue(PROPERTY_APPLICATION_RESOURCE_TYPE_LABEL);
    }

    public String getConnPort() {
        return connPort;
    }

     public String getConnTimeout() {
        return connTimeout;
    }
    
    public String getConnHost() {
        return connHost;
    }
    
    public String getAppResourceType() {
        return appResourceType;
    }

}



