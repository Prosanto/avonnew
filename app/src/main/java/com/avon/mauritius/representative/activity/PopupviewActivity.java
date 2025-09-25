package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.tickview.TickView;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupviewActivity extends AppCompatActivity {
    private static final String TAG = PopupviewActivity.class.getSimpleName();
    private Context mContext;

    @BindView(R.id.header_text)
    TextView header_text;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.code_of_text)
    TextView code_of_text;
    private String user_id = "";
    //private int access_type = 0;
    private int screen_type = 0;
    private TickView tickView;
    @BindView(R.id.layut_for_prodct_limi)
    LinearLayout layut_for_prodct_limi;
    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.product_linit)
    TextView product_linit;
    @BindView(R.id.qty_of_product)
    TextView qty_of_product;
    private int productQty = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_reset_complete);
        ButterKnife.bind(this);
        user_id = getIntent().getStringExtra("user_id");
        screen_type = getIntent().getIntExtra("screen_type", 0);
        productQty = getIntent().getIntExtra("productQty", 0);
        initUI();

    }

    private void initUI() {
        qty_of_product.setVisibility(View.GONE);
        code_of_text.setVisibility(View.GONE);
        tickView = (TickView) findViewById(R.id.tick_view);
        tickView.setChecked(true);
        tickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screen_type == 0) {
                    finish();
                } else if (screen_type == 1) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else if (screen_type == 2) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else if (screen_type == 3) {
                    finish();
                } else if (screen_type == 4) {
                    finish();
                } else if (screen_type == 5) {
//                    Intent intent = new Intent(mContext, BucketListActivity.class);
//                    startActivity(intent);
//                    finish();
                } else if (screen_type == 6) {
                    finish();
                } else if (screen_type == 7) {
                    finish();
                } else if (screen_type == 8) {
                    finish();
                } else if (screen_type == 9) {
                    finish();
                } else {
                    finish();
                }


            }
        });

        if (screen_type == 0) {
            header_text.setText(getResources().getString(R.string.error));
            title_text.setText(getResources().getString(R.string.error_message));
        } else if (screen_type == 1) {
            header_text.setText(getResources().getString(R.string.thank_you));
            title_text.setText(getResources().getString(R.string.thank_you_message));
        } else if (screen_type == 2) {
            code_of_text.setVisibility(View.VISIBLE);
            code_of_text.setText(PersistentUser.getUserpassword(mContext));
            header_text.setText(getResources().getString(R.string.reset_password));
            title_text.setText(getResources().getString(R.string.reset_password_to));
        } else if (screen_type == 3) {
            header_text.setText(getResources().getString(R.string.cart_error));
            title_text.setText(getResources().getString(R.string.cart_error_message));
        } else if (screen_type == 4) {
            header_text.setText(getResources().getString(R.string.error));
            title_text.setText(getResources().getString(R.string.order_message));
        } else if (screen_type == 5) {
            header_text.setText(getResources().getString(R.string.Erasemassage));
            title_text.setText(getResources().getString(R.string.Erasemassage_message));
            userInforerverRequest();
        } else if (screen_type == 6) {
            layut_for_prodct_limi.setVisibility(View.VISIBLE);
            product_name.setText(getIntent().getStringExtra("product_name"));
            product_linit.setText(getIntent().getStringExtra("product_linit"));
            header_text.setText(getResources().getString(R.string.error_limit_empty));
            title_text.setText(getResources().getString(R.string.error_limit_empty_message));
            userInforerverRequest();
        } else if (screen_type == 7) {
            header_text.setText(getResources().getString(R.string.error));
            title_text.setText(getResources().getString(R.string.error_stock_message));
        } else if (screen_type == 8) {
            header_text.setText(getResources().getString(R.string.error));
            title_text.setText(getResources().getString(R.string.unavailableproducts));
        } else if (screen_type == 9) {
            qty_of_product.setVisibility(View.GONE);
            //qty_of_product.setText("" + productQty);
            header_text.setText(getResources().getString(R.string.error));
            title_text.setText(getResources().getString(R.string.out_of_product_2));
        } else if (screen_type == 12) {
            DatabaseQueryHelper.DeleteAllData();
            header_text.setText(getResources().getString(R.string.Newcampaign));
            title_text.setText(getResources().getString(R.string.Newcampaign_message));
            userInforerverRequest();
        }
    }

    @OnClick({R.id.img_complete_reset})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_complete_reset:
                if (screen_type == 5) {
//                    intent = new Intent(mContext, BucketListActivity.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    finish();
                }

                break;

            default:
                break;

        }
    }

    public void userInforerverRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        hashMap.put("read_type", "4");
        hashMap.put("read_id", "0");
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "readdataList";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    Log.e("responseServer", responseServer);
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