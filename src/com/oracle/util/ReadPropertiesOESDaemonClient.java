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
    private String connHost;
    private String connPort;
    private String appResourceType;
    private String groupOfAttributesProtectionLabel;
    
    private ReadProperties prop;

    public ReadPropertiesOESDaemonClient() throws IOException {
        
        prop = new ReadProperties("oesclient.properties");
        
        connPort = prop.getPropertyValue("remote.connection.port");
        connHost = prop.getPropertyValue("remote.connection.host");
        appResourceType = prop.getPropertyValue("application.resource.type");
        groupOfAttributesProtectionLabel = prop.getPropertyValue("policy.protection.groupOfAttributes.lable");
    }

    public String getConnPort() {
        return connPort;
    }

    public String getConnHost() {
        return connHost;
    }
    
    public String getAppResourceType() {
        return appResourceType;
    }
    
    public String getGroupOfAttributesProtectionLabel() {
        return groupOfAttributesProtectionLabel;
    }

}



