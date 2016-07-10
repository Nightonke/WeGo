package com.mini_proj.annetao.wego;

import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/9.
 */
public class Exercise {
    private int id;
    private float latitude;
    private int sponsor_id;
    private String start_time;
    private String end_time;
    private String name;
    private float longitude;
    private String status;
    private String picUrl;
    private ArrayList<Attendency> attendencyList;

    public Exercise(int id_, float latitude_, int sponsor_id_, String start_time_,
                    String end_time_, String name_, float longitude_, String status_, String picUrl_) {
        id = id_;
        latitude = latitude_;
        sponsor_id = sponsor_id_;
        start_time = start_time_;
        end_time = end_time_;
        name = name_;
        longitude = longitude_;
        status = status_;
        picUrl = picUrl_;
        attendencyList = new ArrayList<>();
    }

    public void upload(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        map.put("sponsor_id", "" + sponsor_id);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        map.put("name", name);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/addactivity"
                , map, callback);
    }

    public void addExercise_tag(String tagname, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("name", "" + tagname);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/add_exer_new_tag"
                , map, callback);
    }

    public void deleteExercise_tag(int tag_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("tag_id", "" + tag_id);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/del_tag"
                , map, callback);
    }

    public void queryExercise_tag(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_exer_tag"
                , map, callback);
    }

    public void updateStartTime(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("start_time", "" + start_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chgbegintime"
                , map, callback);
    }

    public void updateEndTime(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("end_time", "" + end_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chgendtime"
                , map, callback);
    }

    public void updateName(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("name", "" + name);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chgactname"
                , map, callback);
    }

    public void updatePlace(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chgloc"
                , map, callback);
    }

    public void updateStatus(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("status", "" + getStatus());
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chgstate"
                , map, callback);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(int sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addAttendency(Attendency attendency) {
        attendencyList.add(attendency);
    }

    public ArrayList<Attendency> getAttendencyList() {
        return attendencyList;
    }

    public void setAttendencyList(ArrayList<Attendency> attendencyList) {
        this.attendencyList = attendencyList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
