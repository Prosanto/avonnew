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
import com.avon.mauritius.representative.adapter.MessageAdapter;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.customview.EndlessRecyclerViewScrollListener;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.model.MessageList;
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

public class MessageActivity extends BaseActivity {
    private static final String TAG = MessageActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView message_RecyclerView;
    MessageAdapter mMessageAdapter;
    private LinearLayout layout_no_message;
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
        getLayoutInflater().inflate(R.layout.activity_message, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 3;
        selectedDeselectedLayut();
        initUI();

    }


    private void initUI() {
        layout_no_message = (LinearLayout) this.findViewById(R.id.layout_no_message);
        message_RecyclerView = (RecyclerView) findViewById(R.id.message_RecyclerView);
        LinearLayoutManager mLinearLayoutManagerRadio = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        message_RecyclerView.setLayoutManager(mLinearLayoutManagerRadio);
        mMessageAdapter = new MessageAdapter(getApplicationContext(), new ArrayList<MessageList>());
        message_RecyclerView.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
        mMessageAdapter.addClickListiner(callback);

        SwipeRefreshLayout refreshLaout = (SwipeRefreshLayout) this.findViewById(R.id.refreshLaout);
        refreshLaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMessageAdapter.deleteItem();
                refreshLaout.setRefreshing(false);
                if (!Helpers.isNetworkAvailable(mContext)) {
                    loadMessage();
                } else {
                    MessageListServerRequest("1");
                }
            }
        });
        message_RecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManagerRadio) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                int laodTotal = Integer.parseInt(AllUrls.PAGE_LIMIT);
                if (page * laodTotal <= totalItemsCount) {
                    MessageListServerRequest("" + (page + 1));
                }
            }
        });
        if (!Helpers.isNetworkAvailable(mContext)) {
            loadMessage();
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
                mMessageAdapter.updateData(position);
                MessageList arrayList = mMessageAdapter.getSuggestMessageList(position);
                Intent intent = new Intent(mContext, MessageNotficationDetailsActivity.class);
                intent.putExtra("title", "" + arrayList.getTitle());
                intent.putExtra("messages", "" + arrayList.getMessages());
                intent.putExtra("date", "" + arrayList.getDate());
                intent.putExtra("read_id", "" + arrayList.getId());
                intent.putExtra("type", "1");
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
        final String url = AllUrls.BASEURL + "messageList";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        if (offset.equalsIgnoreCase("1")) {
                            Reservoir.put("messageServerRequest", responseServer);
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
        MessageList mMessageList = mMessageAdapter.getSuggestMessageList(position);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("message_id", mMessageList.getId());
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "deletemessage";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {

                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        mMessageAdapter.removeAt(position);
                        if (mMessageAdapter.getItemCount() == 0) {
                            layout_no_message.setVisibility(View.VISIBLE);
                        }
                    } else {
                        String message = mJsonObject.getString("message");
                        ToastHelper.showToast(mContext, message);
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

    //===========================================Load Offline Message========================
    public void loadMessage() {
        try {
            boolean objectExists = Reservoir.contains("messageServerRequest");
            Type resultType = new TypeToken<String>() {
            }.getType();
            if (objectExists) {
                String responseServer = Reservoir.get("messageServerRequest", resultType);
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
                List<MessageList> posts = new ArrayList<MessageList>();
                posts = Arrays.asList(mGson.fromJson(mJsonArray.toString(), MessageList[].class));
                ArrayList<MessageList> allLists = new ArrayList<MessageList>(posts);
                mMessageAdapter.addAllItem(allLists);
                if (allLists.size() == 0) {
                    layout_no_message.setVisibility(View.VISIBLE);
                } else {
                    layout_no_message.setVisibility(View.GONE);
                }
            }
            String is_erase = mJsonObject.getString("is_erase");
            if(Helpers.isNetworkAvailable(mContext)){
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