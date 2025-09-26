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
public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type="";
    private String message_id="";
    private String notification_id="";
    private ImageView broshure;
    private ImageView mackup_img;

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
        //bottom_liback.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_main_blue));
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
        broshure=(ImageView) findViewById(R.id.broshure);
        mackup_img=(ImageView) findViewById(R.id.mackup_img);
        broshure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BroshureActivity.class);
                startActivity(intent);
            }
        });
        mackup_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

}