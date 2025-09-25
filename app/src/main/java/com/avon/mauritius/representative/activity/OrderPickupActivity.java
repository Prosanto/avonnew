
package com.avon.mauritius.representative.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.database.CartProductItems;
import com.avon.mauritius.representative.database.CartProducts;
import com.avon.mauritius.representative.database.CartSubProducts;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.BusyDialog;
import com.avon.mauritius.representative.utils.DateUtility;
import com.avon.mauritius.representative.utils.Helpers;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderPickupActivity extends Activity {
    private static final String TAG = OrderPickupActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.layout_pickup_1)
    RelativeLayout layout_pickup_1;
    @BindView(R.id.layout_pickup_2)
    RelativeLayout layout_pickup_2;
    @BindView(R.id.layout_pickup_3)
    RelativeLayout layout_pickup_3;
    @BindView(R.id.layout_pickup_4)
    RelativeLayout layout_pickup_4;
    @BindView(R.id.image_pickup_1)
    AppCompatImageView image_pickup_1;
    @BindView(R.id.image_pickup_2)
    AppCompatImageView image_pickup_2;
    @BindView(R.id.image_pickup_3)
    AppCompatImageView image_pickup_3;
    @BindView(R.id.image_pickup_4)
    AppCompatImageView image_pickup_4;
    private String destination = "";
    double totalValueForOder = 0;
    private BusyDialog mBusyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_orderpickup);
        ButterKnife.bind(this);
        initUI();

    }

    private void initUI() {

        totalValueForOder = 0;
        makeJsondataforOrder();
        selectionOrderDestination(1);
    }

    @OnClick({R.id.layout_confirm, R.id.li_back_order_pick, R.id.img_orderpickup, R.id.layout_pickup_1, R.id.layout_pickup_2, R.id.layout_pickup_3, R.id.layout_pickup_4})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.li_back_order_pick:
                finish();
                break;
            case R.id.img_orderpickup:
                forgotPasswordServerRequest();
                break;
            case R.id.layout_confirm:
                forgotPasswordServerRequest();
                break;

            case R.id.layout_pickup_1:
                selectionOrderDestination(1);
                break;

            case R.id.layout_pickup_2:
                selectionOrderDestination(2);
                break;

            case R.id.layout_pickup_3:
                selectionOrderDestination(3);
                break;

            case R.id.layout_pickup_4:
                selectionOrderDestination(4);
                break;

            default:
                break;

        }
    }

    public void selectionOrderDestination(int pistion) {

        image_pickup_1.setImageResource(R.drawable.ic_ok_gray);//ic_text is a Vector Image
        image_pickup_2.setImageResource(R.drawable.ic_ok_gray);//ic_text is a Vector Image
        image_pickup_3.setImageResource(R.drawable.ic_ok_gray);//ic_text is a Vector Image
        image_pickup_4.setImageResource(R.drawable.ic_ok_gray);//ic_text is a Vector Image

        if (pistion == 1) {
            destination = getResources().getString(R.string.pickup_1);
            destination = "Port Louis";
            image_pickup_1.setImageResource(R.drawable.ic_ok_pink);//ic_text is a Vector Image
        } else if (pistion == 2) {
            destination = getResources().getString(R.string.pickup_2);
            destination = "Curepipe";
            image_pickup_2.setImageResource(R.drawable.ic_ok_pink);//ic_text is a Vector Image
        } else if (pistion == 3) {
            destination = getResources().getString(R.string.pickup_3);
            destination   = "Home Delivery";
            image_pickup_3.setImageResource(R.drawable.ic_ok_pink);//ic_text is a Vector Image
        } else if (pistion == 4) {
            destination = getResources().getString(R.string.pickup_4);
            destination = "Rodrigues";
            image_pickup_4.setImageResource(R.drawable.ic_ok_pink);//ic_text is a Vector Image
        }

    }

    private void forgotPasswordServerRequest() {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("order_divistion", destination);
        hashMap.put("amount", "" + totalValueForOder);
        hashMap.put("order_date_time", DateUtility.getCurrentday());

        hashMap.put("order_data", makeJsondataforOrder());
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "productorder";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {

                try {
                    mBusyDialog.dismis();
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {

                        Intent intent = new Intent(mContext, OrderSentActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent intent = new Intent(mContext, PopupviewActivity.class);
                        intent.putExtra("screen_type", 4);
                        intent.putExtra("user_id", "");
                        startActivity(intent);

                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                try {
                    mBusyDialog.dismis();

                    Logger.debugLog("onFailed", serverResponse);
                    JSONObject mJsonObject = new JSONObject(serverResponse);
                    String message = mJsonObject.getString("message");
                    ToastHelper.showToast(mContext, message);
                } catch (Exception ex) {

                }
            }
        });
    }

    public String makeJsondataforOrder() {
        totalValueForOder = 0;
        JSONArray mJsonArray = new JSONArray();
        try {
            ArrayList<CartProducts> allCartProducts = DatabaseQueryHelper.getProducts();
            for (CartProducts mCartProducts : allCartProducts) {

                JSONObject mObject = new JSONObject();
                double totalValue = 0;
                int quantity = Integer.parseInt(Logger.EmptyStringNumber(mCartProducts.quantity));
                double price = Double.parseDouble(Logger.EmptyStringNumber(mCartProducts.price));
                double value = price * quantity;
                totalValue = totalValue + value;

                totalValueForOder = totalValueForOder + totalValue;

                JSONArray subProductJSONArray = new JSONArray();

                if (mCartProducts.product_type.contains("NA")) {
                    //mObject.put("SubProducts",subProductJSONArray);

                } else if (mCartProducts.product_type.contains("MA")) {
                    ArrayList<CartSubProducts> allCartSubProducts = DatabaseQueryHelper.getSubProductsbyproductsave(mCartProducts.getId());
                    for (CartSubProducts mCartSubProducts : allCartSubProducts) {
                        JSONObject mJsonObject = new JSONObject();
                        JSONArray ProductItemsJSONArray = new JSONArray();
                        mJsonObject.put("id", mCartSubProducts.subproduct_id);
                        mJsonObject.put("product_id", mCartSubProducts.product_id);
                        mJsonObject.put("name", mCartSubProducts.name);
                        mJsonObject.put("product_code", mCartSubProducts.product_code);
                        mJsonObject.put("ProductItems", ProductItemsJSONArray);
                        subProductJSONArray.put(mJsonObject);
                    }

                    // mObject.put("SubProducts",subProductJSONArray);


                }
                if (mCartProducts.product_type.contains("MS")) {
                    ArrayList<CartSubProducts> allCartSubProducts = DatabaseQueryHelper.getSubProductsbyproductsave(mCartProducts.getId());
                    for (CartSubProducts mCartSubProducts : allCartSubProducts) {
                        JSONArray ProductItemsJSONArray = new JSONArray();
                        ArrayList<CartProductItems> allCartProductItems = DatabaseQueryHelper.getCartProductItemsList(mCartSubProducts.getId());
                        for (CartProductItems mCartProductItems : allCartProductItems) {
                            JSONObject mJsonObjectItem = new JSONObject();
                            mJsonObjectItem.put("id", mCartProductItems.productitems_id);
                            mJsonObjectItem.put("subproduct_id", mCartSubProducts.subproduct_id);
                            mJsonObjectItem.put("name", mCartProductItems.name);
                            mJsonObjectItem.put("product_code", mCartProductItems.product_code);
                            ProductItemsJSONArray.put(mJsonObjectItem);
                        }
                        JSONObject mJsonObject = new JSONObject();
                        mJsonObject.put("id", mCartSubProducts.subproduct_id);
                        mJsonObject.put("product_id", mCartSubProducts.product_id);
                        mJsonObject.put("name", mCartSubProducts.name);
                        mJsonObject.put("product_code", mCartSubProducts.product_code);
                        mJsonObject.put("ProductItems", ProductItemsJSONArray);

                        subProductJSONArray.put(mJsonObject);

                    }

                }

                mObject.put("id", mCartProducts.product_id);
                mObject.put("name", mCartProducts.name);
                mObject.put("product_type", mCartProducts.product_type);
                mObject.put("product_code", mCartProducts.product_code);
                mObject.put("product_category", "");
                mObject.put("price", mCartProducts.price);
                mObject.put("product_quote", totalValue);
                mObject.put("qty", mCartProducts.quantity);
                mObject.put("SubProducts", subProductJSONArray);
                mObject.put("order_date_time",DateUtility.getCurrentday());


                mJsonArray.put(mObject);

            }

        } catch (Exception ex) {
            Log.e("Exception", ex.getMessage());
        }

        Log.e("mJsonArray", mJsonArray.toString());
        return mJsonArray.toString();

    }
}