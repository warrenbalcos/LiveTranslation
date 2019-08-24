package com.wfdb.livetranslation.network.api;

import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by warren on 2019-08-24.
 */
public interface TranslationServerAPI {

    @GET("translations")
    Call<List<LocaleInfoResponse>> getListOfAvailableTranslations();

    @GET("translation")
    Call<TranslationResponse> getSpecificTranslation(@Query("language") String language, @Query("region") String region);

}
