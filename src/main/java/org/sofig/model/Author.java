package org.sofig.model;

import java.util.List;

public class Author {
    private String name;
    private String affiliations;
    private String email;
    private String website;
    private String thumbnail;

    // Nuevo: lista de intereses
    private List<Interest> interests;

    public Author() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAffiliations() { return affiliations; }
    public void setAffiliations(String affiliations) { this.affiliations = affiliations; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public List<Interest> getInterests() { return interests; }
    public void setInterests(List<Interest> interests) { this.interests = interests; }
}

