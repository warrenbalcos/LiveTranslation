package com.wfdb.livetranslation.main;

import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.network.response.TranslationResponse;
import com.wfdb.livetranslation.utils.Callback;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by warren on 2019-08-24.
 */
public class MainPresenterTest {

    @Test
    public void onResume() {
        MainPresenter presenter = new MainPresenter();
        MockView view = new MockView();
        presenter.setView(view);
        presenter.setDataProvider(new MockData());
        presenter.onResume();

        Assert.assertEquals("Chinese - Hong Kong SAR", view.localeItems.get(0).getName());
        Assert.assertEquals("zh", view.localeItems.get(0).getLanguage());
        Assert.assertEquals("hk", view.localeItems.get(0).getRegion());

        Assert.assertEquals("Chinese - China", view.localeItems.get(1).getName());
        Assert.assertEquals("zh", view.localeItems.get(1).getLanguage());
        Assert.assertEquals("cn", view.localeItems.get(1).getRegion());
    }

    @Test
    public void selectLocale() {
        MainPresenter presenter = new MainPresenter();
        MockView view = new MockView();
        presenter.setView(view);
        MockData dataProvider = new MockData();
        presenter.setDataProvider(dataProvider);
        presenter.onResume();
        presenter.selectLocale(new LocaleItemData("Test Name", "tx", "xy"));

        Assert.assertEquals("Test Name", dataProvider.response.name);
        Assert.assertEquals("tx", dataProvider.response.language);
        Assert.assertEquals("xy", dataProvider.response.region);
    }

    class MockData implements MainContract.DataProvider {

        TranslationResponse response;

        @Override
        public void changeLocale(String language, String region, Callback<Void> callback) {
            HashMap<String, String> entries = new HashMap<>();
            entries.put("test_label", "translated text value");
            response = new TranslationResponse("Test Name", language, region, entries);
            callback.onSuccess(null);
        }

        @Override
        public void getLocaleList(Callback<List<LocaleInfoResponse>> callback) {
            ArrayList<LocaleInfoResponse> result = new ArrayList<>();
            result.add(new LocaleInfoResponse("Chinese - Hong Kong SAR", "zh", "hk"));
            result.add(new LocaleInfoResponse("Chinese - China", "zh", "cn"));
            callback.onSuccess(result);
        }
    }

    class MockView implements MainContract.View {

        MainContract.Presenter presenter;
        ArrayList<LocaleItemData> localeItems;
        boolean forceRefresh;
        String alert;
        String error;

        @Override
        public void setPresenter(MainContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void renderLocaleList(ArrayList<LocaleItemData> localeItems) {
            this.localeItems = localeItems;
        }

        @Override
        public void forceRefreshUI() {
            this.forceRefresh = true;
        }

        @Override
        public void showAlert(String message) {
            this.alert = message;
        }

        @Override
        public void showError(String message) {
            this.error = message;
        }
    }
}