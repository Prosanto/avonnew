package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Locale;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductAddedActivity extends AppCompatActivity {
    private static final String TAG = ProductAddedActivity.class.getSimpleName();
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
        setContentView(R.layout.activity_product_added);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.li_seecart, R.id.li_continueshopping})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.li_seecart:
                intent = new Intent(this, BucketListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.li_continueshopping:
                finish();
                break;
            default:
                break;

        }
    }



}