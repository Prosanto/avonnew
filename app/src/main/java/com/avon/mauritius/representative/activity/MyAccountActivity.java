package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.BrochurAdapter;
import com.avon.mauritius.representative.adapter.MysellAdapter;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class MyAccountActivity extends BaseActivity {
    private static final String TAG = MyAccountActivity.class.getSimpleName();
    private Context mContext;
    private GridView mysell_list ;
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
        List<String> mySellData = Arrays.asList("The Campaign", "Quarter", "Current Year","Previous Year");

        mysell_list = findViewById(R.id.mysell_list);
        MysellAdapter adapter1 = new MysellAdapter(this, mySellData);
        mysell_list.setAdapter(adapter1);

    }

}