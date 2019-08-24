package com.wfdb.livetranslation.network.provider;

import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;
import com.wfdb.livetranslation.utils.Callback;

import java.util.List;

/**
 * Created by warren on 2019-08-24.
 */
public interface TranslationNetworkProvider {

    interface LocaleList {
        void fetchTranslations(Callback<List<LocaleInfoResponse>> callback);
    }

    interface TranslationData {
        void fetchTranslation(String language, String region, Callback<TranslationResponse> callback);
    }

}
