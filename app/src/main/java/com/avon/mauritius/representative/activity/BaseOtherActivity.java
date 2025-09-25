package com.avon.mauritius.representative.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;
import org.json.JSONObject;
import java.util.Locale;

public class BaseOtherActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private static final String TAG = BaseOtherActivity.class.getSimpleName();
    public FrameLayout frameLayout;
    private Context mContext;
    private Intent intent = null;
    private LinearLayout imageSider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_base_other);
        bottomView();
    }

    public void bottomView() {
        frameLayout = (FrameLayout) this.findViewById(R.id.content_frame);
        imageSider = (LinearLayout) this.findViewById(R.id.imageSider);
        imageSider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Myapplication.NEW_MESSAGE_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED);
        } else {
            registerReceiver(receiver, filter);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Myapplication.NEW_MESSAGE_ACTION.equals(action)) {
                Bundle extra = intent.getBundleExtra("extra");
                String json = extra.getString("objects");
                try {
                    JSONObject obj = new JSONObject(json);
                    String notification_type = obj.getString("notification_type");
                    String message_id = obj.getString("message_id");
                    String notification_id = obj.getString("notification_id");
                    if (notification_type.equalsIgnoreCase("0")) {
                        DatabaseQueryHelper.DeleteAllData();
                        Intent intent3 = new Intent(mContext, PopupviewActivity.class);
                        intent3.putExtra("user_id", PersistentUser.getUserID(mContext));
                        intent3.putExtra("screen_type", 5);
                        intent3.putExtra("access_type", 1);
                        startActivity(intent3);
                    }
                } catch (Exception ex) {
                }

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}

