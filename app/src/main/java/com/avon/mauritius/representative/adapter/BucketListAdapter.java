package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.database.CartProductItems;
import com.avon.mauritius.representative.database.CartProducts;
import com.avon.mauritius.representative.database.CartSubProducts;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.model.ProductList;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.viewHolder> {

    Context context;
    ArrayList<CartProducts> arrayList;
    private FilterItemCallback lFilterItemCallback;
    private HashMap<String, ProductList> unavailaleallProductList = new HashMap<>();

    public BucketListAdapter(Context context, ArrayList<CartProducts> arrayList) {
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

    public void addUnavailableproduct(HashMap<String, ProductList> productLists) {
        this.unavailaleallProductList.clear();
        this.unavailaleallProductList.putAll(productLists);
        notifyDataSetChanged();
    }

    public void addAllItem(ArrayList<CartProducts> bucketItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(bucketItemLists);
        notifyDataSetChanged();
    }

    public void addItem(CartProducts bucketItemLists) {
        this.arrayList.add(bucketItemLists);
        notifyDataSetChanged();
    }

    public void deleteItem(int postion) {
        this.arrayList.remove(postion);
        notifyDataSetChanged();
    }

    public CartProducts getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bucket, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        CartProducts mCartProducts = arrayList.get(position);
        String text = mCartProducts.name + " - Ref " + mCartProducts.product_code;

        String imagePath = AllUrls.IMAGEURL_PRODUCT + mCartProducts.picture;
        ConstantFunctions.loadImageNomal(imagePath, viewHolder.product_images);
        viewHolder.product_out_of_stock.setVisibility(View.GONE);

        if (unavailaleallProductList.containsKey(mCartProducts.product_code)) {
            ProductList mProductList= unavailaleallProductList.get(mCartProducts.product_code);
            viewHolder.product_out_of_stock.setVisibility(View.VISIBLE);
//            viewHolder.product_out_of_stock.setText(context.getResources().getString(R.string.Qtyavailable_2)+": "+mProductList.getStock());
            viewHolder.product_out_of_stock.setText(context.getResources().getString(R.string.Qtyavailable_2));
        }

        viewHolder.txt_productname.setText(text);
        if (mCartProducts.product_type.contains("NM")) {
            viewHolder.layout_price.setVisibility(View.VISIBLE);
            viewHolder.product_details.setText("Ref- " + mCartProducts.product_code);

        } else if (mCartProducts.product_type.contains("MA")) {
            viewHolder.layout_price.setVisibility(View.VISIBLE);
            viewHolder.product_details.setText(detailsMAProducts(mCartProducts.getId()));
        }
        if (mCartProducts.product_type.contains("MS")) {
            viewHolder.layout_price.setVisibility(View.GONE);
            viewHolder.product_details.setText(detailsMSProducts(mCartProducts.getId()));

        }
        int quantity = Integer.parseInt(Logger.EmptyStringNumber(mCartProducts.quantity));
        viewHolder.edt_amount.setText("" + quantity);
        double price = Double.parseDouble(Logger.EmptyStringNumber(mCartProducts.price));
        double value = price * quantity;
        viewHolder.txt_price.setText("Rs " + value);
        viewHolder.ima_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });
        viewHolder.txt_productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(5, position);
            }
        });

        viewHolder.re_decrease_amount.setTag(mCartProducts.product_id);
        viewHolder.re_decrease_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_id = view.getTag().toString();

                CartProducts cartProducts = DatabaseQueryHelper.getCartProductID(product_id);
                if (cartProducts != null) {
                    int quantity = Integer.parseInt(Logger.EmptyStringNumber(cartProducts.quantity));
                    if (quantity > 1) {
                        quantity = quantity - 1;
                        cartProducts.quantity = "" + quantity;
                        cartProducts.save();
                        notifyDataSetChanged();
                        lFilterItemCallback.ClickFilterItemCallback(1, position);

                    }
                } else {
                    Log.e("Not found", "are" + mCartProducts.product_id);
                }

            }
        });
        viewHolder.re_increase_amount.setTag(mCartProducts.product_id);
        viewHolder.re_increase_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_id = view.getTag().toString();
                CartProducts cartProducts = DatabaseQueryHelper.getCartProductID(product_id);
                if (cartProducts != null) {
                    int quantity = Integer.parseInt(Logger.EmptyStringNumber(cartProducts.quantity));
                    quantity = quantity + 1;
                    cartProducts.quantity = "" + quantity;
                    cartProducts.save();
                    notifyDataSetChanged();
                    lFilterItemCallback.ClickFilterItemCallback(1, position);

                } else {
                    Log.e("Not found", "are" + mCartProducts.product_id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_productname)
        TextView txt_productname;
        @BindView(R.id.txt_price)
        TextView txt_price;
        @BindView(R.id.product_out_of_stock)
        TextView product_out_of_stock;

        @BindView(R.id.ima_trash)
        ImageView ima_trash;
        @BindView(R.id.product_images)
        ImageView product_images;
        @BindView(R.id.product_details)
        TextView product_details;
        @BindView(R.id.layout_price)
        LinearLayout layout_price;
        @BindView(R.id.edt_amount)
        EditText edt_amount;
        @BindView(R.id.re_decrease_amount)
        RelativeLayout re_decrease_amount;
        @BindView(R.id.re_increase_amount)
        RelativeLayout re_increase_amount;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String detailsMAProducts(long product_id) {
        String textInfo = "";
        ArrayList<CartSubProducts> allCartSubProducts = DatabaseQueryHelper.getSubProductsbyproductsave(product_id);
        for (CartSubProducts mCartSubProducts : allCartSubProducts) {
            textInfo = textInfo + mCartSubProducts.name + " - Ref " + mCartSubProducts.product_code + "\n";
        }
        return textInfo;
    }

    public String detailsMSProducts(long product_id) {
        String textInfo = "";
        ArrayList<CartSubProducts> allCartSubProducts = DatabaseQueryHelper.getSubProductsbyproductsave(product_id);
        for (CartSubProducts mCartSubProducts : allCartSubProducts) {
            String subtextInfo = "";
            subtextInfo = subtextInfo + mCartSubProducts.name + "\n";
            String itemtextInfo = "";
            ArrayList<CartProductItems> allCartProductItems = DatabaseQueryHelper.getCartProductItemsList(mCartSubProducts.getId());
            for (CartProductItems mCartProductItems : allCartProductItems) {
                itemtextInfo = itemtextInfo + mCartProductItems.name + " - Ref " + mCartProductItems.product_code + "\n";
            }
            subtextInfo = subtextInfo + "   " + "\n" + itemtextInfo;
            textInfo = textInfo + "\n" + subtextInfo;

        }
        return textInfo;
    }
}