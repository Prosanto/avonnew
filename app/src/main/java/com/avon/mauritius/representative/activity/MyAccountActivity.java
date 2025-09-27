package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.BrochurAdapter;
import com.avon.mauritius.representative.adapter.MysellAdapter;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class MyAccountActivity extends BaseActivity {
    private static final String TAG = MyAccountActivity.class.getSimpleName();
    private Context mContext;
    private GridView mysell_list ;
    private BarDataSet barDataSet;
    private BarData data;
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


        BarChart barChart = findViewById(R.id.barChart);

        // 1. Prepare Data Entries (Single DataSet)
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 20f)); // This Campaign
        entries.add(new BarEntry(1f, 35f)); // Quarter
        entries.add(new BarEntry(2f, 50f)); // Current Year
        entries.add(new BarEntry(3f, 40f)); // Previous Year

        BarDataSet dataSet = new BarDataSet(entries, "Performance");
        dataSet.setColors(new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA});
//        dataSet.setValueTextSize(12f);

// 2. Create BarData
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f); // bar width nearly fills each X-axis slot
        barChart.setData(barData);

// 3. Configure X-Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f); // start a little before first bar
        xAxis.setAxisMaximum(3.5f);  // end a little after last bar
        xAxis.setValueFormatter(new IndexAxisValueFormatter(
                new String[]{"This Campaign", "Quarter", "Current Year", "Previous Year"}
        ));

// 4. Configure Legend at top
//        Legend legend = barChart.getLegend();
//        legend.setEnabled(true);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);
//        legend.setTextSize(12f);


// 5. Final tweaks
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true); // makes bars fit the X-axis nicely
        barChart.animateY(1000);
        barChart.invalidate();





    }

}