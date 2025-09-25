package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.core.content.ContextCompat;

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
import io.writeopia.loadingbutton.customViews.CircularProgressButton;

public class ChangePasswordActivity extends BaseOtherActivity {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.edt_current)
    EditText edt_current;
    @BindView(R.id.edt_fp_password)
    EditText edt_fp_password;
    @BindView(R.id.edt_fp_confirmpassword)
    EditText edt_fp_confirmpassword;
    CircularProgressButton fp_btnconfirm_anmiation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_change_password, frameLayout);
        ButterKnife.bind(this);
        initUI();

    }

    private void initUI() {
        fp_btnconfirm_anmiation = (CircularProgressButton) this.findViewById(R.id.fp_btnconfirm_anmiation);
        fp_btnconfirm_anmiation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    public void validation() {
        String currentpassword = edt_current.getText().toString().trim();
        String password = edt_fp_password.getText().toString().trim();
        String confirmpassword = edt_fp_confirmpassword.getText().toString().trim();
        if (currentpassword.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_current_password));
            return;
        } else if (password.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_password));
            return;
        } else if (confirmpassword.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_confirm_password));
            return;
        } else if (!confirmpassword.equalsIgnoreCase(password)) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_confirm_password));
            return;
        } else {
            fp_btnconfirm_anmiation.startAnimation();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", PersistentUser.getUserID(mContext));
            hashMap.put("new_password", password);
            hashMap.put("password", currentpassword);
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
        final String url = AllUrls.BASEURL + "changepassword";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                fp_btnconfirm_anmiation.revertAnimation();
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    fp_btnconfirm_anmiation.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                } else {
                    fp_btnconfirm_anmiation.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                }
                try {
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        finish();
                    } else {
                        String message = mJsonObject.getString("message");
                        ToastHelper.showToast(mContext, message);

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                fp_btnconfirm_anmiation.revertAnimation();
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    fp_btnconfirm_anmiation.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                } else {
                    fp_btnconfirm_anmiation.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                }
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