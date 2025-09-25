package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.BillItemList;
import com.avon.mauritius.representative.utils.DateUtility;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.viewHolder> {

    Context context;
    ArrayList<BillItemList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public BillingAdapter(Context context, ArrayList<BillItemList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        String storeLang = PersistentUser.getLanguage(context);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        this.context.getResources().updateConfiguration(config, this.context.getResources().getDisplayMetrics());

    }

    public void addClickListiner(FilterItemCallback lFilterItemCallback) {
        this.lFilterItemCallback = lFilterItemCallback;
    }

    public void addAllItem(ArrayList<BillItemList> billItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(billItemLists);
        notifyDataSetChanged();
    }

    public void addItem(BillItemList billItemList) {
        this.arrayList.add(billItemList);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public BillItemList getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bill, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        viewHolder.txt_ordernumber.setText(" " + arrayList.get(position).getOrder_unique_id());
        String textMessage = DateUtility.getFormattedDate(arrayList.get(position).getOrder_date());
        viewHolder.txt_orderdate.setText(" " + textMessage);
        viewHolder.txt_totalamount.setText(" Rs " + arrayList.get(position).getAmount());

        String order_date_time = Logger.EmptyString(arrayList.get(position).getOrder_date_time());
        if (order_date_time.equalsIgnoreCase("")) {
            viewHolder.layouttxt_orderdate_time.setVisibility(View.GONE);
        } else {
            viewHolder.layouttxt_orderdate_time.setVisibility(View.VISIBLE);
            viewHolder.txt_orderdate_time.setText(DateUtility.getFormattedDate3(order_date_time));
        }

        viewHolder.image_for_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ordernumber)
        TextView txt_ordernumber;
        @BindView(R.id.txt_orderdate)
        TextView txt_orderdate;
        @BindView(R.id.txt_totalamount)
        TextView txt_totalamount;
        @BindView(R.id.image_for_order)
        ImageView image_for_order;
        @BindView(R.id.txt_orderdate_time)
        TextView txt_orderdate_time;
        @BindView(R.id.layouttxt_orderdate_time)
        LinearLayout layouttxt_orderdate_time;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}