package com.wfdb.livetranslation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wfdb.livetranslation.service.TranslationService;

import static com.wfdb.livetranslation.utils.Constants.TRANSLATION_UPDATED_ACTION;

/**
 * Created by warren on 2019-08-24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TRANSLATION_UPDATED_ACTION.equals(intent.getAction())) {
                onTranslationUpdated();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(broadcastReceiver, new IntentFilter(TRANSLATION_UPDATED_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TranslationService.getInstance().wrapContext(newBase));
    }

    /**
     * Override this to handle translation updates
     */
    public void onTranslationUpdated() {
    }
}
