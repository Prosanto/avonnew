package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Locale;

import butterknife.ButterKnife;

public class AuthonticationsetupActivity extends AppCompatActivity {
    private static final String TAG = AuthonticationsetupActivity.class.getSimpleName();
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
        setContentView(R.layout.activity_2fac_authotications);
        ButterKnife.bind(this);
        initUI();

    }
    private void initUI() {
    }

}