package com.avon.mauritius.representative.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import org.json.JSONObject;
import java.util.Locale;
public class SplashActivity extends AppCompatActivity {
    //    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private Context mContext;
    private String notification_type = "";
    private String message_id = "";
    private String notification_id = "";
    private ImageView car_image_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        if (storeLang.equalsIgnoreCase("")) {
            String degice_language = Locale.getDefault().getLanguage();
            degice_language = deviceLanuages(degice_language);
            PersistentUser.setLanguage(mContext, degice_language);
        }
        FirebaseApp.initializeApp(this);
        storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_splash_new);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("payload")) {
                try {
                    String jsonString = extras.getString("payload");
                    Log.e("jsonString", jsonString);
                    JSONObject obj = new JSONObject(jsonString);
                    if (obj.has("notification_type")) {
                        notification_type = obj.getString("notification_type");
                    }
                    if (obj.has("message_id")) {
                        message_id = obj.getString("message_id");
                    }
                    if (obj.has("notification_id")) {
                        notification_id = obj.getString("notification_id");
                    }

                } catch (Exception ex) {

                }

            }

        }
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

        car_image_icon = (ImageView) this.findViewById(R.id.car_image_icon);
        Glide.with(mContext)
                .load(R.raw.cartloadingicon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(car_image_icon);
        handler.postDelayed(runnable, 5000);
        PersistentUser.setOrderSorting(mContext, "0");
        PersistentUser.setOrderSystem(mContext, "");

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startNextActivity();
        }
    };

    Intent intent = null;
    private void startNextActivity() {
        if (PersistentUser.isLogged(mContext)) {
//            if (notification_type.contains("1")) {
//                intent = new Intent(this, MessageActivity.class);
//            } else if (notification_type.contains("2")) {
//                intent = new Intent(this, NotificationActivity.class);
//            } else if (notification_type.contains("3")) {
//                intent = new Intent(this, BlogActivity.class);
//            } else if (notification_type.contains("4")) {
//                intent = new Intent(this, BoucherActivity.class);
//            } else if (notification_type.contains("5")) {
//                intent = new Intent(this, HomeActivity.class);
//            } else {
//                intent = new Intent(this, HomeActivity.class);
//            }
            intent.putExtra("notification_type", notification_type);
            intent.putExtra("message_id", message_id);
            intent.putExtra("notification_id", notification_id);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public String deviceLanuages(String deviceLanguage) {
        String setInitialLanuage = "en";
        if (deviceLanguage.equalsIgnoreCase("fr"))
            setInitialLanuage = "fr";
        return setInitialLanuage;
    }
}