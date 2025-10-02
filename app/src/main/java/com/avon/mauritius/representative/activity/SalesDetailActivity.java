package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.QuarterlyMilestoneAdapter;
import com.avon.mauritius.representative.adapter.SalesDetailAdapter;
import com.avon.mauritius.representative.adapter.SalesDetailYearlyAdapter;
import com.avon.mauritius.representative.adapter.YearlyMilestoneAdapter;
import com.avon.mauritius.representative.model.SalesDetail;
import com.avon.mauritius.representative.model.SalesDetailYearly;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class SalesDetailActivity extends BaseActivity {
    private static final String TAG = SalesDetailActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type = "";
    private String message_id = "";
    private String notification_id = "";

    private LinearLayout yearly_tab;
    private LinearLayout quarterly_tab;

    private LinearLayout content_tab_yearly;
    private LinearLayout content_tab_quarterly;

    private ListView yearly_incentive_list;
    private ListView quarterly_incentive_list;

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
        getLayoutInflater().inflate(R.layout.activity_sales_detail, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 0;
        selectedDeselectedLayut();

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
        content_tab_yearly = this.findViewById(R.id.content_tab_yearly);
        content_tab_quarterly = this.findViewById(R.id.content_tab_quarterly);

        yearly_tab.setOnClickListener(v -> {
            content_tab_yearly.setVisibility(View.VISIBLE);
            content_tab_quarterly.setVisibility(View.GONE);
            yearly_tab.setBackgroundResource(R.drawable.broshureab_selected);
            quarterly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
        });

        quarterly_tab.setOnClickListener(v -> {
            content_tab_yearly.setVisibility(View.GONE);
            content_tab_quarterly.setVisibility(View.VISIBLE);
            yearly_tab.setBackgroundResource(R.drawable.broshureab_deselected);
            quarterly_tab.setBackgroundResource(R.drawable.broshureab_selected);
        });

        LineChart lineChart = findViewById(R.id.lineChart);

        // --- ListView setup ---
        yearly_incentive_list = findViewById(R.id.yearly_incentive_list);
        quarterly_incentive_list = findViewById(R.id.quarterly_incentive_list);

        ArrayList<SalesDetail> list = new ArrayList<>();
        list.add(new SalesDetail("1", 1000, 3000));
        list.add(new SalesDetail("2", 1500, 2500));
        list.add(new SalesDetail("3", 2000, 4000));
        list.add(new SalesDetail("4", 2500, 1500));

        SalesDetailAdapter adapter = new SalesDetailAdapter(this, list);
        quarterly_incentive_list.setAdapter(adapter);

        // --- Chart Data ---
        ArrayList<Entry> prevEntries = new ArrayList<>();
        ArrayList<Entry> currEntries = new ArrayList<>();

        prevEntries.add(new Entry(1, 1000));
        prevEntries.add(new Entry(2, 1500));
        prevEntries.add(new Entry(3, 2000));
        prevEntries.add(new Entry(4, 2500));

        currEntries.add(new Entry(1, 3000));
        currEntries.add(new Entry(2, 2500));
        currEntries.add(new Entry(3, 4000));
        currEntries.add(new Entry(4, 1500));

        LineDataSet prevSet = new LineDataSet(prevEntries, "Previous");
        prevSet.setColor(0xFFE91E63); // pink
        prevSet.setCircleColor(0xFFE91E63);
        prevSet.setLineWidth(4f);
        prevSet.setDrawValues(false); //  remove point labels
        prevSet.setCircleRadius(6f);  // circle size
        prevSet.setDrawCircleHole(false); //  solid circle

        LineDataSet currSet = new LineDataSet(currEntries, "Current");
        currSet.setColor(0xFF6200EA); // purple
        currSet.setCircleColor(0xFF6200EA);
        currSet.setLineWidth(4f);
        currSet.setDrawValues(false); //  remove point labels
        currSet.setCircleRadius(6f);  // circle size
        currSet.setDrawCircleHole(false); // solid circle

        LineData lineData = new LineData(prevSet, currSet);
        lineChart.setData(lineData);

        // --- Chart Styling ---
        lineChart.getDescription().setEnabled(false);

// X-axis setup
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(4f);

// Remove vertical axis lines (left & right border lines)
        xAxis.setDrawAxisLine(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawAxisLine(false);

//  Only horizontal grid lines (Y-axis at 0, 1000, 2000 …)
        lineChart.getAxisLeft().setDrawGridLines(true);   // keep horizontal lines
        lineChart.getAxisRight().setEnabled(false);       // hide right axis completely
        lineChart.getXAxis().setDrawGridLines(false);     // remove vertical grid lines

// Format X labels as quarters
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] quarters = new String[]{
                    "Quarterly 1", "Quarterly 2", "Quarterly 3", "Quarterly 4"
            };

            @Override
            public String getFormattedValue(float value) {
                if (value >= 1 && value <= quarters.length) {
                    return quarters[(int) value - 1];
                } else {
                    return "";
                }
            }
        });

// Y axis start from zero with threshold steps
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getAxisLeft().setGranularity(1000f); // step size
        lineChart.getAxisLeft().setLabelCount(7, true); // show 0,1000,2000...

// Legend Top-Center
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(12f); // circle size (dp)
        legend.setYOffset(15f); // 15dp margin at bottom

        lineChart.invalidate(); // refresh
        lineChartYearly();
    }
    private void lineChartYearly() {
        LineChart lineChartYearly = findViewById(R.id.lineChart_yearly);

        ArrayList<SalesDetailYearly> list = new ArrayList<>();
        list.add(new SalesDetailYearly("1", 1000, 3000));
        list.add(new SalesDetailYearly("2", 1500, 2500));
        list.add(new SalesDetailYearly("3", 2000, 4000));
        list.add(new SalesDetailYearly("4", 2500, 1500));
        list.add(new SalesDetailYearly("5", 3000, 7500));
        list.add(new SalesDetailYearly("6", 2800, 5000));
        list.add(new SalesDetailYearly("7", 5000, 4000));
        list.add(new SalesDetailYearly("8", 4500, 2000));
        list.add(new SalesDetailYearly("9", 3200, 6000));
        list.add(new SalesDetailYearly("10", 2500, 1200));
        list.add(new SalesDetailYearly("11", 6000, 7000));
        list.add(new SalesDetailYearly("12", 10000, 8000));

        SalesDetailYearlyAdapter salesDetailYearlyAdapter = new SalesDetailYearlyAdapter(this, list);
        yearly_incentive_list.setAdapter(salesDetailYearlyAdapter);

        // --- Chart Data ---
        ArrayList<Entry> prevEntries = new ArrayList<>();
        ArrayList<Entry> currEntries = new ArrayList<>();

        prevEntries.add(new Entry(1, 1000));
        prevEntries.add(new Entry(2, 1500));
        prevEntries.add(new Entry(3, 2000));
        prevEntries.add(new Entry(4, 2500));
        prevEntries.add(new Entry(5, 3000));
        prevEntries.add(new Entry(6, 2800));
        prevEntries.add(new Entry(7, 5000));
        prevEntries.add(new Entry(8, 4500));
        prevEntries.add(new Entry(9, 3200));
        prevEntries.add(new Entry(10, 2500));
        prevEntries.add(new Entry(11, 6000));
        prevEntries.add(new Entry(12, 10000));

        currEntries.add(new Entry(1, 3000));
        currEntries.add(new Entry(2, 2500));
        currEntries.add(new Entry(3, 4000));
        currEntries.add(new Entry(4, 1500));
        currEntries.add(new Entry(5, 7500));
        currEntries.add(new Entry(6, 5000));
        currEntries.add(new Entry(7, 4000));
        currEntries.add(new Entry(8, 2000));
        currEntries.add(new Entry(9, 6000));
        currEntries.add(new Entry(10, 1200));
        currEntries.add(new Entry(11, 7000));
        currEntries.add(new Entry(12, 8000));

        LineDataSet prevSet = new LineDataSet(prevEntries, "Previous");
        prevSet.setColor(0xFFE91E63); // pink
        prevSet.setCircleColor(0xFFE91E63);
        prevSet.setLineWidth(4f);
        prevSet.setDrawValues(false);
        prevSet.setCircleRadius(6f);
        prevSet.setDrawCircleHole(false);

        LineDataSet currSet = new LineDataSet(currEntries, "Current");
        currSet.setColor(0xFF6200EA); // purple
        currSet.setCircleColor(0xFF6200EA);
        currSet.setLineWidth(4f);
        currSet.setDrawValues(false);
        currSet.setCircleRadius(6f);
        currSet.setDrawCircleHole(false);

        LineData lineData = new LineData(prevSet, currSet);
        lineChartYearly.setData(lineData);

        // --- Chart Styling ---
        lineChartYearly.getDescription().setEnabled(false);

        // ✅ X-axis setup
        XAxis xAxis = lineChartYearly.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12, true); // force all labels to show
        xAxis.setLabelRotationAngle(-45f); // tilt for fitting
        xAxis.setDrawAxisLine(false);
        lineChartYearly.getAxisLeft().setDrawAxisLine(false);
        lineChartYearly.getAxisRight().setDrawAxisLine(false);
        lineChartYearly.getXAxis().setDrawGridLines(false);

        // ✅ Dynamic labels (Months) - you can replace with Quarterly if you want
        final String[] months = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value - 1;
                if (index >= 0 && index < months.length) {
                    return months[index];
                } else {
                    return "";
                }
            }
        });

        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(12f);

        // ✅ Y-axis start from 0 with 1000 step
        lineChartYearly.getAxisLeft().setAxisMinimum(0f);
        lineChartYearly.getAxisLeft().setGranularity(1000f);
        lineChartYearly.getAxisLeft().setLabelCount(11, true); // show 0,1000,2000...

        lineChartYearly.getAxisLeft().setDrawGridLines(true);
        lineChartYearly.getAxisRight().setEnabled(false);

        // ✅ Legend Top-Center
        Legend legend = lineChartYearly.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(12f);
        legend.setYOffset(15f);

        lineChartYearly.invalidate();
    }

}
