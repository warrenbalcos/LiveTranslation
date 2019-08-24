package com.wfdb.livetranslation.network.response;

import java.util.HashMap;

/**
 * Created by warren on 2019-08-24.
 */
public class TranslationResponse {

    public String name;
    public String language;
    public String region;
    public HashMap<String, String> entries;

    public TranslationResponse(String name, String language, String region, HashMap<String, String> entries) {
        this.name = name;
        this.language = language;
        this.region = region;
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "TranslationResponse{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", region='" + region + '\'' +
                ", entries=" + entries +
                '}';
    }
}
