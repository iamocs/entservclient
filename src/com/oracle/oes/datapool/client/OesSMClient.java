/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.oes.datapool.client;

/**
 *
 * @author OCS
 */
/*  The java.net package contains the basics needed for network operations. */
import com.oracle.util.ReadPropertiesOESDaemonClient;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;

/**
 * The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

public class OesSMClient {

    private String host = "localhost";
    private int port = 19999;
    private int timeout = 1000;
    private String appResourceType = null;
    private String TimeStamp;
    private InetAddress address = null;
    private Socket connection = null;
    private ReadPropertiesOESDaemonClient properties;

    private static final String DB_NODES_SEPARATOR_VALUE = ",";
    private static final String FALSE_STRING_VALUE_NULL_PARAMETERS = "2=3";
    private static final String FALSE_STRING_VALUE_NOT_OES_LISTENER_RESPONSE = "3=4";

    public OesSMClient() {
        try {
            properties = new ReadPropertiesOESDaemonClient();
        } catch (Exception e) {
            System.out.println("# Se ha producido un error procesando el fichero de configuración...");
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        host = properties.getConnHost();
        port = Integer.parseInt(properties.getConnPort());
        timeout = Integer.parseInt(properties.getConnTimeout());
        appResourceType = properties.getAppResourceType();
    }
    
    public String evaluatePolicy(String dbNodeAlternativeHosts, String userId, String database, String resourceType, String databaseSchema, String databaseTable, String databaseAttribute, String action) {
        String [] dbHosts = null;
        boolean finIntentos = false;
        ArrayList <String> hostNames = new ArrayList <String>();
        String responseAux;
        String response = FALSE_STRING_VALUE_NOT_OES_LISTENER_RESPONSE;
        
        hostNames.add(host);
        
        if (dbNodeAlternativeHosts != null){    
            dbHosts = dbNodeAlternativeHosts.split(DB_NODES_SEPARATOR_VALUE);
            
            for (int i=0; i < dbHosts.length; i++){
                hostNames.add(dbHosts[i]);
            }
        }
        
        try {
            
            if ((database == null)
                    || (databaseSchema == null)
                    || (userId == null)) {
                return FALSE_STRING_VALUE_NULL_PARAMETERS;
            }
            
            Iterator iter = hostNames.iterator();
            
            while (!finIntentos && iter.hasNext()){
                String dbHost = (String) iter.next();
                System.out.println("Contectando con dbHost " + dbHost);
                
                responseAux = evaluatePolicyCallToHost(
                        dbHost, 
                        userId, 
                        database, 
                        resourceType,
                        databaseSchema,
                        databaseTable,
                        databaseAttribute,
                        action);
                
                if (!responseAux.toUpperCase().contains("ERROR")){
                    System.out.println("ERROR: " + responseAux);
                    finIntentos = true;
                    response = responseAux;
                }
            }
            
            
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return FALSE_STRING_VALUE_NOT_OES_LISTENER_RESPONSE;
        }
            
        return response;
    }
    
    public String evaluatePolicy(String dbNodeAlternativeHosts, String userId, String database, String databaseSchema, String databaseTable, String databaseAttribute, String action) {
        String [] dbHosts = null;
        boolean finIntentos = false;
        ArrayList <String> hostNames = new ArrayList <String>();
        String responseAux;
        String response = FALSE_STRING_VALUE_NOT_OES_LISTENER_RESPONSE;
        
        //System.out.println("dbNodeAlternativeHosts = " + dbNodeAlternativeHosts);
        
        hostNames.add(host);
        
        if (dbNodeAlternativeHosts != null){    
            dbHosts = dbNodeAlternativeHosts.split(DB_NODES_SEPARATOR_VALUE);
            
            for (int i=0; i < dbHosts.length; i++){
                //System.out.println("dbHost["+i+"] = " + dbHosts[i]);
                hostNames.add(dbHosts[i]);
            }
        }
        
        try {
            
            if ((database == null)
                    || (databaseSchema == null)
                    || (userId == null)) {
                return FALSE_STRING_VALUE_NULL_PARAMETERS;
            }
            
            Iterator iter = hostNames.iterator();
            
            while (!finIntentos && iter.hasNext()){
                String dbHost = (String) iter.next();
                System.out.println("Contectando con dbHost " + dbHost);
                
                responseAux = evaluatePolicyCallToHost(
                        dbHost, 
                        userId, 
                        database, 
                        databaseSchema,
                        databaseTable,
                        databaseAttribute,
                        action);
                
                if (!responseAux.toUpperCase().contains("ERROR")){
                    System.out.println("OK: " + responseAux);
                    finIntentos = true;
                    response = responseAux;
                }
            }
            
            
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return FALSE_STRING_VALUE_NOT_OES_LISTENER_RESPONSE;
        }
            
        return response;
    }
    
    private String evaluatePolicyCallToHost(String dbHost, String userId, String database, String resourceType, String databaseSchema, String databaseTable, String databaseAttribute, String action) {
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");
        
        try {
            
           
            /**
             * Obtain an address object of the server
             */
            address = InetAddress.getByName(dbHost);
            /**
             * Establish a socket connetion
             */
            connection = new Socket(address, port);
            connection.setSoTimeout(timeout);
            
            /**
             * Instantiate a BufferedOutputStream object
             */

            BufferedOutputStream bos
                    = new BufferedOutputStream(connection.getOutputStream());

            /**
             * Instantiate an OutputStreamWriter object with the optional
             * character encoding.
             */
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");

            TimeStamp = new java.util.Date().toString();
            // String process =
            //    "* CLIENT TRACE: Calling the Socket Server on " + host + " port " + port +
            //    " at " + TimeStamp + ". Data: userID("+ userId + "); schema(" + databaseSchema + "); table(" + databaseTable + ")." + (char)13;

            String process = userId + "#"
                    + ((database != null) ? database : "empty") + "#"
                    + ((resourceType != null) ? resourceType : "empty") + "#"
                    + ((databaseSchema != null) ? databaseSchema : "empty") + "#"
                    + ((databaseTable != null) ? databaseTable : "empty") + "#"
                    + ((databaseAttribute != null) ? databaseAttribute : "empty") + "#"
                    + ((action != null) ? action : "empty") + (char) 13;

            System.out.println("DAEMON TRACE: process = " + process);

            /**
             * Write across the socket connection and flush the buffer
             */
            osw.write(process);
            osw.flush();
            /**
             * Instantiate a BufferedInputStream object for reading incoming
             * socket streams.
             */

            BufferedInputStream bis
                    = new BufferedInputStream(connection.getInputStream());
            /**
             * Instantiate an InputStreamReader with the optional character
             * encoding.
             */

            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

            /**
             * Read the socket's InputStream and append to a StringBuffer
             */
            int c;
            while ((c = isr.read()) != 13) {
                instr.append((char) c);
            }

            /**
             * Close the socket connection.
             */
            connection.close();
            System.out.println("* CLIENT TRACE: received value: " + instr);
        } catch (SocketTimeoutException timeoutEx) {
            System.out.println("Exception: " + timeoutEx);
            return "TIME_OUT_ERROR (1): " + timeoutEx.getMessage();
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            return "CONNECTION_ERROR (2): " + f.getMessage();
        } catch (Exception g) {
            System.out.println("Exception: " + g);
            return "OTHER_ERROR (3): " + g.getMessage();
        }
        return instr.toString();
    }

    private String evaluatePolicyCallToHost(String dbHost, String userId, String database, String databaseSchema, String databaseTable, String databaseAttribute, String action) {
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");

        try {

            /**
             * Obtain an address object of the server
             */
            address = InetAddress.getByName(dbHost);
            /**
             * Establish a socket connetion
             */
            connection = new Socket(address, port);
            connection.setSoTimeout(timeout);
            /**
             * Instantiate a BufferedOutputStream object
             */

            BufferedOutputStream bos
                    = new BufferedOutputStream(connection.getOutputStream());

            /**
             * Instantiate an OutputStreamWriter object with the optional
             * character encoding.
             */
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");

            TimeStamp = new java.util.Date().toString();
            // String process =
            //    "* CLIENT TRACE: Calling the Socket Server on " + host + " port " + port +
            //    " at " + TimeStamp + ". Data: userID("+ userId + "); schema(" + databaseSchema + "); table(" + databaseTable + ")." + (char)13;

            String process = userId + "#"
                    + ((database != null) ? database : "empty") + "#"
                    + ((appResourceType != null) ? appResourceType : "empty") + "#"
                    + ((databaseSchema != null) ? databaseSchema : "empty") + "#"
                    + ((databaseTable != null) ? databaseTable : "empty") + "#"
                    + ((databaseAttribute != null) ? databaseAttribute : "empty") + "#"
                    + ((action != null) ? action : "empty") + (char) 13;

            System.out.println("DAEMON TRACE: process = " + process);

            /**
             * Write across the socket connection and flush the buffer
             */
            osw.write(process);
            osw.flush();
            /**
             * Instantiate a BufferedInputStream object for reading incoming
             * socket streams.
             */

            BufferedInputStream bis
                    = new BufferedInputStream(connection.getInputStream());
            /**
             * Instantiate an InputStreamReader with the optional character
             * encoding.
             */

            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

            /**
             * Read the socket's InputStream and append to a StringBuffer
             */
            int c;
            while ((c = isr.read()) != 13) {
                instr.append((char) c);
            }

            /**
             * Close the socket connection.
             */
            connection.close();
            System.out.println("* CLIENT TRACE: received value: " + instr);
        } catch (SocketTimeoutException timeoutEx) {
            System.out.println("Exception: " + timeoutEx);
            return "TIME_OUT_ERROR (1): " + timeoutEx.getMessage();
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            return "CONNECTION_ERROR (2): " + f.getMessage();
        } catch (Exception g) {
            System.out.println("Exception: " + g);
            return "OTHER_ERROR (3): " + g.getMessage();
        }

        return instr.toString();
    }

    
}
