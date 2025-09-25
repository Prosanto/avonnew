package com.avon.mauritius.representative.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersistentUser {

    //common data
    private static final String PREFS_FILE_NAME = "avonPersistentUser";
    private static final String USERDetails = "user_details";
    private static final String USERID = "userid";
    private static final String USERTOKEN = "user_token";
    private static final String USERPASSWORD = "userpassword";
    private static final String PROFILEIMAGEPATH = "PROFILEIMAGEPATH";
    private static final String PUSHTOKEN = "PushToken";
    private static final String USERTYPE = "USERTYPE";
    private static final String LANGUAGE = "language";
    private static final String OrderSorting = "OrderSorting";


    public static String getallProduct(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString("allProduct", "0");
    }

    public static void setallProduct(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString("allProduct", data);
        editor.commit();
    }

    public static String getofferproduct(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString("offerproduct", "0");
    }

    public static void setofferproduct(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString("offerproduct", data);
        editor.commit();
    }

    public static String getflyeroffer(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString("flyeroffer", "0");
    }

    public static void setflyeroffer(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString("flyeroffer", data);
        editor.commit();
    }


    public static String getLanguage(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.LANGUAGE, "");
    }

    public static void setLanguage(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.LANGUAGE, data);
        editor.commit();
    }


    public static String getUserNotify(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString("UserNotify", "");
    }

    public static void setUserNotify(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString("UserNotify", data);
        editor.commit();
    }

    //0==user ,1==company
    public static String getOrderSystem(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.USERTYPE, "");
    }

    public static void setOrderSystem(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.USERTYPE, data);
        editor.commit();
    }

    public static String getOrderSorting(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.OrderSorting, "0");
    }

    public static void setOrderSorting(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.OrderSorting, data);
        editor.commit();
    }


    public static String getPushToken(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.PUSHTOKEN, "");
    }

    public static void setPushToken(final Context ctx, final String data) {
        /**/
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.PUSHTOKEN, data);
        editor.commit();
    }


    public static String getImagePath(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.PROFILEIMAGEPATH, "");
    }

    public static void setImagePath(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.PROFILEIMAGEPATH, data);
        editor.commit();
    }

    public static String getUserToken(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.USERTOKEN, "");
    }

    public static void setUserToken(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.USERTOKEN, data);
        editor.commit();
    }

    public static String getUserDetails(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.USERDetails, "");
    }

    public static void setUserDetails(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.USERDetails, data);
        editor.commit();
    }

    public static String getUserpassword(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(PersistentUser.USERPASSWORD, "");
    }

    public static void setUserpassword(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.USERPASSWORD, data);
        editor.commit();
    }


    public static String getUserID(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE).getString(USERID, "");
    }

    public static void setUserID(final Context ctx, final String id) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(USERID, id);
        editor.commit();
    }

    public static void logOut(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("LOGIN", false).commit();

    }

    public static void setLogin(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("LOGIN", true).commit();
    }

    public static boolean isLogged(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("LOGIN", false);
    }

    public static void setFirstTime(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("FirstTime", true).commit();
    }

    public static boolean isFirstTime(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("FirstTime", false);
    }


    public static void resetAllData(Context c) {
        setUserID(c, "");
        setUserDetails(c, "");
        logOut(c);

    }

}
