
package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.BucketListAdapter;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.database.CartProductItems;
import com.avon.mauritius.representative.database.CartProducts;
import com.avon.mauritius.representative.database.CartSubProducts;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.model.ProductList;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.reservoir.Reservoir;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.BusyDialog;
import com.avon.mauritius.representative.utils.Helpers;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BucketListActivity extends BaseActivity {
    private static final String TAG = BucketListActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView bucket_RecyclerView;
    BucketListAdapter adapter;
    @BindView(R.id.layout_have_cart)
    RelativeLayout layout_have_cart;
    @BindView(R.id.layout_no_card)
    LinearLayout layout_no_card;
    @BindView(R.id.total_amount)
    TextView total_amount;
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
        getLayoutInflater().inflate(R.layout.activity_bucketlist, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 1;
        selectedDeselectedLayut();
        initUI();
    }

    private void initUI() {
        layout_have_cart.setVisibility(View.GONE);
        layout_no_card.setVisibility(View.GONE);
        int sizeCard = DatabaseQueryHelper.getProducts().size();
        if (sizeCard > 0) {
            layout_have_cart.setVisibility(View.VISIBLE);
        } else {
            layout_no_card.setVisibility(View.VISIBLE);
        }
        bucket_RecyclerView = (RecyclerView) findViewById(R.id.bucket_RecyclerView);
        LinearLayoutManager mLinearLayoutManagerRadio = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        bucket_RecyclerView.setLayoutManager(mLinearLayoutManagerRadio);
        adapter = new BucketListAdapter(getApplicationContext(), new ArrayList<CartProducts>());
        bucket_RecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.addClickListiner(callback);

        refreshListView();
    }

    public void refreshListView() {
        double totalValue = 0;
        ArrayList<CartProducts> allCartProducts = DatabaseQueryHelper.getProducts();
        adapter.addAllItem(allCartProducts);
        for (CartProducts mCartProducts : allCartProducts) {
            int quantity = Integer.parseInt(Logger.EmptyStringNumber(mCartProducts.quantity));
            double price = Double.parseDouble(Logger.EmptyStringNumber(mCartProducts.price));
            double value = price * quantity;
            totalValue = totalValue + value;
        }
        total_amount.setText("Rs " + totalValue);

    }

    public FilterItemCallback callback = new FilterItemCallback() {
        @Override
        public void ClickFilterItemCallback(int type, int position) {
            if (type == 0) {
                CartProducts mCartProducts = adapter.getSuggestMessageList(position);
                DatabaseQueryHelper.DeleteCartProductsByID(mCartProducts.getId());
                refreshListView();
            } else if (type == 5) {
                CartProducts mCartProducts = adapter.getSuggestMessageList(position);
                if (!Helpers.isNetworkAvailable(mContext)) {
                    offLineSearch(mCartProducts.product_id);
                } else {
                    productServerRequest(mCartProducts.product_id);
                }
            } else {
                refreshListView();
            }

        }
    };

    @OnClick({R.id.img_storeselect, R.id.re_bucket_bottom})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_storeselect:
                productordercheckServerRequest();
                break;
            case R.id.re_bucket_bottom:
                productordercheckServerRequest();
                break;

            default:
                break;

        }
    }

    private void productServerRequest(String productId) {

        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product_id", productId);
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "producdetails";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);

                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        JSONObject mJsonArray = mJsonObject.getJSONObject("data");
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        ProductList mProductList = mGson.fromJson(mJsonArray.toString(), ProductList.class);
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                        Bundle extra = new Bundle();
                        extra.putSerializable("objects", mProductList);
                        intent.putExtra("extra", extra);
                        startActivity(intent);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                try {
                    mBusyDialog.dismis();
                    JSONObject mJsonObject = new JSONObject(serverResponse);
                    String message = mJsonObject.getString("message");
                    ToastHelper.showToast(mContext, message);

                } catch (Exception ex) {

                }
            }
        });
    }

    private ArrayList<ProductList> allListsProduct = new ArrayList<>();

    private void offLineSearch(String click_product_id) {
        try {
            allListsProduct.clear();
            boolean objectExists = Reservoir.contains("botiqueServerRequest");
            boolean objectExists1 = Reservoir.contains("offerServerRequest");
            boolean objectExists2 = Reservoir.contains("specialofferServerRequest");
            Logger.debugLog("category", "" + objectExists1);
            Type resultType = new TypeToken<String>() {
            }.getType();
            if (objectExists) {
                String responseServer = Reservoir.get("botiqueServerRequest", resultType);
                loadData(responseServer);
            }
            if (objectExists1) {
                String responseServer1 = Reservoir.get("offerServerRequest", resultType);
                loadData(responseServer1);

            }
            if (objectExists2) {
                String responseServer2 = Reservoir.get("specialofferServerRequest", resultType);
                loadData(responseServer2);
            }

            ArrayList<ProductList> allLists = new ArrayList<>();
            for (ProductList mProductList : allListsProduct) {
                String product_id = mProductList.getId();
                if (click_product_id.equalsIgnoreCase(product_id)) {
                    Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                    Bundle extra = new Bundle();
                    extra.putSerializable("objects", mProductList);
                    intent.putExtra("extra", extra);
                    startActivity(intent);
                }
                break;
            }

        } catch (IOException e) {
        }
    }

    private void loadData(String responseServer) {
        try {
            Logger.debugLog("responseServer", responseServer);
            JSONObject mJsonObject = new JSONObject(responseServer);
            if (mJsonObject.getBoolean("success")) {
                JSONArray mJsonArray = mJsonObject.getJSONArray("data");
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<ProductList> posts = new ArrayList<ProductList>();
                posts = Arrays.asList(mGson.fromJson(mJsonArray.toString(), ProductList[].class));
                ArrayList<ProductList> allLists = new ArrayList<ProductList>(posts);
                allListsProduct.addAll(allLists);
            }


        } catch (Exception e) {

        }
    }

    private void productordercheckServerRequest() {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("order_data", makeJsondataforOrder());
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        String url = AllUrls.BASEURL + "productordercheck";
        //url = AllUrls.BASEURL + "productorder_test";

        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {

                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        Intent intent = new Intent(BucketListActivity.this, OrderPickupActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        HashMap<String, ProductList> allProductList = new HashMap<>();
                        JSONArray mJsonArray = mJsonObject.getJSONArray("data");
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<ProductList> posts = new ArrayList<ProductList>();
                        posts = Arrays.asList(mGson.fromJson(mJsonArray.toString(), ProductList[].class));
                        ArrayList<ProductList> allLists = new ArrayList<ProductList>(posts);
                        for (ProductList mProductList : allLists) {
                            allProductList.put(mProductList.getProduct_code(), mProductList);
                        }
                        if (allProductList.size() > 0) {
                            adapter.addUnavailableproduct(allProductList);
                            Intent intent = new Intent(mContext, PopupviewActivity.class);
                            intent.putExtra("screen_type", 8);
                            intent.putExtra("user_id", "");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, PopupviewActivity.class);
                            intent.putExtra("screen_type", 8);
                            intent.putExtra("user_id", "");
                            startActivity(intent);

                        }

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

    double totalValueForOder = 0;

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

                mJsonArray.put(mObject);

            }

        } catch (Exception ex) {
            Log.e("Exception", ex.getMessage());
        }

        Log.e("mJsonArray", mJsonArray.toString());
        return mJsonArray.toString();

    }


}