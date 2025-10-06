package org.sofig.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.sofig.database.DataBaseManager;
import org.sofig.model.ApiResponse;
import org.sofig.model.Article;

import java.io.IOException;
import java.util.List;

public class AuthorController {

    private final DataBaseManager dbManager;
    private final ObjectMapper objectMapper;

    public AuthorController(DataBaseManager dbManager) {
        this.dbManager = dbManager;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Procesa una lista de investigadores, obtiene sus artículos y los guarda en la BD.
     * @param authorIds Lista de IDs de autores de Google Scholar.
     * @param apiKey Tu clave de la API de SerpApi.
     */
    public void processAuthors(List<String> authorIds, String apiKey) {
        dbManager.createArticlesTable(); // Asegurarse de que la tabla exista

        for (String authorId : authorIds) {
            System.out.println("\n--- Procesando investigador con ID: " + authorId + " ---");
            try {
                String jsonResponse = fetchAuthorData(authorId, apiKey);
                if (jsonResponse != null) {
                    ApiResponse response = objectMapper.readValue(jsonResponse, ApiResponse.class);

                    if (response.getArticles() != null && !response.getArticles().isEmpty()) {
                        // Limitar a los 3 primeros artículos como se solicita
                        response.getArticles().stream().limit(3).forEach(article -> {
                            System.out.println("Guardando artículo: " + article.getTitle());
                            dbManager.insertArticle(article);
                        });
                    } else {
                        System.out.println("No se encontraron artículos para este investigador.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al procesar el autor " + authorId + ": " + e.getMessage());
            }
        }
        System.out.println("\n--- Proceso completado. ---");
    }

    /**
     * Realiza la petición a la API de SerpApi para obtener los datos de un autor.
     * @param authorId El ID del autor.
     * @param apiKey Tu clave de la API.
     * @return Un String con la respuesta JSON.
     * @throws IOException Si ocurre un error de red.
     */
    private String fetchAuthorData(String authorId, String apiKey) throws IOException {
        String url = String.format("https://serpapi.com/search.json?engine=google_scholar_author&author_id=%s&api_key=%s", authorId, apiKey);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    System.err.println("Error en la API: " + statusCode + " - " + EntityUtils.toString(response.getEntity()));
                    return null;
                }
            }
        }
    }
}
