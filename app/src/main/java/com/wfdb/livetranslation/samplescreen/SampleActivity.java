package com.wfdb.livetranslation.samplescreen;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wfdb.livetranslation.BaseActivity;
import com.wfdb.livetranslation.R;
import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.adapter.LocaleViewAdapter;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.service.TranslationService;
import com.wfdb.livetranslation.utils.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleActivity extends BaseActivity implements SampleContract.View {

    @BindView(R.id.locale_list)
    RecyclerView localeList;

    LocaleViewAdapter localeViewAdapter;
    SampleContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ButterKnife.bind(this);

        localeViewAdapter = new LocaleViewAdapter();
        localeViewAdapter.setLocaleClickListener(data -> presenter.selectLocale(data));

        localeList.setAdapter(localeViewAdapter);
        localeList.setLayoutManager(new LinearLayoutManager(this));

        SamplePresenter presenter = new SamplePresenter();
        presenter.setView(this);
        presenter.setDataProvider(new SampleContract.DataProvider() {
            @Override
            public void changeLocale(String language, String region, Callback<Void> callback) {
                TranslationService.getInstance().changeLocale(language, region, callback);
            }

            @Override
            public void getLocaleList(Callback<List<LocaleInfoResponse>> callback) {
                TranslationService.getInstance().getLocaleList(callback);
            }
        });
        setPresenter(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void renderLocaleList(ArrayList<LocaleItemData> localeItems) {
        localeViewAdapter.setItems(localeItems);
    }

    @Override
    public void forceRefreshUI() {
        recreate();
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create()
                .show();
    }

    @Override
    public void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error_alert_title))
                .setMessage(message)
                .create()
                .show();
    }
}
