package com.wfdb.livetranslation.adapter;

import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.utils.LanguageUtils;

/**
 * Created by warren on 2019-08-24.
 */
public class LocaleItemData {

    private String name;
    private String language;
    private String region;

    public LocaleItemData(LocaleInfoResponse data) {
        this.name = data.name;
        this.language = data.language;
        this.region = data.region;
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

    public String getLocaleCode() {
        return LanguageUtils.getLocaleCode(language, region);
    }

    @Override
    public String toString() {
        return "LocaleItemData{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
