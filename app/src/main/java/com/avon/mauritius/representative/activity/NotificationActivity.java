package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.NotificationAdapter;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.customview.EndlessRecyclerViewScrollListener;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.model.NotificationList;
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

import butterknife.ButterKnife;

public class NotificationActivity extends BaseActivity {
    private static final String TAG = NotificationActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView notification_RecyclerView;
    NotificationAdapter mNotificationAdapter;
    private BusyDialog mBusyDialog;
    private LinearLayout layout_no_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 2;
        selectedDeselectedLayut();
        initUI();


    }

    private void initUI() {
        layout_no_message = (LinearLayout) this.findViewById(R.id.layout_no_message);
        notification_RecyclerView = (RecyclerView) findViewById(R.id.notification_RecyclerView);
        LinearLayoutManager mLinearLayoutManagerRadio = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        notification_RecyclerView.setLayoutManager(mLinearLayoutManagerRadio);
        mNotificationAdapter = new NotificationAdapter(getApplicationContext(), new ArrayList<NotificationList>());
        notification_RecyclerView.setAdapter(mNotificationAdapter);
        mNotificationAdapter.notifyDataSetChanged();
        mNotificationAdapter.addClickListiner(callback);


        SwipeRefreshLayout refreshLaout = (SwipeRefreshLayout) this.findViewById(R.id.refreshLaout);
        refreshLaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNotificationAdapter.deleteItem();
                refreshLaout.setRefreshing(false);
                if (!Helpers.isNetworkAvailable(mContext)) {
                    loadNotification();
                } else {
                    MessageListServerRequest("1");
                }
            }
        });
        notification_RecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManagerRadio) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                int laodTotal = Integer.parseInt(AllUrls.PAGE_LIMIT);
                if (page * laodTotal <= totalItemsCount) {
                    MessageListServerRequest("" + (page + 1));
                }
            }
        });
        if (!Helpers.isNetworkAvailable(mContext)) {
            loadNotification();
        } else {
            MessageListServerRequest("1");
        }

    }


    public FilterItemCallback callback = new FilterItemCallback() {
        @Override
        public void ClickFilterItemCallback(int type, int position) {
            if (type == 0) {
                DeleteMessageServerRequest(position);
            } else {
                mNotificationAdapter.updateData(position);
                NotificationList arrayList = mNotificationAdapter.getSuggestMessageList(position);
                Intent intent = new Intent(mContext, MessageNotficationDetailsActivity.class);
                intent.putExtra("title", "" + arrayList.getTitle());
                intent.putExtra("messages", "" + arrayList.getMessages());
                intent.putExtra("date", "" + arrayList.getDate());
                intent.putExtra("read_id", "" + arrayList.getId());
                intent.putExtra("type", "2");
                startActivity(intent);
            }

        }
    };

    private void MessageListServerRequest(String offset) {
        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("limit", AllUrls.PAGE_LIMIT);
        hashMap.put("offset", offset);

        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "notificationList";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        if (offset.equalsIgnoreCase("1")) {
                            Reservoir.put("notificationServerRequest", responseServer);
                        }
                        loadData(responseServer);
                    } else {
                        layout_no_message.setVisibility(View.VISIBLE);

                    }
                    userInforerverRequest();

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

    private void DeleteMessageServerRequest(int position) {
        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();
        NotificationList mMessageList = mNotificationAdapter.getSuggestMessageList(position);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("notification_id", mMessageList.getId());
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "deletenotification";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {

                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        mNotificationAdapter.removeAt(position);
                        if (mNotificationAdapter.getItemCount() == 0) {
                            layout_no_message.setVisibility(View.VISIBLE);
                        }
                    } else {
                        String message = mJsonObject.getString("message");
                        ToastHelper.showToast(mContext, message);
                    }
                    userInforerverRequest();

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

    //===========================================Load Offline Message========================
    public void loadNotification() {
        try {
            boolean objectExists = Reservoir.contains("notificationServerRequest");
            Type resultType = new TypeToken<String>() {
            }.getType();
            if (objectExists) {
                String responseServer = Reservoir.get("notificationServerRequest", resultType);
                loadData(responseServer);
            } else {
                layout_no_message.setVisibility(View.VISIBLE);
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
                List<NotificationList> posts = new ArrayList<NotificationList>();
                posts = Arrays.asList(mGson.fromJson(mJsonArray.toString(), NotificationList[].class));
                ArrayList<NotificationList> allLists = new ArrayList<NotificationList>(posts);
                mNotificationAdapter.addAllItem(allLists);
                if (allLists.size() == 0) {
                    layout_no_message.setVisibility(View.VISIBLE);
                } else {
                    layout_no_message.setVisibility(View.GONE);
                }
            }
            String is_erase = mJsonObject.getString("is_erase");
            if (Helpers.isNetworkAvailable(mContext)) {
                if (is_erase.equalsIgnoreCase("1")) {
                    DatabaseQueryHelper.DeleteAllData();
                    Intent intent = new Intent(mContext, PopupviewActivity.class);
                    intent.putExtra("user_id", PersistentUser.getUserID(mContext));
                    intent.putExtra("screen_type", 5);
                    intent.putExtra("access_type", 1);
                    startActivity(intent);
                }
            }

        } catch (Exception e) {

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        showallItemsAert();
    }

}