package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.BrochurAdapter;
import com.avon.mauritius.representative.adapter.IncentiveAdapter;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import butterknife.ButterKnife;

public class BroshureActivity extends BaseActivity {
    private static final String TAG = BroshureActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type="";
    private String message_id="";
    private String notification_id="";


    private LinearLayout brochure_tab ;
    private LinearLayout incentive_tab ;
    private LinearLayout contentTab1;
    private LinearLayout contentTab2 ;
    private ListView brochure_list;
    private ListView incentive_list ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_broshure, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 0;
        selectedDeselectedLayut();
        //bottom_liback.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_main_blue));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("notification_type")) {
                notification_type = extras.getString("notification_type");
            }
            if (extras.containsKey("message_id")) {
                message_id = extras.getString("message_id");
            }
            if (extras.containsKey("notification_id")) {
                notification_id = extras.getString("notification_id");
            }
        }
        if (notification_type.equalsIgnoreCase("6")) {
            Intent intent = new Intent(mContext, PopupviewActivity.class);
            intent.putExtra("user_id", PersistentUser.getUserID(mContext));
            intent.putExtra("screen_type", 5);
            startActivity(intent);
        }
        initUI();

    }
    private void initUI() {
        brochure_tab = this.findViewById(R.id.brochure_tab);
        incentive_tab = this.findViewById(R.id.incentive_tab);
          contentTab1 = this.findViewById(R.id.content_tab1);
          contentTab2 = this.findViewById(R.id.content_tab2);
        brochure_tab.setOnClickListener(v -> {
            contentTab1.setVisibility(View.VISIBLE);
            contentTab2.setVisibility(View.GONE);
            brochure_tab.setBackgroundResource(R.drawable.broshureab_selected);
            incentive_tab.setBackgroundResource(R.drawable.broshureab_deselected);
        });

        incentive_tab.setOnClickListener(v -> {
            contentTab1.setVisibility(View.GONE);
            contentTab2.setVisibility(View.VISIBLE);
            brochure_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            incentive_tab.setBackgroundResource(R.drawable.broshureab_selected);
        });

        // Dummy data
        List<String> brochureData = Arrays.asList("Brochure c4/25", "Flyer C4/25", "Campaign Letter C4/25");
        List<String> incentiveData = Arrays.asList("Gift 1st Quarter 2025(C1-C3)", "Gift 1st Quarter 2025(C4-C6)");

// Set adapters
        brochure_list = findViewById(R.id.brochure_list);
        incentive_list = findViewById(R.id.incentive_list);
        BrochurAdapter adapter1 = new BrochurAdapter(this, brochureData);
        brochure_list.setAdapter(adapter1);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(this, incentiveData);
        incentive_list.setAdapter(incentiveAdapter);
    }

}