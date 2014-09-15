/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Test

package com.oracle.oes.datapool.client;

/**
 *
 * @author OCS
 */
public class OesDBClient {
        public static String evaluatePolicy(String userId, String database, String databaseSchema, String databaseTable) {
        int port = 19999;
        String host = "localhost";
        OesSMClient cliente = null;
        
        try {
            cliente = new OesSMClient(host, port);
            
            return cliente.evaluatePolicy(userId, database, databaseSchema, databaseTable);
        } catch (Exception e) {
            return "Error from OesWrapper: " + e.getMessage();
        }
    }
        
public static void main(String[] args) {
    System.out.println("OesDBCliebt main: Response from OesListener: " 
            + OesDBClient.evaluatePolicy("trueuser", "REPOS_Pre", "Esquema2", "table1"));
    }
}
