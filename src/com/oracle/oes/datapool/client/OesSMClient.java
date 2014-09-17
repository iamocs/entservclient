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
import java.net.*;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */

import com.oracle.util.ReadPropertiesOESDaemonClient;

public class OesSMClient {
    
    private String host = "localhost";
    private int port = 19999;
    private String appResourceType = null;
    private String TimeStamp;
    private InetAddress address = null;
    private Socket connection = null;
    private ReadPropertiesOESDaemonClient properties;
        
    
    public OesSMClient (){
        try{
            properties = new ReadPropertiesOESDaemonClient();
        } catch (Exception e){
            System.out.println("# Se ha producido un error procesando el fichero de configuración...");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        
        host = properties.getConnHost();
        port = Integer.parseInt(properties.getConnPort());
        appResourceType = properties.getAppResourceType();
    }
    
    
    public String evaluatePolicy (String userId, String database, String resourceType, String databaseSchema, String databaseTable, String databaseAttribute, String action){
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");

        try {
            /** Obtain an address object of the server */
            address = InetAddress.getByName(host);
            /** Establish a socket connetion */
            connection = new Socket(address, port);
            /** Instantiate a BufferedOutputStream object */

            BufferedOutputStream bos =
                new BufferedOutputStream(connection.getOutputStream());

            /** Instantiate an OutputStreamWriter object with the optional character
                 * encoding.
                 */
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");

            TimeStamp = new java.util.Date().toString();
            // String process =
            //    "* CLIENT TRACE: Calling the Socket Server on " + host + " port " + port +
            //    " at " + TimeStamp + ". Data: userID("+ userId + "); schema(" + databaseSchema + "); table(" + databaseTable + ")." + (char)13;

            String process = userId + "#" + 
                    ((database != null)? database:"empty") + "#" +
                    ((resourceType != null)? resourceType:"empty") + "#" +
                    ((databaseSchema != null)? databaseSchema:"empty") + "#" + 
                    ((databaseTable != null)? databaseTable:"empty") + "#" +
                    ((databaseAttribute != null)? databaseAttribute:"empty") + "#" +
                    ((action != null)? action:"empty") + (char)13;
            
            System.out.println("DAEMON TRACE: process = " + process);
            
            /** Write across the socket connection and flush the buffer */
            osw.write(process);
            osw.flush();
            /** Instantiate a BufferedInputStream object for reading
           * incoming socket streams.
           */

            BufferedInputStream bis =
                new BufferedInputStream(connection.getInputStream());
            /**Instantiate an InputStreamReader with the optional
           * character encoding.
           */

            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

            /**Read the socket's InputStream and append to a StringBuffer */
            int c;
            while ((c = isr.read()) != 13)
                instr.append((char)c);

            /** Close the socket connection. */
            connection.close();
            System.out.println("* CLIENT TRACE: received value: "+ instr);
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            return "Error from OesSMClient (1): " + f.getMessage();
        } catch (Exception g) {
            System.out.println("Exception: " + g);
            return "Error from OesSMClient (2): " + g.getMessage();
        }
        return instr.toString();
    }
    
    public String evaluatePolicy (String userId, String database, String databaseSchema, String databaseTable, String databaseAttribute, String action){
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");

        try {
            /** Obtain an address object of the server */
            address = InetAddress.getByName(host);
            /** Establish a socket connetion */
            connection = new Socket(address, port);
            /** Instantiate a BufferedOutputStream object */

            BufferedOutputStream bos =
                new BufferedOutputStream(connection.getOutputStream());

            /** Instantiate an OutputStreamWriter object with the optional character
                 * encoding.
                 */
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");

            TimeStamp = new java.util.Date().toString();
            // String process =
            //    "* CLIENT TRACE: Calling the Socket Server on " + host + " port " + port +
            //    " at " + TimeStamp + ". Data: userID("+ userId + "); schema(" + databaseSchema + "); table(" + databaseTable + ")." + (char)13;

            String process = userId + "#" + 
                    ((database != null)? database:"empty") + "#" +
                    ((appResourceType != null)? appResourceType:"empty") + "#" +
                    ((databaseSchema != null)? databaseSchema:"empty") + "#" + 
                    ((databaseTable != null)? databaseTable:"empty") + "#" +
                    ((databaseAttribute != null)? databaseAttribute:"empty") + "#" +
                    ((action != null)? action:"empty") + (char)13;
            
            System.out.println("DAEMON TRACE: process = " + process);
            
            /** Write across the socket connection and flush the buffer */
            osw.write(process);
            osw.flush();
            /** Instantiate a BufferedInputStream object for reading
           * incoming socket streams.
           */

            BufferedInputStream bis =
                new BufferedInputStream(connection.getInputStream());
            /**Instantiate an InputStreamReader with the optional
           * character encoding.
           */

            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

            /**Read the socket's InputStream and append to a StringBuffer */
            int c;
            while ((c = isr.read()) != 13)
                instr.append((char)c);

            /** Close the socket connection. */
            connection.close();
            System.out.println("* CLIENT TRACE: received value: "+ instr);
        } catch (IOException f) {
            System.out.println("IOException: " + f);
            return "Error from OesSMClient (1): " + f.getMessage();
        } catch (Exception g) {
            System.out.println("Exception: " + g);
            return "Error from OesSMClient (2): " + g.getMessage();
        }
        return instr.toString();
    }
}
