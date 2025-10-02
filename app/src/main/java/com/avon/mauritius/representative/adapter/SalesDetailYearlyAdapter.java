package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.model.SalesDetail;
import com.avon.mauritius.representative.model.SalesDetailYearly;

import java.util.List;

public class SalesDetailYearlyAdapter extends BaseAdapter {
    private Context context;
    private List<SalesDetailYearly> dataList;

    public SalesDetailYearlyAdapter(Context context, List<SalesDetailYearly> dataList) {
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

        SalesDetailYearly data = dataList.get(position);

        TextView tvQuarter = convertView.findViewById(R.id.tvQuarter);
        TextView tvPrevious = convertView.findViewById(R.id.tvPrevious);
        TextView tvCurrent = convertView.findViewById(R.id.tvCurrent);

        tvQuarter.setText(data.getQuarter());
        tvPrevious.setText(String.valueOf(data.getPreviousYear()));
        tvCurrent.setText(String.valueOf(data.getCurrentYear()));

        return convertView;
    }

}