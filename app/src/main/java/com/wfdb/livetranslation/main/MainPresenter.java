package com.wfdb.livetranslation.main;

import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.utils.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by warren on 2019-08-24.
 */
public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainContract.DataProvider dataProvider;

    private ArrayList<LocaleItemData> localeItems;
    private MainContract.View view;

    public MainPresenter() {
        this.localeItems = new ArrayList<>();
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        reload();
    }

    @Override
    public void onDestroy() {
        localeItems.clear();
        localeItems = null;
    }

    @Override
    public void selectLocale(LocaleItemData item) {
        dataProvider.changeLocale(item.getLanguage(), item.getRegion(), new Callback<Void>() {
            @Override
            public void onSuccess(Void response) {
                view.forceRefreshUI();
            }

            @Override
            public void onFail(Throwable e) {
                view.showError(e.getMessage());
            }
        });
    }

    private void reload() {
        dataProvider.getLocaleList(new Callback<List<LocaleInfoResponse>>() {
            @Override
            public void onSuccess(List<LocaleInfoResponse> response) {
                localeItems.clear();
                for (LocaleInfoResponse value : response) {
                    localeItems.add(new LocaleItemData(value));
                }
                view.renderLocaleList(localeItems);
            }

            @Override
            public void onFail(Throwable e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void setDataProvider(MainContract.DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

}
