package com.wfdb.livetranslation.samplescreen;

import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.utils.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by warren on 2019-08-24.
 */
public class SamplePresenter implements SampleContract.Presenter {

    private SampleContract.DataProvider dataProvider;
    private SampleContract.View view;
    private ArrayList<LocaleItemData> localeItems;

    public SamplePresenter() {
        this.localeItems = new ArrayList<>();
    }

    @Override
    public void setView(SampleContract.View view) {
        this.view = view;
    }

    @Override
    public void setDataProvider(SampleContract.DataProvider provider) {
        this.dataProvider = provider;
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
}
