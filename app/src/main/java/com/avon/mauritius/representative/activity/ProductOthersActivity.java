package com.avon.mauritius.representative.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.adapter.ProductAdapter;
import com.avon.mauritius.representative.adapter.SortAdapter;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.BusyDialog;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class ProductOthersActivity extends BaseActivity {
    private static final String TAG = ProductOthersActivity.class.getSimpleName();
    private Context mContext;
    private String notification_type="";
    private String message_id="";
    private String notification_id="";


    private ListView product_list;
    private LinearLayout li_sortby;
    private LinearLayout li_subcatby;

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
        getLayoutInflater().inflate(R.layout.activity_product, frameLayout);
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


        // Dummy data
        List<String> productData = Arrays.asList("Brochure c4/25", "Flyer C4/25", "Campaign Letter C4/25");

// Set adapters
        product_list = findViewById(R.id.product_list);
        ProductAdapter adapter1 = new ProductAdapter(this, productData);
        product_list.setAdapter(adapter1);

        li_sortby=(LinearLayout) findViewById(R.id.li_sortby);
        li_subcatby=(LinearLayout) findViewById(R.id.li_subcatby);
        li_subcatby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });
        li_sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });

    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_sorting, null);
        builder.setView(mView);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.END; // top-right
            wlp.x = (int) (20 * getResources().getDisplayMetrics().density); // 20dp horizontal offset
            wlp.y = (int) (100 * getResources().getDisplayMetrics().density); // 20dp vertical offset
            window.setAttributes(wlp);
        }
        ListView listView = mView.findViewById(R.id.dialog_listview);
        List<String> data = Arrays.asList("Option 1", "Option 2", "Option 3", "Option 4");
        SortAdapter adapter = new SortAdapter(this, data);
        listView.setAdapter(adapter);



    }



}