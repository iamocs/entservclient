/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Test
package com.oracle.oes.datapool.client;

import java.util.Scanner;

/**
 *
 * @author OCS
 */
public class OesDBClient {

    public static String evaluatePolicy(String userId, String database, String resourceType, String databaseSchema, String databaseTable, String action) {
        OesSMClient cliente = null;

        try {
            cliente = new OesSMClient();

            return cliente.evaluatePolicy(userId, database, resourceType, databaseSchema, databaseTable, action);
        } catch (Exception e) {
            return "Error from OesWrapper: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        String userId = null;
        String database = null;
        String resourceType = null;
        String databaseSchema = null;
        String databaseTable = null;
        String action = null;
        Scanner in = new Scanner(System.in);
        String s = null;
        boolean fin = false;

        System.out.println("Iniciando OesDbClient Tester...");

        while (!fin) {

            System.out.println("### Captura de parámetros de entrada ###");
            System.out.println("# User ID: ");
            s = in.nextLine();
            while (s.isEmpty()) {
                System.out.println("### Error: debe indicar un valor para el User ID.");
                System.out.println("# User ID: ");
                s = in.nextLine();
            }
            userId = s;
            s = null;

            System.out.println("# Base de Datos: [dpdbvra]");
            s = in.nextLine();
            if (s.isEmpty()) {
                database = "dpdbvra";
                System.out.println(database);
            } else {
                database = s;
            }
  
            s = null;

            System.out.println("# Tipo de Recurso: [DatabaseObject]");
            s = in.nextLine();
            if (s.isEmpty()) {
                resourceType = "DatabaseObject";
                System.out.println(resourceType);
            } else {
                resourceType = s;
            }

            s = null;

            System.out.println("# Esquema de base de datos: ");
            s = in.nextLine();
            while (s.isEmpty()) {
                System.out.println("### Error: debe indicar un valor para el Esquema de base de datos.");
                System.out.println("# Esquema de base de datos: ");
                s = in.nextLine();
            }
            databaseSchema = s;
            s = null;

            System.out.println("# Tabla de base de datos: []");
            s = in.nextLine();
            if (s.isEmpty()) {
                databaseTable = null;
            } else {
                databaseTable = s;
            }

            s = null;

            System.out.println("# Acción: [select]");
            s = in.nextLine();
            if (s.isEmpty()) {
                action = "select";
                System.out.println(action);
            } else {
                action = s;
            }

            s = null;

            System.out.println("###");
            System.out.println("");
            System.out.println("Procesando parámetros...");
            System.out.println("");
            System.out.println("");
            System.out.println("Llamando al listener OES...");
            System.out.println("");
            System.out.println("Respuesta desde OesListener: "
                    + OesDBClient.evaluatePolicy(userId, database, resourceType, databaseSchema, databaseTable, action));
            System.out.println("");
            System.out.println("");
            System.out.println("# Indique si quiere realizar una nueva consulta: (S|N) [N]");
            
            s = in.nextLine();
            if (s.isEmpty() || (s.compareToIgnoreCase("N") == 0)){
                fin = true;
            }
        }
    }
}
