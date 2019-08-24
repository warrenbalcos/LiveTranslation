package com.wfdb.livetranslation.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wfdb.livetranslation.R;
import com.wfdb.livetranslation.network.provider.TranslationNetworkProvider;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;
import com.wfdb.livetranslation.utils.Callback;
import com.wfdb.livetranslation.utils.LanguageUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import io.github.inflationx.viewpump.InflateResult;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by warren on 2019-08-24.
 */
public class TranslationService {

    private static final String TAG = TranslationService.class.getSimpleName();
    private static final byte[] lock = new byte[0];
    private static final TranslationService INSTANCE = new TranslationService();

    private TranslationNetworkProvider.LocaleList localeListProvider;
    private TranslationNetworkProvider.TranslationData translationDataProvider;
    private HashMap<String, TranslationMap> translationMaps;
    private HashMap<String, String> stringResourceReverseLookup;
    private String currentLanguage;
    private String currentRegion;

    private TranslationService() {
        stringResourceReverseLookup = new HashMap<>();
        translationMaps = new HashMap<>();
    }

    public static synchronized TranslationService getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(chain -> {
                    InflateResult result = chain.proceed(chain.request());
                    View view = result.view();
                    if (view instanceof TextView) {
                        handleTextIntercept((TextView) view);
                    }
                    return result;
                })
                .build());
        loadStringResources(context);
    }

    private void loadStringResources(Context context) {
        synchronized (lock) {
            Field[] fields = R.string.class.getFields();
            String id, value;
            int resId;
            for (Field field : fields) {
                id = field.getName();
                resId = context.getResources().getIdentifier(id, "string", context.getPackageName());
                value = context.getString(resId);
                stringResourceReverseLookup.put(value, id);
            }
            Log.d(TAG, "loaded strings: " + stringResourceReverseLookup);
        }
    }

    private void handleTextIntercept(TextView textView) {
        synchronized (lock) {
            TranslationMap map = null;
            if (currentLanguage != null && currentRegion != null) {
                map = translationMaps.get(LanguageUtils.getLocaleCode(currentLanguage, currentRegion));
            }
            if (map != null) {
                String currentText = textView.getText().toString();
                String resourceName = stringResourceReverseLookup.get(currentText);
                String translationMatch = map.getValue(resourceName);
                if (!TextUtils.isEmpty(translationMatch)) {
                    textView.setText(translationMatch);
                }
            }
        }
    }

    public String getNameForStringResource(String value) {
        synchronized (lock) {
            return stringResourceReverseLookup.get(value);
        }
    }

    public void setLocaleListProvider(TranslationNetworkProvider.LocaleList localeListProvider) {
        synchronized (lock) {
            this.localeListProvider = localeListProvider;
        }
    }

    public void setTranslationDataProvider(TranslationNetworkProvider.TranslationData translationDataProvider) {
        synchronized (lock) {
            this.translationDataProvider = translationDataProvider;
        }
    }

    public void changeLocale(String language, String region, Callback<Void> callback) {
        synchronized (lock) {
            if (translationDataProvider == null) {
                callback.onFail(new Throwable("Failed to change locale"));
                return;
            }
            translationDataProvider.fetchTranslation(language, region, new Callback<TranslationResponse>() {
                @Override
                public void onSuccess(TranslationResponse response) {
                    synchronized (lock) {
                        currentLanguage = language;
                        currentRegion = region;
                        translationMaps.put(
                                LanguageUtils.getLocaleCode(language, region),
                                new TranslationMap(response));
                        callback.onSuccess(null);
                    }
                }

                @Override
                public void onFail(Throwable e) {
                    callback.onFail(e);
                }
            });
        }
    }

    public void getLocaleList(Callback<List<LocaleInfoResponse>> callback) {
        synchronized (lock) {
            if (localeListProvider == null) {
                callback.onFail(new Throwable("Failed to get locale list"));
                return;
            }
            localeListProvider.fetchTranslations(callback);
        }
    }

    public TranslationMap getTranslationMap(String language, String region) {
        synchronized (lock) {
            return translationMaps.get(LanguageUtils.getLocaleCode(language, region));
        }
    }

    public Context wrapContext(Context newBase) {
        return ViewPumpContextWrapper.wrap(newBase);
    }

}
