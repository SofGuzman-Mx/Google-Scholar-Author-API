package org.sofig.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Author author;

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}




