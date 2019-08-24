package com.wfdb.livetranslation.network.response;

/**
 * Created by warren on 2019-08-24.
 */
public class LocaleInfoResponse {

    public String name;
    public String language;
    public String region;

    @Override
    public String toString() {
        return "LocaleInfoResponse{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
