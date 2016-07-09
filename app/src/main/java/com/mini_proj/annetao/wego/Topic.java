package com.mini_proj.annetao.wego;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/9.
 */
public class Topic {
    private int id;
    private float latitude;
    private int sponsor_id;
    private String start_time;
    private String end_time;
    private String name;
    private float longitude;
    private String status;

    public Topic(int id_, float latitude_, int sponsor_id_, String start_time_,
                 String end_time_, String name_, float longitude_) {
        id = id_;
        latitude = latitude_;
        sponsor_id = sponsor_id_;
        start_time = start_time_;
        end_time = end_time_;
        name = name_;
        longitude = longitude_;
    }

    public void upload(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        map.put("sponsor_id", "" + sponsor_id);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        map.put("name", name);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/addactivity"
                , map, success, failed);
    }

    public void addActivity_tag(String tagname, Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "" + tagname);
        map.put("activity_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/addActiTag"
                , map, success, failed);
    }

    public void deleteActivity_tag(int tag_id, Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("tag_id", "" + tag_id);
        map.put("activity_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/delTag"
                , map, success, failed);
    }

    public void queryActivity_tag(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/query_ActiTag"
                , map, success, failed);
    }

    public void updateStartTime(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("start_time", "" + start_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/chgbegintime"
                , map, success, failed);
    }

    public void updateEndTime(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("end_time", "" + end_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/chgendtime"
                , map, success, failed);
    }

    public void updateName(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("name", "" + name);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/chgactname"
                , map, success, failed);
    }

    public void updatePlace(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/chgloc"
                , map, success, failed);
    }

    public void updateStatus(Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        map.put("status", "" + status);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/chgstate"
                , map, success, failed);
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
}
