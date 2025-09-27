package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Locale;

import butterknife.ButterKnife;

public class MyAccountActivity extends BaseActivity {
    private static final String TAG = MyAccountActivity.class.getSimpleName();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_my_account, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 4;
        selectedDeselectedLayut();

        initUI();

    }
    private void initUI() {
    }

}