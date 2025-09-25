package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.OrderHistoryProduct;
import com.avon.mauritius.representative.model.OrderHistorylist;
import com.avon.mauritius.representative.utils.DateUtility;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.viewHolder> {

    Context context;
    ArrayList<OrderHistorylist> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistorylist> arrayList) {
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

    public void addAllItem(ArrayList<OrderHistorylist> billItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(billItemLists);
        notifyDataSetChanged();
    }

    public void addItem(OrderHistorylist billItemList) {
        this.arrayList.add(billItemList);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public OrderHistorylist getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_orderhistory, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        OrderHistorylist orderHistoryProduct = arrayList.get(position);
        ArrayList<OrderHistoryProduct> allCartProductItems = orderHistoryProduct.getOrderHistoryProducts();
        String textMessage = DateUtility.getFormattedDate2(arrayList.get(position).getOrder_date());
        viewHolder.txt_date.setText(" " + textMessage);

        viewHolder.layout_add_order_hisoty.removeAllViews();
        int ordrPosition = 0;
        String orderData = "";

        for (OrderHistoryProduct orderHistoryProduct1 : allCartProductItems) {

            View viewProductItem = LayoutInflater.from(context).inflate(R.layout.row_order_history, null, false);
            TextView txt_order_id = (TextView) viewProductItem.findViewById(R.id.txt_order_id);
            TextView txt_prodectname = (TextView) viewProductItem.findViewById(R.id.txt_prodectname);
            TextView txt_order_price = (TextView) viewProductItem.findViewById(R.id.txt_order_price);
            View oder_different = (View) viewProductItem.findViewById(R.id.oder_different);
            TextView txt_order_date_time = (TextView) viewProductItem.findViewById(R.id.txt_order_date_time);


            int quantity = Integer.parseInt(Logger.EmptyStringNumber(orderHistoryProduct1.getQty()));
            double price = Double.parseDouble(Logger.EmptyStringNumber(orderHistoryProduct1.getPrice()));
            double value = quantity * price;
            txt_order_id.setText(" " + quantity);
            txt_prodectname.setText(" " + orderHistoryProduct1.getName() + " - " + orderHistoryProduct1.getProduct_code());
            txt_order_price.setText(" Rs " + value);
            viewHolder.layout_add_order_hisoty.addView(viewProductItem);
            String at= context.getResources().getString(R.string.at);

            oder_different.setVisibility(View.GONE);
            txt_order_date_time.setVisibility(View.GONE);

            if (ordrPosition == 0) {
                orderData = orderHistoryProduct1.getOrder_id();
                ordrPosition = ordrPosition + 1;
                String order_date_time = Logger.EmptyString(orderHistoryProduct1.getOrder_date_time());
                if (order_date_time.equalsIgnoreCase("")) {
                    txt_order_date_time.setVisibility(View.GONE);
                } else {
                    txt_order_date_time.setVisibility(View.VISIBLE);
                    txt_order_date_time.setText(textMessage+" "+at+ " "+DateUtility.getFormattedDate3(order_date_time));
                }
            } else {

                if (orderData.equalsIgnoreCase(orderHistoryProduct1.getOrder_id())) {
                    oder_different.setVisibility(View.GONE);
                    orderData = orderHistoryProduct1.getOrder_id();

                } else {
                    oder_different.setVisibility(View.VISIBLE);
                    orderData = orderHistoryProduct1.getOrder_id();
                    String order_date_time = Logger.EmptyString(orderHistoryProduct1.getOrder_date_time());
                    if (order_date_time.equalsIgnoreCase("")) {
                        txt_order_date_time.setVisibility(View.GONE);
                    } else {
                        txt_order_date_time.setVisibility(View.VISIBLE);
                        txt_order_date_time.setText(textMessage+" "+at+ " "+DateUtility.getFormattedDate3(order_date_time));
                    }

                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.layout_add_order_hisoty)
        LinearLayout layout_add_order_hisoty;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}