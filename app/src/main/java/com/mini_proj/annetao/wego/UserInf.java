package com.mini_proj.annetao.wego;

import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/10.
 */
public class UserInf {
    private static UserInf userinf;
    private ArrayList<Exercise> exerciseAttendList;
    private ArrayList<Exercise> exerciseMyList;

    private UserInf() {
        exerciseMyList = new ArrayList<>();
        exerciseAttendList = new ArrayList<>();
        userinf = this;
    }

    public static UserInf getUserInf() {
        if (userinf == null)
            return new UserInf();
        return userinf;
    }

    public void doLogin(String user, Callback callback) {
        Map<String, String> map = new HashMap<>();
        //map.putAll(NetworkTools.paramsMap);
        map.put("id", user);
        //map.put("password", pass);
        NetworkTools.doRequest(NetworkTools.URL_USER + "/login", map, callback);
    }

    //Tag
    public void addUserTag(String tagid, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("tag_id", tagid);
        NetworkTools.doRequest(NetworkTools.URL_USER_TAG + "/add_user_tag", map, callback);
    }

    public void addUserNewTag(String tagname, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("name", tagname);
        NetworkTools.doRequest(NetworkTools.URL_USER_TAG + "/add_user_new_tag", map, callback);
    }

    //Tag
    public void deleteUserTag(String tagid, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("tag_id", tagid);
        NetworkTools.doRequest(NetworkTools.URL_USER_TAG + "/del_usr_tag", map, callback);
    }

    public void queryUserTag(String tagname, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        NetworkTools.doRequest(NetworkTools.URL_USER_TAG + "/query_usr_tag", map, callback);
    }

    //attend
    public void addUserAttend(String tagname, String exercise_id, String created_day, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("activity_id", exercise_id);
        map.put("created_day", created_day);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/addusrActi", map, callback);
    }

    public void deleteUserAttend(String tagname, String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("activity_id", exercise_id);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/delusrActi", map, callback);
    }

    public void queryIsAttend(String exercise_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("activity_id", exercise_id);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_actiforusr", map, callback);
    }

    public void queryAllMyAttend(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("type", "all");
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_allforusr", map, callback);
    }

    public void queryAllMyAttendWithTime(String time_lower_bound, String time_upper_bound, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("time_lower_bound", time_lower_bound);
        map.put("time_upper_bound", time_upper_bound);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_actibytime", map, callback);
    }

    public void queryMyAttendWithNoFinish(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("type", "waiting");
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_actibeforeend", map, callback);
    }

    public ArrayList<Exercise> getExerciseMyList() {
        return exerciseMyList;
    }

    public void setExerciseMyList(ArrayList<Exercise> exerciseMyList) {
        this.exerciseMyList = exerciseMyList;
    }

    public void addExerciseMyList(Exercise exercise) {
        exerciseMyList.add(exercise);
    }

    public void addExerciseAttendList(Exercise exercise) {
        exerciseAttendList.add(exercise);
    }

    public ArrayList<Exercise> getExerciseAttendList() {
        return exerciseAttendList;
    }

    public void setExerciseAttendList(ArrayList<Exercise> exerciseAttendList) {
        this.exerciseAttendList = exerciseAttendList;
    }
    //attend
}
