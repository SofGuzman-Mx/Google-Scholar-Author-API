package org.sofig.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("citation_id")
    private String citationId; // Lo usaremos como ID único

    @JsonProperty("authors")
    private String authors; // El JSON lo da como string

    @JsonProperty("publication")
    private String publicationInfo; // Contiene fecha y más datos

    @JsonProperty("snippet")
    private String snippet; // Usaremos esto como el 'abstract'

    @JsonProperty("cited_by")
    private CitedBy citedBy;

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getCitationId() { return citationId; }
    public void setCitationId(String citationId) { this.citationId = citationId; }
    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
    public String getPublicationInfo() { return publicationInfo; }
    public void setPublicationInfo(String publicationInfo) { this.publicationInfo = publicationInfo; }
    public String getSnippet() { return snippet; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
    public CitedBy getCitedBy() { return citedBy; }
    public void setCitedBy(CitedBy citedBy) { this.citedBy = citedBy; }

    // Clase anidada para "cited_by"
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CitedBy {
        @JsonProperty("value")
        private int value;

        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
    }
}

