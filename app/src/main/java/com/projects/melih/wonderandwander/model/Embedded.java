package com.projects.melih.wonderandwander.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Melih Gültekin on 22.06.2018
 */
@SuppressWarnings("unused")
public class Embedded {
    @SerializedName("city:search-results")
    private List<EmbeddedCity> searchResults;

    public List<EmbeddedCity> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<EmbeddedCity> searchResults) {
        this.searchResults = searchResults;
    }
}