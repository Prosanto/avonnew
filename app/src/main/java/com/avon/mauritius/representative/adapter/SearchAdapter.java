package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.ProductList;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import java.util.ArrayList;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {

    Context context;
    ArrayList<ProductList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public SearchAdapter(Context context, ArrayList<ProductList> arrayList) {
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

    public void addAllItem(ArrayList<ProductList> homeItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(homeItemLists);
        notifyDataSetChanged();
    }

    public ProductList getProductList(int position) {
        return arrayList.get(position);
    }

    public void addItem(ProductList homeItemLists) {
        this.arrayList.add(homeItemLists);
        notifyDataSetChanged();
    }

    public void deleteItem(int postion) {
        this.arrayList.remove(postion);
        notifyDataSetChanged();
    }

    public ProductList getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_home, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        viewHolder.txt_productname.setText(arrayList.get(position).getName());
        viewHolder.txt_product_price.setText("Rs " + arrayList.get(position).getPrice());
        viewHolder.txt_product_ref.setText("Ref : " + arrayList.get(position).getProduct_code());
        String imagePath = AllUrls.IMAGEURL_PRODUCT + arrayList.get(position).getPicture();
        ConstantFunctions.loadImageNomal(imagePath, viewHolder.icon_image);

        String qty = context.getResources().getString(R.string.qty);
        viewHolder.txt_product_qty.setText( qty+": " + arrayList.get(position).getStock());

//        if (arrayList.get(position).getStatus().equalsIgnoreCase("0")) {
//            viewHolder.txt_product_stock.setTextColor(context.getResources().getColor(R.color.text_red));
//            viewHolder.txt_product_stock.setText(context.getResources().getString(R.string.Indisponible));
//        } else {
//            viewHolder.txt_product_stock.setText(context.getResources().getString(R.string.Enstock));
//            viewHolder.txt_product_stock.setTextColor(context.getResources().getColor(R.color.text_green));
//        }
        int availableStock = Integer.parseInt(Logger.EmptyStringNumber(arrayList.get(position).getStock()));
        if (availableStock > 0) {
            viewHolder.txt_product_stock.setText(context.getResources().getString(R.string.Enstock));
            viewHolder.txt_product_stock.setTextColor(context.getResources().getColor(R.color.text_green));

        } else {
            viewHolder.txt_product_stock.setTextColor(context.getResources().getColor(R.color.text_red));
            viewHolder.txt_product_stock.setText(context.getResources().getString(R.string.Indisponible));

        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_product_price)
        TextView txt_product_price;
        @BindView(R.id.re_addshop)
        RelativeLayout re_addshop;
        @BindView(R.id.re_single_shopping)
        RelativeLayout re_single_shopping;
        @BindView(R.id.txt_productname)
        TextView txt_productname;
        @BindView(R.id.txt_product_stock)
        TextView txt_product_stock;
        @BindView(R.id.txt_product_ref)
        TextView txt_product_ref;
        @BindView(R.id.icon_image)
        ImageView icon_image;
        @BindView(R.id.txt_product_qty)
        TextView txt_product_qty;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            lFilterItemCallback.ClickFilterItemCallback(0, getAdapterPosition());

        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

}