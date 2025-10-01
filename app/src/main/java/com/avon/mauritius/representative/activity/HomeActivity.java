package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;
import java.util.Locale;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type="";
    private String message_id="";
    private String notification_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        ButterKnife.bind(this);
        Myapplication.selection = 0;
        selectedDeselectedLayut();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("notification_type")) {
                notification_type = extras.getString("notification_type");
            }
            if (extras.containsKey("message_id")) {
                message_id = extras.getString("message_id");
            }
            if (extras.containsKey("notification_id")) {
                notification_id = extras.getString("notification_id");
            }
        }
        if (notification_type.equalsIgnoreCase("6")) {
            Intent intent = new Intent(mContext, PopupviewActivity.class);
            intent.putExtra("user_id", PersistentUser.getUserID(mContext));
            intent.putExtra("screen_type", 5);
            startActivity(intent);
        }
        initUI();

    }
    private void initUI() {

    }

    @OnClick({R.id.layout_broshure, R.id.layout_accessories, R.id.mackup_img})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layout_broshure:
                intent = new Intent(mContext, BroshureActivity.class);
                intent.putExtra("product_type", 1);
                startActivity(intent);
                break;
            case R.id.layout_outof_brochure:
                intent = new Intent(mContext, ProductOthersActivity.class);
                intent.putExtra("product_type", 2);
                startActivity(intent);
                break;
            case R.id.layout_accessories:
                intent = new Intent(mContext, ProductOthersActivity.class);
                intent.putExtra("product_type", 3);
                startActivity(intent);
                break;

            case R.id.mackup_img:
                intent = new Intent(mContext, ProductActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

}