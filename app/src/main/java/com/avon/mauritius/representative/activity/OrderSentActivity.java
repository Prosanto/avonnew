package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.utils.PersistentUser;
import java.util.Locale;
import butterknife.ButterKnife;
public class OrderSentActivity extends BaseOtherActivity {
    private static final String TAG = OrderSentActivity.class.getSimpleName();
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
        getLayoutInflater().inflate(R.layout.activity_ordersent, frameLayout);
        ButterKnife.bind(this);
        DatabaseQueryHelper.DeleteAllData();
        initUI();
    }

    private void initUI() {
        findViewById(R.id.text_for_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(mContext, OrderHistoryActivity.class);
                //startActivity(intent);
                //finish();
            }
        });
        findViewById(R.id.text_for_history2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(mContext, BillingHistoryActivity.class);
                //startActivity(intent);
                //finish();
            }
        });
    }

}