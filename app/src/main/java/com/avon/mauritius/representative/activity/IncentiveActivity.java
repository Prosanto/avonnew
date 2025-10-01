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
import com.avon.mauritius.representative.adapter.QuarterlyMilestoneAdapter;
import com.avon.mauritius.representative.adapter.YearlyMilestoneAdapter;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class IncentiveActivity extends BaseActivity {
    private static final String TAG = IncentiveActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type="";
    private String message_id="";
    private String notification_id="";


    private LinearLayout yearly_tab ;
    private LinearLayout quarterly_tab ;
    private LinearLayout other_tab ;

    private LinearLayout content_tab_yearly;
    private LinearLayout content_tab_quarterly ;
    private LinearLayout content_tab_others ;

    private ListView yearly_incentive_list;
    private ListView quarterly_incentive_list ;

    private QuarterlyMilestoneAdapter quarterlyMilestoneAdapter;
    private YearlyMilestoneAdapter yearlyMilestoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_incentive, frameLayout);
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
        yearly_tab = this.findViewById(R.id.yearly_tab);
        quarterly_tab = this.findViewById(R.id.quarterly_tab);
        other_tab = this.findViewById(R.id.other_tab);
        content_tab_yearly = this.findViewById(R.id.content_tab_yearly);
        content_tab_quarterly = this.findViewById(R.id.content_tab_quarterly);
        content_tab_others = this.findViewById(R.id.content_tab_others);
        yearly_tab.setOnClickListener(v -> {
            content_tab_yearly.setVisibility(View.VISIBLE);
            content_tab_quarterly.setVisibility(View.GONE);
            content_tab_others.setVisibility(View.GONE);
            yearly_tab.setBackgroundResource(R.drawable.broshureab_selected);
            quarterly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            other_tab.setBackgroundResource(R.drawable.broshureab_deselected);

        });

        quarterly_tab.setOnClickListener(v -> {
            content_tab_yearly.setVisibility(View.GONE);
            content_tab_quarterly.setVisibility(View.VISIBLE);
            content_tab_others.setVisibility(View.GONE);
            yearly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            quarterly_tab.setBackgroundResource(R.drawable.broshureab_selected);
            other_tab.setBackgroundResource(R.drawable.broshureab_deselected);
        });
        other_tab.setOnClickListener(v -> {
            content_tab_yearly.setVisibility(View.GONE);
            content_tab_quarterly.setVisibility(View.GONE);
            content_tab_others.setVisibility(View.VISIBLE);
            yearly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            quarterly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            other_tab.setBackgroundResource(R.drawable.broshureab_selected);
        });

        // Dummy data
        List<String> brochureData = Arrays.asList("Brochure c4/25", "Flyer C4/25", "Campaign Letter C4/25");
        List<String> incentiveData = Arrays.asList("Gift 1st Quarter 2025(C1-C3)", "Gift 1st Quarter 2025(C4-C6)");

// Set adapters
        yearly_incentive_list = findViewById(R.id.yearly_incentive_list);
        quarterly_incentive_list = findViewById(R.id.quarterly_incentive_list);

        List<QuarterlyMilestoneAdapter.QuarterlyData> data =
                QuarterlyMilestoneAdapter.getDummyData();

        quarterlyMilestoneAdapter = new QuarterlyMilestoneAdapter(this, data);
        quarterly_incentive_list.setAdapter(quarterlyMilestoneAdapter);

        List<YearlyMilestoneAdapter.QuarterlyData> dummyData =
                YearlyMilestoneAdapter.getDummyData();

        yearlyMilestoneAdapter = new YearlyMilestoneAdapter(this, dummyData);
        yearly_incentive_list.setAdapter(yearlyMilestoneAdapter);


    }

}