package com.avon.mauritius.representative.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.database.DatabaseQueryHelper;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.networkcalls.ServerCallsProvider;
import com.avon.mauritius.representative.slidermenu.SlidingMenu;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.avon.mauritius.representative.utils.ToastHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private static final String TAG = BaseActivity.class.getSimpleName();
    public FrameLayout frameLayout;
    private Context mContext;
    private Intent intent = null;

    private RelativeLayout layout_home;
    private RelativeLayout layout_cart;
    private RelativeLayout layout_notification;
    private RelativeLayout layout_message;
    private RelativeLayout layout_profile;

    private int selection = 0;
    private SlidingMenu slidingMenu;
    public LinearLayout bottom_liback;
    private ImageView img_shopping_home;

    public ImageView image_Usermessage;
    public ImageView image_UserNotification;
    public ImageView image_Userblog;
    public ImageView ic_3, ic_5, imagearrowamination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        String storeLang = PersistentUser.getLanguage(mContext);
        Locale locale = new Locale(storeLang);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_base);
        bottomView();
    }

    public void bottomView() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowDrawable(R.drawable.shadow_second);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.8f);
        slidingMenu.setFadeEnabled(false);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.home_side_menu);
        slidingMenu.setSlidingEnabled(true);


        frameLayout = (FrameLayout) this.findViewById(R.id.content_frame);
        layout_home = (RelativeLayout) this.findViewById(R.id.layout_home);
        layout_cart = (RelativeLayout) this.findViewById(R.id.layout_cart);
        layout_notification = (RelativeLayout) this.findViewById(R.id.layout_notifications);
        layout_message = (RelativeLayout) this.findViewById(R.id.layout_message);
        layout_profile = (RelativeLayout) this.findViewById(R.id.layout_profile);


        bottom_liback = (LinearLayout) this.findViewById(R.id.bottom_liback);
        img_shopping_home = (ImageView) this.findViewById(R.id.img_shopping_home);


//        image_Usermessage = (ImageView) this.findViewById(R.id.image_Usermessage);
//        image_UserNotification = (ImageView) this.findViewById(R.id.image_UserNotification);
//        image_Userblog = (ImageView) this.findViewById(R.id.image_Userblog);
//        ic_3 = (ImageView) this.findViewById(R.id.ic_3);
//        ic_5 = (ImageView) this.findViewById(R.id.ic_5);
//        imagearrowamination = (ImageView) this.findViewById(R.id.imagearrowamination);


//        Glide.with(this)
//                .load(R.raw.downarrowanimatedhome)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imagearrowamination);
        // img_menu_home.setImageResource(mContext.getResources().get(R.drawable.avonmenuicon));
        //img_menu_home.setImageResource(R.drawable.avonmenuicon);

        layout_cart.setOnClickListener(listenerForTab);
        layout_notification.setOnClickListener(listenerForTab);
        layout_home.setOnClickListener(listenerForTab);
        layout_profile.setOnClickListener(listenerForTab);
        layout_message.setOnClickListener(listenerForTab);

        img_shopping_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  slidingMenu.toggle();
            }
        });

        findViewById(R.id.img_closeslide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
            }
        });


        findViewById(R.id.li_incentive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
                if (Myapplication.selection != 0) {
//                    intent = new Intent(mContext, BlogActivity.class);
//                    startActivity(intent);
//                    finish();

                }

            }
        });

        findViewById(R.id.li_catalog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
//                intent = new Intent(mContext, BoucherActivity.class);
//                startActivity(intent);
            }
        });
        findViewById(R.id.li_blog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
                if (Myapplication.selection != 0) {
//                    intent = new Intent(mContext, BlogActivity.class);
//                    startActivity(intent);
//                    finish();

                }

            }
        });


        findViewById(R.id.li_historic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent = new Intent(mContext, OrderHistoryActivity.class);
//                startActivity(intent);
            }
        });


        findViewById(R.id.li_politique).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
//                intent = new Intent(mContext, WebViewActivity.class);
//                intent.putExtra("screen_type", 1);
//                startActivity(intent);
            }
        });
        findViewById(R.id.li_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();
//                intent = new Intent(mContext, WebViewActivity.class);
//                intent.putExtra("screen_type", 0);
//                startActivity(intent);
            }
        });


        findViewById(R.id.imageFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (!PersistentUser.getUserNotify(mContext).equalsIgnoreCase("")) {
                        JSONObject mObject3 = new JSONObject(PersistentUser.getUserNotify(mContext));
                        if (mObject3.getBoolean("success")) {
                            JSONObject mObject = mObject3.getJSONObject("data");
                            JSONObject other_service = mObject.getJSONObject("other_service");
                            String facebook_link = Logger.EmptyString(other_service.getString("facebook_link"));
                            if (!facebook_link.equalsIgnoreCase("")) {
                                ConstantFunctions.webIntntCall(facebook_link);
                            }


                        }
                    }
                } catch (Exception ex) {
                    Log.e("Exception", ex.getMessage());
                }
            }
        });
        findViewById(R.id.imageInstragram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!PersistentUser.getUserNotify(mContext).equalsIgnoreCase("")) {
                        JSONObject mObject3 = new JSONObject(PersistentUser.getUserNotify(mContext));
                        if (mObject3.getBoolean("success")) {
                            JSONObject mObject = mObject3.getJSONObject("data");
                            JSONObject other_service = mObject.getJSONObject("other_service");
                            String instragram_link = Logger.EmptyString(other_service.getString("instragram_link"));
                            if (!instragram_link.equalsIgnoreCase("")) {
                                ConstantFunctions.webIntntCall(instragram_link);
                            }

                        }
                    }
                } catch (Exception ex) {
                    Log.e("Exception", ex.getMessage());

                }


            }
        });
        findViewById(R.id.imageYouteb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (!PersistentUser.getUserNotify(mContext).equalsIgnoreCase("")) {
                        JSONObject mObject3 = new JSONObject(PersistentUser.getUserNotify(mContext));
                        if (mObject3.getBoolean("success")) {
                            JSONObject mObject = mObject3.getJSONObject("data");
                            JSONObject other_service = mObject.getJSONObject("other_service");
                            String youtube_link = Logger.EmptyString(other_service.getString("youtube_link"));
                            if (!youtube_link.equalsIgnoreCase("")) {
                                ConstantFunctions.webIntntCall(youtube_link);
                            }


                        }
                    }
                } catch (Exception ex) {
                    Log.e("Exception", ex.getMessage());

                }

            }
        });

        showallItemsAert();
    }


    public View.OnClickListener listenerForTab = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_home:
                    if (selection != 0) {
                        intent = new Intent(mContext, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.layout_cart:
                    if (selection != 1) {
                        intent = new Intent(mContext, BucketListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.layout_notifications:
                    if (selection != 2) {
                        intent = new Intent(mContext, NotificationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.layout_message:
                    if (selection != 3) {
                        intent = new Intent(mContext, MessageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.layout_profile:
                    if (selection != 4) {
                        intent = new Intent(mContext, MyAccountActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;


                default:
                    break;

            }
        }
    };

    public void selectedDeselectedLayut() {

        layout_cart.setOnClickListener(listenerForTab);
        layout_notification.setOnClickListener(listenerForTab);
        layout_home.setOnClickListener(listenerForTab);
        layout_profile.setOnClickListener(listenerForTab);
        layout_message.setOnClickListener(listenerForTab);

        layout_cart.setSelected(false);
        layout_notification.setSelected(false);
        layout_home.setSelected(false);
        layout_profile.setSelected(false);
        layout_message.setSelected(false);

        selection = Myapplication.selection;
        if (selection == 0) {
            layout_home.setSelected(true);
        } else if (selection == 1) {
            layout_cart.setSelected(true);
        } else if (selection == 2) {
            layout_notification.setSelected(true);
        } else if (selection == 3) {
            layout_message.setSelected(true);
        } else if (selection == 4) {
            layout_profile.setSelected(true);
        }


    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        showallItemsAert();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Myapplication.NEW_MESSAGE_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED);
        } else {
            registerReceiver(receiver, filter);
        }

    }


    public void showallItemsAert() {

//
//        LinearLayout layoutCart_count = (LinearLayout) this.findViewById(R.id.layoutCart_count);
//        layoutCart_count.setVisibility(View.GONE);
//        image_Usermessage.setVisibility(View.GONE);
//        image_UserNotification.setVisibility(View.GONE);
//        image_Userblog.setVisibility(View.GONE);
//
//
//        TextView textCart_count = (TextView) this.findViewById(R.id.textCart_count);
//        int sizeCard = DatabaseQueryHelper.getProducts().size();
//        textCart_count.setText("" + sizeCard);
//        if (sizeCard > 0) {
//            layoutCart_count.setVisibility(View.VISIBLE);
//            Glide.with(this)
//                    .load(R.raw.activecarticon)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .into(img_shopping_home);
//        } else {
//            img_shopping_home.setImageResource(R.drawable.ic_shopping);
//        }
//        try {
//
//            if (!PersistentUser.getUserNotify(mContext).equalsIgnoreCase("")) {
//                JSONObject mObject = new JSONObject(PersistentUser.getUserNotify(mContext));
//                if (mObject.getBoolean("success")) {
//                    JSONObject mObjectData = mObject.getJSONObject("data");
//                    int total_notificaion = Integer.parseInt(Logger.EmptyStringNumber(mObjectData.getString("total_notificaion")));
//                    int total_message = Integer.parseInt(Logger.EmptyStringNumber(mObjectData.getString("total_message")));
//                    int total_blog = Integer.parseInt(Logger.EmptyStringNumber(mObjectData.getString("total_blog")));
//
//                    Log.e("total_notificaion", "are" + total_notificaion);
//
//                    if (total_notificaion > 0) {
//                        image_UserNotification.setVisibility(View.VISIBLE);
//                        Glide.with(this)
//                                .load(R.raw.notificationactive)
//                                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                .into(ic_3);
//                    } else {
//                        ic_5.setImageResource(R.drawable.notification_selected);
//
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                            ic_3.setImageDrawable(getResources().getDrawable(R.drawable.btn_tab_notification, getApplicationContext().getTheme()));
////                        } else {
////                            ic_3.setImageDrawable(getResources().getDrawable(R.drawable.btn_tab_notification));
////                        }
//                    }
//                    if (total_message > 0) {
//                        image_Usermessage.setVisibility(View.VISIBLE);
//                        Glide.with(mContext)
//                                .load(R.raw.messageactive)
//                                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                .into(ic_5);
//                    } else {
//                        ic_5.setImageResource(R.drawable.enveloperegular);
//                    }
////                    if (total_blog > 0) {
////                        image_Userblog.setVisibility(View.VISIBLE);
////
////                    }
//                }
//            }
//        } catch (Exception ex) {
//        }

    }

    public void userInforerverRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", PersistentUser.getUserID(mContext));
        HashMap<String, String> allHashMapHeader = new HashMap<>();
        allHashMapHeader.put("appKey", AllUrls.appKey);
        final String url = AllUrls.BASEURL + "user_notify";
        ServerCallsProvider.volleyPostRequest(url, hashMap, allHashMapHeader, TAG, new ServerResponse() {
            @Override
            public void onSuccess(String statusCode, String responseServer) {
                try {
                    JSONObject mJsonObject = new JSONObject(responseServer);
                    if (mJsonObject.getBoolean("success")) {
                        PersistentUser.setUserNotify(mContext, responseServer);
                        showallItemsAert();
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            if (Myapplication.NEW_MESSAGE_ACTION.equals(action)) {
//                Bundle extra = intent.getBundleExtra("extra");
//                String json = extra.getString("objects");
//                try {
//                    JSONObject obj = new JSONObject(json);
//                    String notification_type = obj.getString("notification_type");
//                    String message_id = obj.getString("message_id");
//                    String notification_id = obj.getString("notification_id");
//
//                    if (notification_type.equalsIgnoreCase("0")) {
//                        DatabaseQueryHelper.DeleteAllData();
//                        Intent intent3 = new Intent(mContext, PopupviewActivity.class);
//                        intent3.putExtra("user_id", PersistentUser.getUserID(mContext));
//                        intent3.putExtra("screen_type", 5);
//                        intent3.putExtra("access_type", 1);
//                        startActivity(intent3);
//                    } else if (notification_type.equalsIgnoreCase("1")) {
//                        intent = new Intent(mContext, MessageActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                    } else if (notification_type.equalsIgnoreCase("2")) {
//                        intent = new Intent(mContext, NotificationActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (notification_type.equalsIgnoreCase("5")) {
//                        DatabaseQueryHelper.DeleteAllData();
//                        Intent intent3 = new Intent(mContext, PopupviewActivity.class);
//                        intent3.putExtra("user_id", PersistentUser.getUserID(mContext));
//                        intent3.putExtra("screen_type", 12);
//                        intent3.putExtra("access_type", 1);
//                        startActivity(intent3);
//
//                    } else if (notification_type.equalsIgnoreCase("6")) {
//                        intent = new Intent(mContext, PopupviewActivity.class);
//                        intent.putExtra("user_id", PersistentUser.getUserID(mContext));
//                        intent.putExtra("screen_type", 5);
//                        startActivity(intent);
//
//                    }
//                } catch (Exception ex) {
//                }
//
//            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}

