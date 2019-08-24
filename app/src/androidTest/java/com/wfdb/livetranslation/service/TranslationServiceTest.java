package com.wfdb.livetranslation.service;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.wfdb.livetranslation.R;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;
import com.wfdb.livetranslation.utils.Callback;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by warren on 2019-08-24.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class TranslationServiceTest {

    @Test
    public void getStringResourcesLoaded() {
        Context context = ApplicationProvider.getApplicationContext();
        TranslationService.getInstance().init(context);

        String string = context.getString(R.string.hello_world_label);
        String name = TranslationService.getInstance().getNameForStringResource(string);
        Assert.assertEquals("hello_world_label", name);

        string = context.getString(R.string.app_name);
        name = TranslationService.getInstance().getNameForStringResource(string);
        Assert.assertEquals("app_name", name);
    }

    @Test
    public void getLocaleList() {
        TranslationService.getInstance().setLocaleListProvider(callback -> {
            ArrayList<LocaleInfoResponse> result = new ArrayList<>();
            result.add(new LocaleInfoResponse("Chinese - Hong Kong SAR", "zh", "hk"));
            callback.onSuccess(result);
        });
        TranslationService.getInstance().getLocaleList(new Callback<List<LocaleInfoResponse>>() {
            @Override
            public void onSuccess(List<LocaleInfoResponse> response) {
                Assert.assertEquals("Chinese - Hong Kong SAR", response.get(0).name);
                Assert.assertEquals("zh", response.get(0).language);
                Assert.assertEquals("hk", response.get(0).region);
            }

            @Override
            public void onFail(Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void setGetTranslation() {
        TranslationService.getInstance().setTranslationDataProvider((language, region, callback) -> {
            HashMap<String, String> entries = new HashMap<>();
            entries.put("test_label", "translated text value");
            callback.onSuccess(new TranslationResponse("Test Name", language, region, entries));
        });
        TranslationService.getInstance().changeLocale("en", "ph", new Callback<Void>() {
            @Override
            public void onSuccess(Void response) {
                TranslationMap map = TranslationService.getInstance().getTranslationMap("en", "ph");
                Assert.assertEquals("Test Name", map.getName());
                Assert.assertEquals("en", map.getLanguage());
                Assert.assertEquals("ph", map.getRegion());
                Assert.assertEquals("translated text value", map.getValue("test_label"));
            }

            @Override
            public void onFail(Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}