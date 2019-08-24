package com.wfdb.livetranslation;

import android.app.Application;

import com.wfdb.livetranslation.config.Config;
import com.wfdb.livetranslation.network.provider.RetrofitNetworkProvider;
import com.wfdb.livetranslation.service.TranslationService;

/**
 * Created by warren on 2019-08-24.
 */
public class ApplicationEx extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TranslationService.getInstance().init(this);
        RetrofitNetworkProvider provider = new RetrofitNetworkProvider(Config.getInstance().getBaseUrl());
        TranslationService.getInstance().setLocaleListProvider(provider);
        TranslationService.getInstance().setTranslationDataProvider(provider);
    }
}
