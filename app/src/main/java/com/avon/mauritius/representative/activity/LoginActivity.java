package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.BusyDialog;
import com.avon.mauritius.representative.utils.Helpers;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.writeopia.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private RadioGroup login_radiogroup;
    private RadioButton rb_login, rb_forgot_password;
    Typeface font_regular, font_bold;
    private Context mContext;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_pass_forgotpass)
    EditText edt_pass_forgotpass;
    @BindView(R.id.re_andsign)
    RelativeLayout re_andsign;
    LinearLayout li_login;
    private BusyDialog mBusyDialog;
    private CircularProgressButton btn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        PersistentUser.setPushToken(mContext, token);
                    }
                });

        initUI();

    }

    private void initUI() {
        font_regular = Typeface.createFromAsset(getAssets(), "fonts/avenir_nextlt_pro_regular.otf");
        font_bold = Typeface.createFromAsset(getAssets(), "fonts/avenir_nextlt_pro_bold.otf");
        rb_login = (RadioButton) findViewById(R.id.rb_login);
        rb_forgot_password = (RadioButton) findViewById(R.id.rb_forgot_password);
        login_radiogroup = (RadioGroup) findViewById(R.id.login_radiogroup);
        btn_id = (CircularProgressButton) findViewById(R.id.btn_id);

        login_radiogroup.setOnCheckedChangeListener(this);
        rb_login.setChecked(true);

        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (re_andsign.getVisibility() == View.VISIBLE) {
                    validationForgotPassword();
                } else {
                    validation();
                }
            }
        });
        setupLogin();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.rb_login:
                rb_login.setTypeface(font_bold);
                rb_forgot_password.setTypeface(font_regular);
                setupLogin();

                break;
            case R.id.rb_forgot_password:
                rb_forgot_password.setTypeface(font_bold);
                rb_login.setTypeface(font_regular);
                setupForgotPassword();
                break;
        }
    }

    private void setupForgotPassword() {
        edt_name.setHint(getResources().getString(R.string.username_hint));
        edt_pass_forgotpass.setHint(getResources().getString(R.string.mobile_hint));
        btn_id.setText(getResources().getString(R.string.reset_btn_btn));

        re_andsign.setVisibility(View.VISIBLE);
        edt_name.setText("");
        edt_pass_forgotpass.setText("");
        edt_pass_forgotpass.setInputType(InputType.TYPE_CLASS_PHONE);

    }

    private void setupLogin() {
        edt_name.setHint(getResources().getString(R.string.username_hint));
        edt_pass_forgotpass.setHint(getResources().getString(R.string.forgot_password_hint));

        btn_id.setText(getResources().getString(R.string.login_btn_btn));
        re_andsign.setVisibility(View.GONE);
        edt_name.setText("");
        edt_pass_forgotpass.setText("");
        edt_pass_forgotpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


    }

    public void validation() {
        String username = edt_name.getText().toString().trim();
        String password = edt_pass_forgotpass.getText().toString().trim();
        if (username.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_username));
            return;

        } else if (password.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_password));
            return;

        } else {
            btn_id.startAnimation();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username);
            hashMap.put("password", password);
            hashMap.put("user_type", "0");
            hashMap.put("push_token", PersistentUser.getPushToken(mContext));
            loginServerRequest(hashMap);
        }
    }

    public void validationForgotPassword() {
        String username = edt_name.getText().toString().trim();
        String password = edt_pass_forgotpass.getText().toString().trim();
        if (username.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_username));
            return;

        } else if (password.equalsIgnoreCase("")) {
            ToastHelper.showToast(mContext, getResources().getString(R.string.enter_phone));
            return;

        } else {
            btn_id.startAnimation();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username);
            hashMap.put("phone", password);
            forgotPasswordServerRequest(hashMap);
        }
    }

    private void loginServerRequest(HashMap<String, String> hashMap) {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "login";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                btn_id.revertAnimation();
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btn_id.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                } else {
                    btn_id.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                }
                try {
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        JSONObject userdata = mJsonObject.getJSONObject("data");
                        String id = userdata.getString("id");
                        PersistentUser.setUserID(mContext, id);
                        PersistentUser.setUserDetails(mContext, userdata.toString());
                        PersistentUser.setLogin(mContext);
                        int is_default_password = Integer.parseInt(Logger.EmptyStringNumber(userdata.getString("is_default_password")));

                        int totalpageall = 0;
                        int totalpageoffer = 0;
                        int totalpageflyer = 0;

                        String all = mJsonObject.getString("all");
                        String offer = mJsonObject.getString("offer");
                        String flyer = mJsonObject.getString("flyer");
                        int productsCountall = Integer.parseInt(Logger.EmptyStringNumber(all));
                        if (productsCountall % AllUrls.PAGE_LIMIT_CAL == 0) {
                            totalpageall = (productsCountall / AllUrls.PAGE_LIMIT_CAL) + 0;
                        } else {
                            totalpageall = (productsCountall / AllUrls.PAGE_LIMIT_CAL) + 1;
                        }

                        int productsCountoffer = Integer.parseInt(Logger.EmptyStringNumber(offer));
                        if (productsCountoffer % AllUrls.PAGE_LIMIT_CAL == 0) {
                            totalpageoffer = (productsCountoffer / AllUrls.PAGE_LIMIT_CAL) + 0;
                        } else {
                            totalpageoffer = (productsCountoffer / AllUrls.PAGE_LIMIT_CAL) + 1;
                        }

                        int productsCountflyer = Integer.parseInt(Logger.EmptyStringNumber(flyer));
                        if (productsCountflyer % AllUrls.PAGE_LIMIT_CAL == 0) {
                            totalpageflyer = (productsCountflyer / AllUrls.PAGE_LIMIT_CAL) + 0;
                        } else {
                            totalpageflyer = (productsCountflyer / AllUrls.PAGE_LIMIT_CAL) + 1;
                        }

                        PersistentUser.setallProduct(mContext, "" + totalpageall);
                        PersistentUser.setofferproduct(mContext, "" + totalpageoffer);
                        PersistentUser.setflyeroffer(mContext, "" + totalpageflyer);

                        if (PersistentUser.isFirstTime(mContext) && is_default_password == 1) {
                            Intent intent = new Intent(mContext, FirstTimeUserActivity.class);
                            intent.putExtra("screen_type", 1);
                            intent.putExtra("user_id", id);
                            startActivity(intent);
                        } else if (!PersistentUser.isFirstTime(mContext) && is_default_password == 1) {
                            Intent intent = new Intent(mContext, FirstTimeUserActivity.class);
                            intent.putExtra("screen_type", 1);
                            intent.putExtra("user_id", id);
                            startActivity(intent);
                        } else if (PersistentUser.isFirstTime(mContext) && is_default_password != 1) {
                            Intent intent = new Intent(mContext, FirstTimeUserActivity.class);
                            intent.putExtra("screen_type", 0);
                            intent.putExtra("user_id", id);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(mContext, PopupviewActivity.class);
                        intent.putExtra("screen_type", 0);
                        intent.putExtra("user_id", "");
                        startActivity(intent);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(String statusCode, String serverResponse) {
                btn_id.recoverInitialState();
                try {
                    JSONObject mJsonObject = new JSONObject(serverResponse);
                    String message = mJsonObject.getString("message");
                    ToastHelper.showToast(mContext, message);

                } catch (Exception ex) {

                }
            }
        });
    }


    private void forgotPasswordServerRequest(HashMap<String, String> hashMap) {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "forgotpassword";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                btn_id.revertAnimation();
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btn_id.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                } else {
                    btn_id.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_login));
                }
                try {
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        JSONObject userdata = mJsonObject.getJSONObject("data");
                        String user_id = userdata.getString("id");
                        Intent intent = new Intent(mContext, FirstTimeUserActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("screen_type", 2);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(mContext, PopupviewActivity.class);
                        intent.putExtra("screen_type", 0);
                        intent.putExtra("user_id", "");
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

}