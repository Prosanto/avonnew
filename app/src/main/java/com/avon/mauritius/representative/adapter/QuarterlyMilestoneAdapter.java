package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;

import java.util.ArrayList;
import java.util.List;

public class QuarterlyMilestoneAdapter extends BaseAdapter {

    private Context context;
    private List<QuarterlyData> quarterlyList;

    // Data model for each quarter
    public static class QuarterlyData {
        public String quarterLabel;
        public String amount;
        public boolean isCompleted;
        public boolean showTotalSales;
        public String totalSalesAmount;
        public boolean isFirst;
        public boolean isLast;

        public QuarterlyData(String label, String amount, boolean completed,
                             boolean showTotal, String totalAmount, boolean first, boolean last) {
            this.quarterLabel = label;
            this.amount = amount;
            this.isCompleted = completed;
            this.showTotalSales = showTotal;
            this.totalSalesAmount = totalAmount;
            this.isFirst = first;
            this.isLast = last;
        }
    }

    public QuarterlyMilestoneAdapter(Context context, List<QuarterlyData> data) {
        this.context = context;
        this.quarterlyList = data;
    }

    @Override
    public int getCount() {
        return quarterlyList.size();
    }

    @Override
    public Object getItem(int position) {
        return quarterlyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.row_quarterly_milestone, parent, false);

            holder = new ViewHolder();
            holder.topLine = convertView.findViewById(R.id.top_line);
            holder.bottomLine = convertView.findViewById(R.id.bottom_line);
            holder.outerCircle = convertView.findViewById(R.id.outer_circle);
            holder.innerCircle = convertView.findViewById(R.id.inner_circle);
            holder.quarterLabel = convertView.findViewById(R.id.quarter_label);
            holder.quarterAmount = convertView.findViewById(R.id.quarter_amount);
            holder.starIcon = convertView.findViewById(R.id.star_icon);
            holder.completionStatus = convertView.findViewById(R.id.completion_status);
            holder.totalSalesContainer = convertView.findViewById(R.id.total_sales_container);
            holder.totalSalesText = convertView.findViewById(R.id.total_sales_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QuarterlyData data = quarterlyList.get(position);

        // Set quarter label and amount
        holder.quarterLabel.setText(data.quarterLabel);
        holder.quarterAmount.setText(data.amount);

        // Configure timeline lines
        if (data.isFirst) {
            holder.topLine.setVisibility(View.INVISIBLE);
        } else {
            holder.topLine.setVisibility(View.VISIBLE);
            if (data.isCompleted) {
                holder.topLine.setBackgroundColor(0xFFE91E63); // Pink for completed
            } else {
                holder.topLine.setBackgroundColor(0xFFCCCCCC); // Gray for pending
            }
        }

        if (data.isLast) {
            holder.bottomLine.setVisibility(View.INVISIBLE);
        } else {
            holder.bottomLine.setVisibility(View.VISIBLE);
            if (data.isCompleted) {
                holder.bottomLine.setBackgroundColor(0xFFE91E63); // Pink for completed
            } else {
                holder.bottomLine.setBackgroundColor(0xFFCCCCCC); // Gray for pending
            }
        }

        // Configure circles based on completion status
        if (data.isCompleted) {
            holder.outerCircle.setBackgroundResource(R.drawable.circle_outer_completed);
            holder.innerCircle.setBackgroundResource(R.drawable.circle_inner_completed);
            holder.starIcon.setVisibility(View.VISIBLE);
            holder.completionStatus.setVisibility(View.VISIBLE);
        } else {
            holder.outerCircle.setBackgroundResource(R.drawable.circle_outer_pending);
            holder.innerCircle.setBackgroundResource(R.drawable.circle_inner_pending);
            holder.starIcon.setVisibility(View.GONE);
            holder.completionStatus.setVisibility(View.GONE);
        }

        // Show total sales if needed
        if (data.showTotalSales) {
            holder.totalSalesContainer.setVisibility(View.VISIBLE);
            holder.totalSalesText.setText("Total Sales : " + data.totalSalesAmount);
        } else {
            holder.totalSalesContainer.setVisibility(View.GONE);
        }

        return convertView;
    }
    static class ViewHolder {
        View topLine;
        View bottomLine;
        View outerCircle;
        View innerCircle;
        TextView quarterLabel;
        TextView quarterAmount;
        ImageView starIcon;
        TextView completionStatus;
        LinearLayout totalSalesContainer;
        TextView totalSalesText;
    }
    public static List<QuarterlyData> getDummyData() {
        List<QuarterlyData> data = new ArrayList<>();

        data.add(new QuarterlyData(
                "Quarter 1",
                "Rs 70,000",
                true,      // Completed
                false,     // Don't show total sales
                "",
                true,      // First item
                false
        ));

        data.add(new QuarterlyData(
                "Quarter 2",
                "Rs 115,000",
                true,      // Completed
                false,     // Don't show total sales
                "",
                false,
                false
        ));

        data.add(new QuarterlyData(
                "Quarter 3",
                "Rs 160,000",
                false,     // Not completed (in progress)
                true,      // Show total sales
                "Rs 180,000",
                false,
                false
        ));

        data.add(new QuarterlyData(
                "Quarter  4",
                "Rs 250,000",
                false,     // Not completed (pending)
                false,     // Don't show total sales
                "",
                false,
                true       // Last item
        ));


        return data;
    }
}