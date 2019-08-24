package com.wfdb.livetranslation.main;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.wfdb.livetranslation.adapter.LocaleItemData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by warren on 2019-08-24.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void renderLocaleList() {
        MainActivity activity = activityRule.getActivity();
        MainContract.Presenter presenter = new MockPresenter(activity);
        activity.setPresenter(presenter);
    }

    class MockPresenter implements MainContract.Presenter {

        MainContract.View view;
        ArrayList<LocaleItemData> list;

        public MockPresenter(MainContract.View view) {
            setView(view);
            list = new ArrayList<>();
            list.add(new LocaleItemData("Chinese - Hong Kong SAR", "zh", "hk"));
            list.add(new LocaleItemData("Chinese - China", "zh", "cn"));
        }

        @Override
        public void setView(MainContract.View view) {
            this.view = view;
        }

        @Override
        public void setDataProvider(MainContract.DataProvider provider) {
        }

        @Override
        public void onResume() {
            view.renderLocaleList(list);
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void selectLocale(LocaleItemData item) {
        }
    }
}