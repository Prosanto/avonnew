package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.Helpers;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.writeopia.loadingbutton.customViews.CircularProgressButton;

public class FirstTimeUserActivity extends AppCompatActivity {
    private static final String TAG = FirstTimeUserActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.edt_fp_password)
    EditText edt_fp_password;
    @BindView(R.id.edt_fp_confirmpassword)
    EditText edt_fp_confirmpassword;
    @BindView(R.id.li_skip)
    LinearLayout li_skip;
    private String user_id = "";
    private int screen_type = 0;
    CircularProgressButton btn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_first_user);
        ButterKnife.bind(this);
        screen_type = getIntent().getIntExtra("screen_type", 0);
        user_id = getIntent().getStringExtra("user_id");
        initUI();

    }

    private void initUI() {
        li_skip.setVisibility(View.GONE);
        if (screen_type == 0) {
            li_skip.setVisibility(View.VISIBLE);
        }
        btn_id = (CircularProgressButton) this.findViewById(R.id.fp_btnconfirm_anmiation);
        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    @OnClick({R.id.li_skip})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.li_skip:
                if (screen_type == 0) {
                    intent = new Intent(mContext, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    PersistentUser.resetAllData(mContext);
                    PersistentUser.logOut(mContext);
                    finish();
                }
                break;

            default:
                break;

        }
    }

    public void validation() {
        String password = edt_fp_password.getText().toString().trim();
        String confirmpassword = edt_fp_confirmpassword.getText().toString().trim();
        if (password.equalsIgnoreCase("")) {

        } else if (confirmpassword.equalsIgnoreCase("")) {

        } else if (!confirmpassword.equalsIgnoreCase(password)) {

        } else {
            btn_id.startAnimation();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", user_id);
            hashMap.put("password", password);
            resetpasswordServerRequest(hashMap);
        }
    }

    private void resetpasswordServerRequest(HashMap<String, String> hashMap) {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "resetpassword";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                btn_id.revertAnimation();
                try {
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {

                        String password = edt_fp_password.getText().toString().trim();
                        PersistentUser.setUserpassword(mContext, password);
                        Intent intent = new Intent(mContext, PopupviewActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("screen_type", screen_type);
                        intent.putExtra("access_type", 1);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(mContext, PopupviewActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("screen_type", 0);
                        intent.putExtra("access_type", 0);
                        startActivity(intent);

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                btn_id.revertAnimation();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (screen_type == 0) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                PersistentUser.resetAllData(mContext);
                PersistentUser.logOut(mContext);
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}