package org.sofig;

import org.sofig.controller.AuthorController;
import org.sofig.database.DataBaseManager;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // --- CONFIGURACIÓN API ---
        String apiKey = "335d0d09b270256a3693fa335ddd88b5996fafeaf851b01d50a615940e6ecb43";

        // --- CONFIGURACIÓN BASE DE DATOS MYSQL ---
        // IMPORTANTE: Reemplaza estos valores con los de tu configuración de MySQL.
        String dbHost = "127.0.0.1:3306"; // Host y puerto de tu servidor MySQL
        String dbName = "scholar_db";      // El nombre de la base de datos que creaste
        String dbUser = "root";            // Tu usuario de MySQL
        String dbPassword = "GN2446fa"; // Tu contraseña de MySQL

        // IDs
        List<String> researcherIds = Arrays.asList(
                "LSsXyncAAAAJ",
                "km6CP8cAAAAJ"
        );

        // RUN
        DataBaseManager dbManager = new DataBaseManager(dbHost, dbName, dbUser, dbPassword);
        AuthorController controller = new AuthorController(dbManager);

        controller.processAuthors(researcherIds, apiKey);
    }
}




