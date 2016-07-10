package com.mini_proj.annetao.wego;


import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

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
    public static String URL_EXERCISE = "/exercise";
    public static String URL_TAG = "/tag";
    public static String URL_EXERCISE_TAG = "/exercise_tag";
    public static String URL_USER_TAG = "/user_tag";
    public static String URL_ATTENDENCY = "/attendency";
    public static String URL_EXERCISE_COMMENT = "/activity_comment";
    public static String URL_NOTICE = "/notice";
    private static NetworkTools networkTools = null;
    private static String UserName = "";
    private Handler mHandler;

    public NetworkTools(String serveraddr, Handler handler) {
        setServerAddr(serveraddr);
        mHandler = handler;
        networkTools = this;
    }

    public static void setUserName(String name) {
        UserName = name;
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

    public void doLogin(String user, String pass, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put(user, pass);
        doRequest(URL_USER + "/login", map, callback);
    }

    //Tag
    public void addUserTag(String tagid, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("tag_id", tagid);
        map.put("user_id", UserName);
        doRequest(URL_USER_TAG + "/add_user_tag", map, callback);
    }

    public void addUserNewTag(String tagname, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("name", tagname);
        map.put("user_id", UserName);
        doRequest(URL_USER_TAG + "/add_user_new_tag", map, callback);
    }

    public void queryAllTag(Callback callback) {
        Map<String, String> map = new HashMap<>();
        doRequest(URL_TAG + "/query_all_tag", null, callback);
    }
    //Tag

    public void deleteUserTag(String tagid, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("tag_id", tagid);
        map.put("user_id", UserName);
        doRequest(URL_USER_TAG + "/del_usr_tag", map, callback);
    }

    public void queryUserTag(String tagname, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        doRequest(URL_USER_TAG + "/query_usr_tag", map, callback);
    }

    //attend
    public void addUserAttend(String tagname, String exercise_id, String created_day, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("activity_id", exercise_id);
        map.put("created_day", created_day);
        doRequest(URL_ATTENDENCY + "/addusrActi", map, callback);
    }

    public void deleteUserAttend(String tagname, String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("activity_id", exercise_id);
        doRequest(URL_ATTENDENCY + "/delusrActi", map, callback);
    }

    public void queryWhoAttend(String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", exercise_id);
        doRequest(URL_ATTENDENCY + "/query_usrforActi", map, callback);
    }

    public void queryIsAttend(String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("activity_id", exercise_id);
        doRequest(URL_ATTENDENCY + "/query_actiforusr", map, callback);
    }

    public void queryAllMyAttend(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("type", "all");
        doRequest(URL_ATTENDENCY + "/query_allforusr", map, callback);
    }

    public void queryAllMyAttendWithTime(String time_lower_bound, String time_upper_bound, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("time_lower_bound", time_lower_bound);
        map.put("time_upper_bound", time_upper_bound);
        doRequest(URL_ATTENDENCY + "/query_actibytime", map, callback);
    }

    public void queryMyAttendWithNoFinish(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("type", "waiting");
        doRequest(URL_ATTENDENCY + "/query_actibeforeend", map, callback);
    }
    //attend

    //exercise_comment
    public void addComment(String exercise_id, String comment, String grade, String time, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        map.put("activity_id", exercise_id);
        map.put("comment", comment);
        map.put("grade", grade);
        map.put("time", time);
        doRequest(URL_EXERCISE_COMMENT + "/addcomment", map, callback);
    }

    public void queryComment(String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", exercise_id);
        doRequest(URL_ATTENDENCY + "/query_comment", map, callback);
    }
    //exercise_comment

    //notice
    public void queryMyNotice(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserName);
        doRequest(URL_NOTICE + "/query_notice", map, callback);
    }


    public void doRequest(String url, Map<String, String> paramsmap, Callback callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        for (Map.Entry<String, String> param : paramsmap.entrySet()) {
            builder = builder.addParams(param.getKey(), param.getValue());
        }
        builder.build().execute(callback);
    }

    public void doRequest(String url, Map<String, String> paramsmap, final Runnable success, final Runnable fail) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
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
