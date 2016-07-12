package com.mini_proj.annetao.wego;


import android.os.Handler;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by bran on 2016/7/7.
 */
public class NetworkTools {
    public static String RESULT_SUCCESS = "200";
    public static String RESULT_FAILED = "400";
    public static String RESULT_PERMISSION_DENY = "401";
    public static String RESULT_SERVER_FAILED = "501";
    public static String RESULT_USER_LOGIN_REQUESTED = "402";
    public static String RESULT_TIMESTAMP_ERROR = "8000";
    public static String ACTION_QUERY = "1";
    public static String ACTION_REMOVE = "2";
    public static String ACTION_INSERT = "3";
    public static String ACTION_UPDATE = "4";
    public static String ServerAddr="http://119.29.235.26:33000";
    public static String URL_USER = "/user";
    public static String URL_EXERCISE = "/exercise";
    public static String URL_TAG = "/tag";
    public static String URL_EXERCISE_TAG = "/exercise_tag";
    public static String URL_USER_TAG = "/user_tag";
    public static String URL_ATTENDENCY = "/attendency";
    public static String URL_EXERCISE_COMMENT = "/activity_comment";
    public static String URL_NOTICE = "/user_notice";
    public static Map<String, String> paramsMap=new HashMap<>();
    public static Handler mHandler;
    private static NetworkTools networkTools = null;
    private static String UserName = "";

    private NetworkTools(String serveraddr, Handler handler) {
        setServerAddr(serveraddr);
        mHandler = handler;
        networkTools = this;
    }

    public static void setUserName(String name) {
        UserName = name;
        paramsMap.put("user_id", UserName);
    }

    public static NetworkTools getNetworkTools() {
        if (networkTools != null)
            return networkTools;
        return null;
    }

    public static String getServerAddr() {
        return ServerAddr;
    }

    public static void setServerAddr(String mServerAddr) {
        NetworkTools.ServerAddr = mServerAddr;
    }

    public static void doRequest(String url, Map<String, String> paramsmap, Callback callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(getServerAddr()+url);
        Log.d("Wego",  "URL: " + getServerAddr()+url);
        for (Map.Entry<String, String> param : paramsmap.entrySet()) {
            builder = builder.addParams(param.getKey(), param.getValue());
        }
        RequestCall build = builder.build();
        //Log.d("WeGo",build.getRequest().toString());
        build.execute(callback);
    }

    //Tag
    public void queryAllTag(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(paramsMap);
        doRequest(URL_TAG + "/query_all_tag", null, callback);
    }

    public void queryWhoAttend(String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(paramsMap);
        map.put("activity_id", exercise_id);
        doRequest(URL_ATTENDENCY + "/query_usrforActi", map, callback);
    }

    public void doRequest(String url, Map<String, String> paramsmap, final Runnable success, final Runnable fail) {
        PostFormBuilder builder = OkHttpUtils.post().url(getServerAddr()+url);
        if (paramsmap != null) {
            for (Map.Entry<String, String> param : paramsmap.entrySet()) {
                builder = builder.addParams(param.getKey(), param.getValue());
            }
        }
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("WeGo", e.toString());
                mHandler.post(fail);
            }

            @Override
            public void onResponse(String response, int id) {
                mHandler.post(success);
            }
        });
    }

}