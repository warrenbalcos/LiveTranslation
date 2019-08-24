package com.wfdb.livetranslation.samplescreen;

import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.main.MainContract;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.utils.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by warren on 2019-08-24.
 */
public class SampleContract {

    interface View {

        void setPresenter(Presenter presenter);

        void renderLocaleList(ArrayList<LocaleItemData> localeItems);

        void forceRefreshUI();

        void showAlert(String message);

        void showError(String message);

    }

    interface Presenter {

        void setView(View view);

        void setDataProvider(DataProvider provider);

        void onResume();

        void onDestroy();

        void selectLocale(LocaleItemData item);

    }

    interface DataProvider {

        void changeLocale(String language, String region, Callback<Void> callback);

        void getLocaleList(Callback<List<LocaleInfoResponse>> callback);

    }


}
