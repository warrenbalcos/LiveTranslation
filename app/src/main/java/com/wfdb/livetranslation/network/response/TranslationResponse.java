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
