package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.DateUtility;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageNotficationDetailsActivity extends BaseOtherActivity {
    private static final String TAG = MessageNotficationDetailsActivity.class.getSimpleName();
    private Context mContext;
    private String titleString = "";
    private String longDescString = "";
    private String date = "";
    private String type = "";
    private String read_id = "";

    @BindView(R.id.txt_title_bgdetail)
    TextView txt_title_bgdetail;
    @BindView(R.id.txt_longdesc_bgdetail)
    TextView txt_longdesc_bgdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_message_notification_details, frameLayout);
        ButterKnife.bind(this);
        read_id = getIntent().getStringExtra("read_id");
        titleString = getIntent().getStringExtra("title");
        longDescString = getIntent().getStringExtra("messages");
        date = getIntent().getStringExtra("date");
        type = getIntent().getStringExtra("type");

        initUI();

    }

    private void initUI() {
        if (type.equalsIgnoreCase("1")) {
            String textMessage = DateUtility.getFormattedDate(date);
            txt_title_bgdetail.setText(textMessage);
            txt_longdesc_bgdetail.setText(longDescString);
        } else {

            txt_title_bgdetail.setText(titleString);
            txt_longdesc_bgdetail.setText(longDescString);
        }
        txt_longdesc_bgdetail.setMovementMethod(LinkMovementMethod.getInstance());

        userInforerverRequest();
    }

    public void userInforerverRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("read_type", type);
        hashMap.put("read_id", read_id);
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "readdataList";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    Log.e("responseServer",responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        PersistentUser.setUserNotify(mContext, responseServer);


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                try {
                    Logger.debugLog("onFailed", serverResponse);
                    JSONObject mJsonObject = new JSONObject(serverResponse);
                    String message = mJsonObject.getString("message");
                    ToastHelper.showToast(mContext, message);
                } catch (Exception ex) {

                }
            }
        });
    }


}