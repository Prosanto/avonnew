package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.SwitchCompat;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.BusyDialog;
import com.avon.mauritius.representative.utils.Helpers;
import com.avon.mauritius.representative.utils.LocaleHelper;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.text_username)
    TextView text_username;
    @BindView(R.id.text_name)
    TextView text_name;
    @BindView(R.id.text_email)
    TextView text_email;
    @BindView(R.id.text_phone)
    TextView text_phone;
    @BindView(R.id.edit_phone_number)
    ImageView edit_phone_number;
    @BindView(R.id.text_address)
    TextView text_address;
    @BindView(R.id.img_addressedit)
    ImageView img_addressedit;

    @BindView(R.id.change_language)
    SwitchCompat change_language;
    @BindView(R.id.change_mode)
    SwitchCompat change_mode;


    private AlertDialog alertDialog;
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
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 4;
        selectedDeselectedLayut();
        intUI();
    }

    private void intUI() {
        text_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForResetConifmration(0);
            }
        });
        text_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForResetConifmration(1);
            }
        });
//        findViewById(R.id.re_changeLanguage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setPopUpWindow(view);
//            }
//        });
        showInformation();
    }

    public void showInformation() {
        try {
            JSONObject userdata = new JSONObject(PersistentUser.getUserDetails(mContext));
            text_username.setText(Logger.EmptyString(userdata.getString("username")));
            text_name.setText(Logger.EmptyString(userdata.getString("name")));
            text_email.setText(Logger.EmptyString(userdata.getString("email")));
            text_phone.setText(Logger.EmptyString(userdata.getString("phone")));
            text_address.setText(Logger.EmptyString(userdata.getString("address")));

        } catch (Exception ex) {
        }
    }

    @OnClick({R.id.re_changepassword, R.id.re_logout,R.id.re_forgorpassword})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.re_changepassword:
                intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.re_logout:
                showDialogForLogout();
                break;
            case R.id.re_forgorpassword:
               // showDialogForLogout();
                break;

            default:
                break;

        }
    }

    public void showDialogForLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_app_logout, null);
        builder.setView(mView);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        TextView yes_btn = (TextView) mView.findViewById(R.id.yes_btn);
        TextView no_botton = (TextView) mView.findViewById(R.id.no_botton);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                PersistentUser.resetAllData(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });
        no_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });


    }


    public void showDialogForResetConifmration(final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_other_popup, null);
        builder.setView(mView);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        TextView headerText = (TextView) mView.findViewById(R.id.headerText);
        EditText edit_phone = (EditText) mView.findViewById(R.id.edit_phone);
        EditText edit_address = (EditText) mView.findViewById(R.id.edit_address);
        LinearLayout layoutyes_btn = (LinearLayout) mView.findViewById(R.id.layoutyes_btn);
        ImageView imagecrossdialog = (ImageView) mView.findViewById(R.id.imagecrossdialog);

        edit_phone.setText(text_phone.getText().toString());
        edit_address.setText(text_address.getText().toString());
        if (type == 0) {
            headerText.setText(getResources().getString(R.string.change_phone));
            edit_phone.setVisibility(View.VISIBLE);
            edit_address.setVisibility(View.GONE);
        } else {
            headerText.setText(getResources().getString(R.string.change_adress));
            edit_phone.setVisibility(View.GONE);
            edit_address.setVisibility(View.VISIBLE);
        }
        layoutyes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                String serverURL = "";
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id", PersistentUser.getUserID(mContext));
                if (type == 0) {
                    String phone = edit_phone.getText().toString().trim();
                    if (phone.equalsIgnoreCase("")) {
                        ToastHelper.showToast(mContext, getResources().getString(R.string.enter_phone));
                        return;

                    } else {
                        serverURL = AllUrls.BASEURL + "changemobile";
                        hashMap.put("phone", phone);
                        loginServerRequest(hashMap, serverURL);
                    }
                } else {
                    String address = edit_address.getText().toString().trim();
                    if (address.equalsIgnoreCase("")) {
                        ToastHelper.showToast(mContext, getResources().getString(R.string.enter_address));
                        return;
                    } else {
                        serverURL = AllUrls.BASEURL + "changeaddress";
                        hashMap.put("address", address);
                        loginServerRequest(hashMap, serverURL);

                    }
                }

            }
        });
        imagecrossdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });

    }

    private void loginServerRequest(HashMap<String, String> hashMap, final String url) {

        if (!Helpers.isNetworkAvailable(mContext)) {
            Helpers.showOkayDialog(mContext, R.string.no_internet_connection);
            return;
        }
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);

        mBusyDialog = new BusyDialog(mContext);
        mBusyDialog.show();

        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    mBusyDialog.dismis();
                    Logger.debugLog("responseServer", responseServer);
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        JSONObject userdata = mJsonObject.getJSONObject("data");
                        PersistentUser.setUserDetails(mContext, userdata.toString());
                        showInformation();

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

    private PopupWindow mypopupWindow;
    private RadioGroup genderRadioGroup;
    private AppCompatRadioButton radioButtonAsc, radioButtonDec;

    private void setPopUpWindow(View mView) {
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_window_languages, null);
        mypopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        genderRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButtonAsc = (AppCompatRadioButton) view.findViewById(R.id.radioButtonAscLanguage);
        radioButtonDec = (AppCompatRadioButton) view.findViewById(R.id.radioButtonDecLanguage);
        radioButtonAsc.setText(getResources().getString(R.string.lanugage_english));
        radioButtonDec.setText(getResources().getString(R.string.lanugage_french));

        if (storeLang.equalsIgnoreCase("en")) {
            radioButtonAsc.setChecked(true);
        } else if (storeLang.equalsIgnoreCase("fr")) {
            radioButtonDec.setChecked(true);
        }
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                Intent intent = null;
                String storeLang="";
                switch (id) {
                    case R.id.radioButtonAscLanguage:
                        mypopupWindow.dismiss();
                        PersistentUser.setLanguage(mContext, "en");
                         storeLang = PersistentUser.getLanguage(mContext);
                        LocaleHelper.setLocale(mContext,storeLang);
                        intent = new Intent(mContext, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.radioButtonDecLanguage:
                        mypopupWindow.dismiss();
                        PersistentUser.setLanguage(mContext, "fr");
                        storeLang = PersistentUser.getLanguage(mContext);
                        LocaleHelper.setLocale(mContext,storeLang);

                        intent = new Intent(mContext, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        break;

                    default:
                        break;
                }
            }
        });
        mypopupWindow.showAsDropDown(mView, 0, 0);

    }
}