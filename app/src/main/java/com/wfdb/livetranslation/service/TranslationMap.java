package com.wfdb.livetranslation.service;

import com.wfdb.livetranslation.network.response.TranslationResponse;

import java.util.HashMap;

/**
 * Created by warren on 2019-08-24.
 */
public class TranslationMap {
    private String name;
    private String language;
    private String region;
    private HashMap<String, String> entries;

    public TranslationMap(TranslationResponse data) {
        this.name = data.name;
        this.language = data.language;
        this.region = data.region;
        this.entries = new HashMap<>();
        this.entries.putAll(data.entries);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getValue(String key) {
        return entries.get(key);
    }
}
