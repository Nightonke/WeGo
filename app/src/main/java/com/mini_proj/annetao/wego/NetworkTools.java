package com.mini_proj.annetao.wego;


import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by bran on 2016/7/7.
 */
public class NetworkTools {
    public static String ServerAddr;
    public static String URL_USER = "/user";
    public static String URL_ACTIVITY = "/activity";
    public static String URL_ACTIVITY_TAG = "/activity_tag";
    public static String URL_USER_TAG = "/user_tag";
    public static String URL_ATTENDENCY = "/attendency";
    public static String URL_ACTIVITY_COMMENT = "/activity_comment";
    public static String URL_NOTICE = "/notice";
    private static NetworkTools networkTools = null;
    private Handler mHandler;
    private String UserName = "";

    public NetworkTools(String serveraddr, Handler handler) {
        setServerAddr(serveraddr);
        mHandler = handler;
        networkTools = this;
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

    public void doLogin(String user, String pass, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put(user, pass);
        doRequest(URL_USER + "/login", map, buildStringCallback(success, failed));
    }

    //Tag
    public void addUserTag(String user, String tagname, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", tagname);
        doRequest(URL_USER_TAG + "/adduserTag", map, buildStringCallback(success, failed));
        UserName = user;
    }
    //Tag

    public void deleteUserTag(String tagname, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("password", tagname);
        doRequest(URL_USER_TAG + "/delusrTag", map, buildStringCallback(success, failed));
    }

    public void queryUserTag(String tagname, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        doRequest(URL_USER_TAG + "/query_usrTag", map, buildStringCallback(success, failed));
    }

    //attend
    public void addUserAttend(String tagname, String activity_id, String created_day, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("activity_id", activity_id);
        map.put("created_day", created_day);
        doRequest(URL_ATTENDENCY + "/addusrActi", map, buildStringCallback(success, failed));
    }

    public void deleteUserAttend(String tagname, String activity_id, String created_day, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("activity_id", activity_id);
        map.put("created_day", created_day);
        doRequest(URL_ATTENDENCY + "/delusrActi", map, buildStringCallback(success, failed));
    }

    public void queryWhoAttend(String activity_id, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", activity_id);
        doRequest(URL_ATTENDENCY + "/query_usrforActi", map, buildStringCallback(success, failed));
    }

    public void queryIsAttend(String activity_id, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("activity_id", activity_id);
        doRequest(URL_ATTENDENCY + "/query_actiforusr", map, buildStringCallback(success, failed));
    }

    public void queryAllMyAttend(final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("type", "all");
        doRequest(URL_ATTENDENCY + "/query_allforusr", map, buildStringCallback(success, failed));
    }

    public void queryAllMyAttendWithTime(String time_lower_bound, String time_upper_bound, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("time_lower_bound", time_lower_bound);
        map.put("time_upper_bound", time_upper_bound);
        doRequest(URL_ATTENDENCY + "/query_allforusr", map, buildStringCallback(success, failed));
    }
    //attend

    public void queryMyAttendWithNoFinish(final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("type", "waiting");
        doRequest(URL_ATTENDENCY + "/query_actibeforeend", map, buildStringCallback(success, failed));
    }
    //activity_comment

    public void queryMyAttendWithNoFinish(String activity_id, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", activity_id);
        doRequest(URL_ATTENDENCY + "/query_comment", map, buildStringCallback(success, failed));
    }

    //activity_comment
    public void addcomment(String activity_id, String comment, String grade, String time, final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        map.put("activity_id", activity_id);
        map.put("comment", comment);
        map.put("grade", grade);
        map.put("time", time);
        doRequest(URL_ACTIVITY_COMMENT + "/query_actibeforeend", map, buildStringCallback(success, failed));
    }

    //notice
    public void queryMyNotice(final Runnable success, final Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("username", UserName);
        doRequest(URL_NOTICE + "/query_notice", map, buildStringCallback(success, failed));
    }

    public Callback buildStringCallback(final Runnable success, final Runnable failed) {
        return new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("WeGo", e.toString());
                mHandler.post(failed);
            }

            @Override
            public void onResponse(String response, int id) {
                mHandler.post(success);
            }
        };
    }

    public void doRequest(String url, Map<String, String> paramsmap, Callback callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        for (Map.Entry<String, String> param : paramsmap.entrySet()) {
            builder = builder.addParams(param.getKey(), param.getValue());
        }
        builder.build().execute(callback);
    }

    public void doRequest(String url, Map<String, String> paramsmap, Runnable success, Runnable fail) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        for (Map.Entry<String, String> param : paramsmap.entrySet()) {
            builder = builder.addParams(param.getKey(), param.getValue());
        }
        builder.build().execute(buildStringCallback(success, fail));
    }

}
