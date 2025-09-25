package com.avon.mauritius.representative.pushnotification;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.activity.SplashActivity;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage);
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        PersistentUser.setPushToken(getApplicationContext(), token);
        sendRegistrationToServer(token);
    }

    private void sendNotification(RemoteMessage messageBody) {
        Intent intent;

        try {
            String channelId = "Avon_channel";

            JSONObject obj = new JSONObject(messageBody.getData().get("payload"));
            Log.e("obj", obj.toString());
            String notification_type = obj.getString("notification_type");
            String message_id = obj.getString("message_id");
            String notification_id = obj.getString("notification_id");

            intent = new Intent(Myapplication.NEW_MESSAGE_ACTION);
            Bundle extra = new Bundle();
            extra.putSerializable("objects", obj.toString());
            intent.putExtra("extra", extra);
            sendBroadcast(intent);

            intent = new Intent(this, SplashActivity.class);
            intent.putExtra("payload", obj.toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =

                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle(messageBody.getNotification().getTitle())
                            .setContentText(messageBody.getNotification().getBody())
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                            .setSound(defaultSoundUri)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setLightColor(Color.GRAY);
                channel.enableLights(true);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                channel.setShowBadge(true);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }

            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


        } catch (JSONException ex) {
            Log.e("JSONException", ex.getMessage().toString());
        }


    }


    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}