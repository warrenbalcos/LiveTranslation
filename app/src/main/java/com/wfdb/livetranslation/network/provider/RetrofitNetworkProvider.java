package com.wfdb.livetranslation.network.provider;

import com.wfdb.livetranslation.network.api.TranslationServerAPI;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;
import com.wfdb.livetranslation.utils.Callback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by warren on 2019-08-24.
 */
public class RetrofitNetworkProvider implements
        TranslationNetworkProvider.LocaleList,
        TranslationNetworkProvider.TranslationData {

    private Retrofit retrofit;
    private String baseUrl;

    public RetrofitNetworkProvider(String baseUrl) {
        setBaseUrl(baseUrl);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void fetchTranslations(Callback<List<LocaleInfoResponse>> callback) {
        retrofit.create(TranslationServerAPI.class)
                .getListOfAvailableTranslations()
                .enqueue(new retrofit2.Callback<List<LocaleInfoResponse>>() {
                    @Override
                    public void onResponse(Call<List<LocaleInfoResponse>> call, Response<List<LocaleInfoResponse>> response) {
                        List<LocaleInfoResponse> body = response.body();
                        if (body == null) {
                            callback.onFail(new Throwable("unexpected response from the server"));
                        } else {
                            callback.onSuccess(body);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LocaleInfoResponse>> call, Throwable t) {
                        callback.onFail(t);
                    }
                });
    }

    @Override
    public void fetchTranslation(String language, String region, Callback<TranslationResponse> callback) {
        retrofit.create(TranslationServerAPI.class)
                .getSpecificTranslation(language, region)
                .enqueue(new retrofit2.Callback<TranslationResponse>() {
                    @Override
                    public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                        TranslationResponse body = response.body();
                        if (body == null) {
                            callback.onFail(new Throwable("unexpected response from the server"));
                        } else {
                            callback.onSuccess(body);
                        }
                    }

                    @Override
                    public void onFailure(Call<TranslationResponse> call, Throwable t) {
                        callback.onFail(t);
                    }
                });
    }
}
