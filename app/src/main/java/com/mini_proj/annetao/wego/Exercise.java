package com.mini_proj.annetao.wego;

import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

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
    private String description;
    private String created_datetime;
    private String status;
    private String pic_store;
    private float avg_cost;
    private String deadline;
    private int attendencyNum;
    private Map<String, String> tagList;

    public Exercise(int id_, float latitude_, int sponsor_id_, String start_time_,
                    String end_time_, String name_, float longitude_,
                    String description_, String created_datetime_, String status_,
                    String picUrl_, float avgCost_, String deadline_, int attendencyNum_,
                    Map<String, String> tagList_) {
        id = id_;
        latitude = latitude_;
        sponsor_id = sponsor_id_;
        start_time = start_time_;
        end_time = end_time_;
        name = name_;
        longitude = longitude_;
        description = description_;
        created_datetime = created_datetime_;
        status = status_;
        pic_store = picUrl_;
        setAvg_cost(avgCost_);
        setDeadline(deadline_);
        setAttendencyNum(attendencyNum_);
        setTagList(tagList_);
    }


    public static void upload(float latitude_, int sponsor_id_, String start_time_,
                              String end_time_, String name_, float longitude_, String description_,
                              float avgCost_, String deadline_,
                              Map<String, String> tagList_, final Runnable runnable) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude_);
        map.put("sponsor_id", "" + sponsor_id_);
        map.put("start_time", start_time_);
        map.put("end_time", end_time_);
        map.put("name", name_);
        map.put("longitude", "" + longitude_);
        map.put("description", "" + description_);
        map.put("avg_cost", "" + avgCost_);
        map.put("deadline", deadline_);
        for (Map.Entry<String, String> entry : tagList_.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/addactivity"
                , map, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("WeGo", "新建活动失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Exercise exercise = gson.fromJson(response, Exercise.class);
                        UserInf.getUserInf().addExerciseMyList(exercise);
                        Map<String, String> tagList = exercise.getTagList();
                        for (Map.Entry<String, String> entry : tagList.entrySet()) {
                            ExercisePool.getTopicPool().addExerciseToMap(entry.getValue(), exercise);
                        }
                        NetworkTools.getNetworkTools().mHandler.post(runnable);
                    }
                });
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

    //exercise_comment
    public void addComment(String comment, String grade, String time, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);

        map.put("activity_id", "" + id);
        map.put("comment", comment);
        map.put("grade", grade);
        map.put("time", time);
        NetworkTools.doRequest(NetworkTools.URL_EXERCISE_COMMENT + "/addcomment", map, callback);
    }

    public void queryComment(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("activity_id", "" + id);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_comment", map, callback);
    }
    //exercise_comment


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

    public String getPic_store() {
        return pic_store;
    }

    public void setPic_store(String pic_store) {
        this.pic_store = pic_store;
    }

    public float getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(float avg_cost) {
        this.avg_cost = avg_cost;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getAttendencyNum() {
        return attendencyNum;
    }

    public void setAttendencyNum(int attendencyNum) {
        this.attendencyNum = attendencyNum;
    }

    public Map<String, String> getTagList() {
        return tagList;
    }

    public void setTagList(Map<String, String> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }
}
