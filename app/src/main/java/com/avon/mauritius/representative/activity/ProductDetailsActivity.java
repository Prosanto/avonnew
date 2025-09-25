package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.database.CartProductItems;
import com.avon.mauritius.representative.database.CartProducts;
import com.avon.mauritius.representative.database.CartSubProducts;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.model.ProductItems;
import com.avon.mauritius.representative.model.ProductList;
import com.avon.mauritius.representative.model.SubProducts;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsActivity extends AppCompatActivity {
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.edt_amount)
    EditText edt_amount;
    @BindView(R.id.product_image)
    ImageView product_image;
    @BindView(R.id.product_ref)
    TextView product_ref;
    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.txt_price)
    TextView txt_price;
    @BindView(R.id.textprivecy_detail_1)
    TextView textprivecy_detail_1;
    @BindView(R.id.product_layout)
    LinearLayout product_layout;
    @BindView(R.id.layout_for_with_ms)
    LinearLayout layout_for_with_ms;
    @BindView(R.id.layout_for_without_ms)
    LinearLayout layout_for_without_ms;
    @BindView(R.id.for_again_ms)
    LinearLayout for_again_ms;
    @BindView(R.id.product_ref_ms)
    TextView product_ref_ms;
    @BindView(R.id.product_name_ms)
    TextView product_name_ms;
    @BindView(R.id.ms_product_item)
    TextView ms_product_item;
    int totalAmount = 0;
    private ProductList mProductList;
    private HashMap<String, Integer> itemProduct = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_normal_product);
        ButterKnife.bind(this);
        Bundle extra = getIntent().getBundleExtra("extra");
        mProductList = (ProductList) extra.getSerializable("objects");
        initUI();

    }

    private void initUI() {
        String imagePath = AllUrls.IMAGEURL_PRODUCT + mProductList.getPicture();
        ConstantFunctions.loadImageNomal(imagePath, product_image);
        product_ref.setText("Ref: " + mProductList.getProduct_code());
        txt_price.setText("Rs " + mProductList.getPrice());
        product_ref_ms.setText("Ref: " + mProductList.getProduct_code());
        product_name_ms.setText(mProductList.getName());
        product_name.setText(mProductList.getName());


        Log.e("product_type","are"+mProductList.getProduct_type());

        textprivecy_detail_1.setText(mProductList.getDescription());
        if (mProductList.getProduct_type().contains("MS")) {
            ms_product_item.setVisibility(View.VISIBLE);
            layout_for_without_ms.setVisibility(View.GONE);
            layout_for_with_ms.setVisibility(View.GONE);
            for_again_ms.setVisibility(View.VISIBLE);
            edt_amount.setText("1");
            addItemForMS();
        } else if (mProductList.getProduct_type().contains("MA")) {
            ms_product_item.setVisibility(View.GONE);
            layout_for_without_ms.setVisibility(View.VISIBLE);
            layout_for_with_ms.setVisibility(View.VISIBLE);
            for_again_ms.setVisibility(View.GONE);

            addItemForMa();
        } else {
            ms_product_item.setVisibility(View.GONE);
            layout_for_with_ms.setVisibility(View.VISIBLE);
            layout_for_without_ms.setVisibility(View.VISIBLE);
        }

    }

    public void addItemForMa() {
        product_layout.removeAllViews();
        ArrayList<SubProducts> arrayList = mProductList.getAllProductItems();
        Log.e("arrayList","are"+arrayList.size());

        for (SubProducts mSubProducts : arrayList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.row_product_ma, null, false);
            TextView product_item_text = (TextView) view.findViewById(R.id.product_item_text);
            AppCompatRadioButton item_checkbookx = (AppCompatRadioButton) view.findViewById(R.id.item_RadioButton);
            product_item_text.setText(mSubProducts.getName());
            item_checkbookx.setTag(mSubProducts);

            Log.e("mSubProducts","are"+mSubProducts.getId());

            CartSubProducts mCartSubProducts = DatabaseQueryHelper.getCartSubProducts(mSubProducts.getId());

            if (mCartSubProducts != null) {
                item_checkbookx.setChecked(true);
                itemProduct.put(mSubProducts.getId(), 1);
            } else {
                item_checkbookx.setChecked(true);
                itemProduct.put(mSubProducts.getId(), 1);
            }
            item_checkbookx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    RadioButton mCheckBox = (RadioButton) _view;
                    SubProducts mSubProducts = (SubProducts) mCheckBox.getTag();
                    if (itemProduct.containsKey(mSubProducts.getId())) {
                        //itemProduct.remove(mSubProducts.getId());
                        mCheckBox.setChecked(true);
                    } else {
                        itemProduct.put(mSubProducts.getId(), 1);
                        mCheckBox.setChecked(true);
                    }

                }
            });
            view.setTag(mSubProducts);
            product_layout.addView(view);

        }


    }

    public void addItemForMS() {
        product_layout.removeAllViews();
        ArrayList<SubProducts> arrayList = mProductList.getAllProductItems();
        for (SubProducts mSubProducts : arrayList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.row_product_ms, null, false);
            LinearLayout product_items_layout = (LinearLayout) view.findViewById(R.id.product_items_layout);
            TextView sub_product_name = (TextView) view.findViewById(R.id.sub_product_name);
            TextView text_limit = (TextView) view.findViewById(R.id.text_limit);
            sub_product_name.setText(mSubProducts.getName() + " - ");
            text_limit.setText("Limit-" + mSubProducts.getLimit());
            product_items_layout.removeAllViews();
            ArrayList<ProductItems> arrayListProductItems = mSubProducts.getAllProductItems();

            for (ProductItems mProductItems : arrayListProductItems) {
                View viewProductItem = LayoutInflater.from(mContext).inflate(R.layout.row_product_ms_items, null, false);
                TextView product_item_text = (TextView) viewProductItem.findViewById(R.id.product_item_text);
                final CheckBox item_checkbookx = (CheckBox) viewProductItem.findViewById(R.id.item_checkbookx);
                item_checkbookx.setTag(mSubProducts);
                product_item_text.setText(mProductItems.getName());
                //======already added==========
//
//                CartProductItems mCartSubProducts = DatabaseQueryHelper.getCartProductItems(mProductItems.getId());
//                if (mCartSubProducts != null) {
//
//                    item_checkbookx.setChecked(true);
//                    if (itemProduct.containsKey(mSubProducts.getId())) {
//                        int alreadyCount = itemProduct.get(mCartSubProducts.subproduct_id);
//                        alreadyCount = alreadyCount + 1;
//                        itemProduct.put(mCartSubProducts.subproduct_id, alreadyCount);
//                    } else {
//                        itemProduct.put(mCartSubProducts.subproduct_id, 1);
//                    }
//                }

                item_checkbookx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {

                        SubProducts mSubProducts = (SubProducts) item_checkbookx.getTag();
                        if (item_checkbookx.isChecked()) {

                            int countSubProductItems = Integer.parseInt(mSubProducts.getLimit());
                            if (itemProduct.containsKey(mSubProducts.getId())) {
                                int alreadyCount = itemProduct.get(mSubProducts.getId());
                                if (alreadyCount >= countSubProductItems) {
                                    item_checkbookx.setChecked(false);
                                    Intent intent = new Intent(mContext, PopupviewActivity.class);
                                    intent.putExtra("user_id", PersistentUser.getUserID(mContext));
                                    intent.putExtra("screen_type", 3);
                                    intent.putExtra("access_type", 0);
                                    startActivity(intent);

                                } else {
                                    alreadyCount = alreadyCount + 1;
                                    itemProduct.put(mSubProducts.getId(), alreadyCount);
                                }
                            } else {
                                itemProduct.put(mSubProducts.getId(), 1);
                            }


                        } else {
                            if (itemProduct.containsKey(mSubProducts.getId())) {
                                int alreadyCount = itemProduct.get(mSubProducts.getId());
                                alreadyCount = alreadyCount - 1;
                                if (alreadyCount == 0) {
                                    itemProduct.remove(mSubProducts.getId());
                                } else {
                                    itemProduct.put(mSubProducts.getId(), alreadyCount);
                                }
                            }
                        }


                    }
                });
                viewProductItem.setTag(mProductItems);
                product_item_text.setText(mProductItems.getName());
                product_items_layout.addView(viewProductItem);
            }

            view.setTag(mSubProducts);
            product_layout.addView(view);

        }


    }

    @OnClick({R.id.re_decrease_amount, R.id.re_increase_amount, R.id.li_back_product, R.id.li_addto_cart})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.re_increase_amount:
                totalAmount = Integer.parseInt(Logger.EmptyStringNumber(edt_amount.getText().toString().trim()));
                totalAmount = totalAmount + 1;
                edt_amount.setText("" + totalAmount);
                break;
            case R.id.re_decrease_amount:
                totalAmount = Integer.parseInt(Logger.EmptyStringNumber(edt_amount.getText().toString().trim()));
                if (totalAmount > 1)
                    totalAmount = totalAmount - 1;
                else
                    totalAmount = 1;
                edt_amount.setText("" + totalAmount);
                break;
            case R.id.li_back_product:
                finish();
                break;
            case R.id.li_addto_cart:
                int availableStock = Integer.parseInt(Logger.EmptyStringNumber(mProductList.getStock()));
                int orderquantity = Integer.parseInt(Logger.EmptyStringNumber(edt_amount.getText().toString()));
                if (mProductList.getStatus().equalsIgnoreCase("0")) {
                    intent = new Intent(mContext, PopupviewActivity.class);
                    intent.putExtra("user_id", PersistentUser.getUserID(mContext));
                    intent.putExtra("screen_type", 7);
                    intent.putExtra("access_type", 0);
                    intent.putExtra("product_name", product_name_error);
                    intent.putExtra("product_linit", product_linit);
                    startActivity(intent);
                } else if (orderquantity > availableStock) {
                    intent = new Intent(mContext, PopupviewActivity.class);
                    intent.putExtra("screen_type", 9);
                    intent.putExtra("user_id", "");
                    intent.putExtra("productQty", availableStock);
                    startActivity(intent);
                } else {
                    int quantity = 0;
                    if (mProductList.getProduct_type().contains("MS")) {
                        String message = validationforMS();
                        if (message.equalsIgnoreCase("")) {
                            addIntoCart(quantity);
                        } else {
                            intent = new Intent(mContext, PopupviewActivity.class);
                            intent.putExtra("user_id", PersistentUser.getUserID(mContext));
                            intent.putExtra("screen_type", 6);
                            intent.putExtra("access_type", 0);
                            intent.putExtra("product_name", product_name_error);
                            intent.putExtra("product_linit", product_linit);
                            startActivity(intent);

                        }
                    } else {
                        CartProducts mCartProducts = DatabaseQueryHelper.getCartProductID(mProductList.getId());
                        if (mCartProducts != null) {
                            quantity = Integer.parseInt(mCartProducts.quantity);
                        }
                        DatabaseQueryHelper.DeleteCartProductsbyProductID(mProductList.getId());
                        addIntoCart(quantity);
                    }
                }
                break;
            default:
                break;

        }
    }

    public void addIntoCart(int quantityPrevious) {
        int quantity = 0;
        quantity = Integer.parseInt(Logger.EmptyStringNumber(edt_amount.getText().toString()));
        quantity = quantity + quantityPrevious;
        CartProducts mCartProducts = new CartProducts();
        mCartProducts.product_id = mProductList.getId();
        mCartProducts.name = mProductList.getName();
        mCartProducts.product_type = mProductList.getProduct_type();
        mCartProducts.product_code = mProductList.getProduct_code();
        mCartProducts.price = mProductList.getPrice();
        mCartProducts.limit_product = mProductList.getLimit();
        mCartProducts.picture = mProductList.getPicture();
        mCartProducts.quantity = "" + quantity;
        mCartProducts.save();

        if (mProductList.getProduct_type().contains("MS")) {
            int layoutcount = product_layout.getChildCount();
            for (int index = 0; index < layoutcount; index++) {

                View nextChild = product_layout.getChildAt(index);
                SubProducts mSubProducts = (SubProducts) nextChild.getTag();
                CartSubProducts mCartSubProducts = new CartSubProducts();
                mCartSubProducts.subproduct_id = mSubProducts.getId();
                mCartSubProducts.product_id = mProductList.getId();
                mCartSubProducts.name = mSubProducts.getName();
                mCartSubProducts.product_code = mSubProducts.getProduct_code();
                mCartSubProducts.product_save_id = mCartProducts.getId();
                mCartSubProducts.save();

                LinearLayout itemnextChild = (LinearLayout) nextChild.findViewById(R.id.product_items_layout);
                int layoutcountMain = itemnextChild.getChildCount();
                for (int type = 0; type < layoutcountMain; type++) {
                    View nextChildMain = itemnextChild.getChildAt((type));
                    CheckBox item_checkbookx = (CheckBox) nextChildMain.findViewById(R.id.item_checkbookx);
                    ProductItems mProductItems = (ProductItems) nextChildMain.getTag();
                    if (item_checkbookx.isChecked()) {
                        CartProductItems mSubProductItems = new CartProductItems();
                        mSubProductItems.productitems_id = mProductItems.getId();
                        mSubProductItems.subproduct_id = mSubProducts.getId();
                        mSubProductItems.name = mProductItems.getName();
                        mSubProductItems.product_code = mProductItems.getProduct_code();
                        mSubProductItems.subproduct_save_id = mCartSubProducts.getId();
                        mSubProductItems.save();
                    }
                }

            }


        } else if (mProductList.getProduct_type().contains("MA")) {
            int layoutcount = product_layout.getChildCount();
            for (int index = 0; index < layoutcount; index++) {
                View nextChild = product_layout.getChildAt(index);
                SubProducts mSubProducts = (SubProducts) nextChild.getTag();
                AppCompatRadioButton item_checkbookx = (AppCompatRadioButton) nextChild.findViewById(R.id.item_RadioButton);
                if (item_checkbookx.isChecked()) {
                    CartSubProducts mCartSubProducts = new CartSubProducts();
                    mCartSubProducts.subproduct_id = mSubProducts.getId();
                    mCartSubProducts.product_id = mSubProducts.getProduct_id();
                    mCartSubProducts.name = mSubProducts.getName();
                    mCartSubProducts.product_code = mSubProducts.getProduct_code();
                    mCartSubProducts.product_save_id = mCartProducts.getId();
                    mCartSubProducts.save();

                }
            }
        }

        Intent intent = new Intent(mContext, ProductAddedActivity.class);
        startActivity(intent);
        finish();

    }

    private String product_name_error = "";
    private String product_linit = "";

    public String validationforMS() {
        String message = "";
        int layoutcount = product_layout.getChildCount();
        for (int index = 0; index < layoutcount; index++) {
            View nextChild = product_layout.getChildAt(index);
            SubProducts mSubProducts = (SubProducts) nextChild.getTag();
            LinearLayout itemnextChild = (LinearLayout) nextChild.findViewById(R.id.product_items_layout);
            int layoutcountMain = itemnextChild.getChildCount();
            int limit = Integer.parseInt(Logger.EmptyStringNumber(mSubProducts.getLimit()));
            int checkLimit = 0;
            for (int type = 0; type < layoutcountMain; type++) {
                View nextChildMain = itemnextChild.getChildAt((type));
                CheckBox item_checkbookx = (CheckBox) nextChildMain.findViewById(R.id.item_checkbookx);
                ProductItems mProductItems = (ProductItems) nextChildMain.getTag();
                if (item_checkbookx.isChecked()) {
                    checkLimit = checkLimit + 1;
                }
            }
            if (limit != checkLimit) {
                product_name_error = mSubProducts.getName();
                product_linit = " - " + " Limit " + limit;
                message = mSubProducts.getName() + "-" + "Limit " + limit;
                break;
            }
        }
        return message;
    }

}