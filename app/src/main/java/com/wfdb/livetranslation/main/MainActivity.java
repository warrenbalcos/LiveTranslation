package com.wfdb.livetranslation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wfdb.livetranslation.BaseActivity;
import com.wfdb.livetranslation.R;
import com.wfdb.livetranslation.adapter.LocaleItemData;
import com.wfdb.livetranslation.adapter.LocaleViewAdapter;
import com.wfdb.livetranslation.network.response.LocaleInfoResponse;
import com.wfdb.livetranslation.samplescreen.SampleActivity;
import com.wfdb.livetranslation.service.TranslationService;
import com.wfdb.livetranslation.utils.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.hello_message_text)
    TextView helloTextView;

    @BindView(R.id.locale_list)
    RecyclerView localeList;

    @BindView(R.id.sample_screen_button)
    Button sampleScreenButton;

    LocaleViewAdapter localeViewAdapter;
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        localeViewAdapter = new LocaleViewAdapter();
        localeViewAdapter.setLocaleClickListener(data -> presenter.selectLocale(data));

        localeList.setAdapter(localeViewAdapter);
        localeList.setLayoutManager(new LinearLayoutManager(this));

        MainPresenter presenter = new MainPresenter();
        presenter.setView(this);
        presenter.setDataProvider(new MainContract.DataProvider() {
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
        sampleScreenButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SampleActivity.class)));
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
    public void setPresenter(MainContract.Presenter presenter) {
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
