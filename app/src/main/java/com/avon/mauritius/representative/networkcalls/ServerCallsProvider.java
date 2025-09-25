package com.avon.mauritius.representative.networkcalls;


import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.avon.mauritius.representative.callbackinterface.ServerResponse;
import com.avon.mauritius.representative.myapplication.Myapplication;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ServerCallsProvider {
    private static final String TAG = ServerCallsProvider.class.getSimpleName();

    public static void volleyGetRequest(@NonNull String url, @NonNull final Map<String, String> headerParams, String TAG, @NonNull final ServerResponse serverResponse) {
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String body = response.toString();
                String statusCode = "200";
                serverResponse.onSuccess(statusCode, body);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                String statusCode = "-1";
                try {
                    Logger.debugLog("error", "" + error.networkResponse);
                    Logger.debugLog("error", "" + error.getMessage());

                    if (error.networkResponse.data != null) {
                        statusCode = String.valueOf(error.networkResponse.statusCode);
                        body = new String(error.networkResponse.data, "UTF-8");
                        if (statusCode.equalsIgnoreCase("490")) {
                            JSONObject mJsonObject = new JSONObject(body);
                            String token = mJsonObject.getString("token");
                            PersistentUser.setUserToken(Myapplication.getContext(), token);
                            HashMap<String, String> allHashMapHeader = new HashMap<>();
                            allHashMapHeader.put("Authorization", "Bearer " + PersistentUser.getUserToken(Myapplication.getContext()));
                            allHashMapHeader.put("Accept", "application/json");
                            volleyGetRequest(url, allHashMapHeader, TAG, serverResponse);

                        } else {
                            serverResponse.onFailed(statusCode, body);
                        }

                    } else {
                        JSONObject mObject = new JSONObject();
                        mObject.put("msg", "Network issue.Please checked your mobile data.");
                        serverResponse.onFailed(statusCode, mObject.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse.onFailed(statusCode, e.getMessage());

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerParams;
            }
        };
        int socketTimeout = 50000;// 50 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);
        Myapplication.getInstance().addToRequestQueue(getRequest, TAG);
    }

    public static void volleyPostRequest(@NonNull String url,  Map<String, String> params, @NonNull final Map<String, String> headerParams, final String TAG, @NonNull final ServerResponse serverResponse) {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String body = response.toString();
                        String statusCode = "200";
                        serverResponse.onSuccess(statusCode, body);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                String statusCode = "-1";
                Logger.warnLog("VolleyError", "message #### " + error.getMessage());
                try {
                    if (error.networkResponse.data != null) {
                        statusCode = String.valueOf(error.networkResponse.statusCode);
                        body = new String(error.networkResponse.data, "UTF-8");
                        serverResponse.onFailed(statusCode, body);

                    } else {
                        JSONObject mObject = new JSONObject();
                        mObject.put("msg", "Network issue.Please checked your mobile data.");
                        serverResponse.onFailed(statusCode, mObject.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse.onFailed(statusCode, body);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerParams;
            }
        };

        Logger.debugLog(TAG, "volleyPostRequest  " + postRequest.getUrl());
        int socketTimeout = 50000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        Myapplication.getInstance().addToRequestQueue(postRequest, TAG);
    }

    public static void volleyPostRequest_new(@NonNull String url, @NonNull final Map<String, String> params, @NonNull final Map<String, String> headerParams, final String TAG, @NonNull final ServerResponse serverResponse) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String body = response.toString();
                String statusCode = "200";
                serverResponse.onSuccess(statusCode, body);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String statusCode = "-1";
                    statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.e("statusCode",statusCode);

                    Log.e("VolleyError", "message #### " + error.getMessage());
                    JSONObject mObject = new JSONObject();
                    mObject.put("msg", "Network issue.Please checked your mobile data.");
                    serverResponse.onFailed(statusCode, mObject.toString());
                } catch (Exception ex) {
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Content-Type", "application/form-data");

                return params;
            }
        };


        int socketTimeout = 50000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        Myapplication.getInstance().addToRequestQueue(postRequest, TAG);
    }


    public static void VolleyMultipartRequest(@NonNull String url, @NonNull final Map<String, String> params, @NonNull final Map<String, String> headerParams, final Map<String, VolleyMultipartRequest.DataPart> ByteData, @NonNull final ServerResponse serverResponse) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                String statusCode = "200";
                serverResponse.onSuccess(statusCode, resultResponse);
                Logger.debugLog(TAG, "response  " + resultResponse);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                String statusCode = "-1";
                try {
                    statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.e("statusCode",statusCode);


                    if (error.networkResponse.data != null) {
                        body = new String(error.networkResponse.data, "UTF-8");
                        serverResponse.onFailed(statusCode, body);

                    } else {
                        JSONObject mObject = new JSONObject();
                        mObject.put("msg", "Network issue.Please checked your mobile data.");
                        serverResponse.onFailed(statusCode, mObject.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return ByteData;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=utf-8");
                if (headerParams != null) {
                    for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }
                params = CookieHelper.getInstance().addSessionCookie(Myapplication.getContext(), params);
                return headerParams;
                //return headerParams;
            }
        };

        int socketTimeout = 50000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(policy);
        Myapplication.getInstance().addToRequestQueue(multipartRequest, TAG);
    }

}
