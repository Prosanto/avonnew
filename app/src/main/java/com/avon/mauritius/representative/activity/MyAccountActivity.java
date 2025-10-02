package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick({R.id.image_for_notifcation, R.id.image_my_account, R.id.layout_for_incentive,R.id.layout_for_sells_details})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.image_for_notifcation:

                break;
            case R.id.image_my_account:
                intent = new Intent(mContext, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_for_sells_details:
                intent = new Intent(mContext, SalesDetailActivity.class);
                startActivity(intent);

                break;
            case R.id.layout_for_incentive:
                intent = new Intent(mContext, IncentiveActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

}