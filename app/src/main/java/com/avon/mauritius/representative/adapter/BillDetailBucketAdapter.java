package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.BillProductItems;
import com.avon.mauritius.representative.model.BillProductList;
import com.avon.mauritius.representative.model.BillSubProducts;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillDetailBucketAdapter extends RecyclerView.Adapter<BillDetailBucketAdapter.viewHolder> {

    Context context;
    ArrayList<BillProductList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public BillDetailBucketAdapter(Context context, ArrayList<BillProductList> arrayList) {
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

    public void addAllItem(ArrayList<BillProductList> bucketItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(bucketItemLists);
        notifyDataSetChanged();
    }

    public void addItem(BillProductList bucketItemLists) {
        this.arrayList.add(bucketItemLists);
        notifyDataSetChanged();
    }

    public void deleteItem(int postion) {
        this.arrayList.remove(postion);
        notifyDataSetChanged();
    }

    public BillProductList getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bill_bucket, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        BillProductList mBillProductList = arrayList.get(position);
        viewHolder.txt_bill_productname.setText(mBillProductList.getName());
        int quty = Integer.parseInt(Logger.EmptyStringNumber(mBillProductList.getQty()));
        double Price = Double.parseDouble(Logger.EmptyStringNumber(mBillProductList.getPrice()));
        viewHolder.bill_productprice.setText("Rs " + (Price * quty));
        viewHolder.orderqty.setText(""+quty);

        if (mBillProductList.getProduct_type().contains("NA")) {
            viewHolder.bill_product_details.setText("");

        } else if (mBillProductList.getProduct_type().contains("MA")) {
            viewHolder.bill_product_details.setText(detailsMAProducts(mBillProductList.getProduct_id(), position));
        }
        if (mBillProductList.getProduct_type().contains("MS")) {
            viewHolder.bill_product_details.setText(detailsMSProducts(position));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bill_productprice)
        TextView bill_productprice;
        @BindView(R.id.bill_product_details)
        TextView bill_product_details;
        @BindView(R.id.txt_bill_productname)
        TextView txt_bill_productname;
        @BindView(R.id.orderqty)
        TextView orderqty;


        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String detailsMAProducts(String product_id, int pos) {
        String textInfo = "";
        BillProductList mBillProductList = arrayList.get(pos);
        ArrayList<BillSubProducts> allCartSubProducts = mBillProductList.getBillSubProducts();
        for (BillSubProducts mCartSubProducts : allCartSubProducts) {
            textInfo = textInfo + mCartSubProducts.getName() + " - Ref " + mCartSubProducts.getProduct_code() + "\n";
        }
        return textInfo;
    }

    public String detailsMSProducts(int pos) {
        String textInfo = "";
        BillProductList mBillProductList = arrayList.get(pos);
        ArrayList<BillSubProducts> allCartSubProducts = mBillProductList.getBillSubProducts();
        for (BillSubProducts mCartSubProducts : allCartSubProducts) {
            String subtextInfo = "";
            subtextInfo = subtextInfo + mCartSubProducts.getName() + " - Ref " + mCartSubProducts.getProduct_code();
            String itemtextInfo = "";

            ArrayList<BillProductItems> allCartProductItems = mCartSubProducts.getBillProductItems();
            for (BillProductItems mCartProductItems : allCartProductItems) {
                itemtextInfo = itemtextInfo + mCartProductItems.getName() + " - Ref " + mCartProductItems.getProduct_code();
            }
            subtextInfo = subtextInfo + "   " + "\n\n" + itemtextInfo;
            textInfo = textInfo + "\n\n" + subtextInfo;

        }
        return textInfo;
    }
}