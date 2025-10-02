package org.sofig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.sofig.model.ApiResponse;
import org.sofig.model.Author;
import org.sofig.view.AuthorView;
import org.sofig.controller.AuthorController;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //API Key
        final String apiKey = "335d0d09b270256a3693fa335ddd88b5996fafeaf851b01d50a615940e6ecb43";
        //Author Id
        final String authorId = "4bahYMkAAAAJ";
        //URL's API
        String url = "https://serpapi.com/search.json?engine=google_scholar_author&author_id=" + authorId + "&api_key=" + apiKey;

        // "Base de datos" en memoria
        List<Author> database = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String result = EntityUtils.toString(response.getEntity());

                // Mapear JSON -> ApiResponse -> Author
                ObjectMapper mapper = new ObjectMapper();
                ApiResponse apiResponse = mapper.readValue(result, ApiResponse.class);

                Author author = apiResponse.getAuthor();

                // Guardar en la "base de datos"
                database.add(author);

                // Crear vista y controlador
                AuthorView view = new AuthorView();
                AuthorController controller = new AuthorController(author, view);

                // Mostrar datos iniciales
                System.out.println("---- Initial data (from API) ----");
                controller.updateView();

                // Mostrar toda la "base de datos"
                System.out.println("\n---- Database records ----");
                for (Author a : database) {
                    System.out.println("Name: " + a.getName());
                    System.out.println("Affiliations: " + a.getAffiliations());
                    System.out.println("Email: " + a.getEmail());
                    System.out.println("Website: " + a.getWebsite());
                    System.out.println("Thumbnail: " + a.getThumbnail());
                    if (a.getInterests() != null) {
                        System.out.println("Interests:");
                        a.getInterests().forEach(i ->
                                System.out.println("  - " + i.getTitle() + " (" + i.getLink() + ")"));
                    }
                    System.out.println("-------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

