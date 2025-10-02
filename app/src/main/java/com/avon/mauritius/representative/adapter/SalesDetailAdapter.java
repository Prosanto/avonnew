package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.model.SalesDetail;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SalesDetailAdapter extends BaseAdapter {
    private Context context;
    private List<SalesDetail> dataList;

    public SalesDetailAdapter(Context context, List<SalesDetail> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() { return dataList.size(); }

    @Override
    public Object getItem(int position) { return dataList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_salesdetail, parent, false);
        }

        SalesDetail data = dataList.get(position);

        TextView tvQuarter = convertView.findViewById(R.id.tvQuarter);
        TextView tvPrevious = convertView.findViewById(R.id.tvPrevious);
        TextView tvCurrent = convertView.findViewById(R.id.tvCurrent);

        tvQuarter.setText(data.getQuarter());
        tvPrevious.setText(String.valueOf(data.getPreviousYear()));
        tvCurrent.setText(String.valueOf(data.getCurrentYear()));

        return convertView;
    }

}