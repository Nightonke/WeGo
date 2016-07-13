package com.mini_proj.annetao.wego;


import android.graphics.Bitmap;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

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

    public Exercise()
    {
        tagList=new HashMap<>();
    }

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

    public static Exercise jsonToExercise(JSONObject dataJson) throws JSONException {
        int id_ = Integer.valueOf(dataJson.getString("id"));
        float latitude_ = Float.valueOf(dataJson.getString("latitude"));
        float longitude_ = Float.valueOf(dataJson.getString("longitude"));
        int sponsor_id_ = Integer.valueOf(dataJson.getString("sponsor_id"));
        String start_time_ = dataJson.getString("start_time");
        String end_time_ = dataJson.getString("end_time");
        String description_ = dataJson.getString("description");
        String name_ = dataJson.getString("name");
        int min_num_ = Integer.valueOf(dataJson.getString("min_num"));
        int max_num_ = Integer.valueOf(dataJson.getString("max_num"));
        String deadline_ = dataJson.getString("deadline");
        String picUrl_ = dataJson.getString("pic_store");
        float avgCost_ = Float.valueOf(dataJson.getString("avg_cost"));
        //tag数组
        String status_ = "";
        int attendencyNum_;
        Map<String, String> tagList_ = new HashMap<String, String>();
        //Gson gson = new Gson();
        //Exercise exercise = gson.fromJson(response, Exercise.class);
        Exercise exercise = new Exercise(id_, latitude_, sponsor_id_, start_time_, end_time_
                , name_, longitude_, description_, "2011-01-01", status_, picUrl_, avgCost_, deadline_,//"attendencynum"
                1, tagList_);
        return exercise;
    }

    public static void createExercise(
            float latitude_,
            float longitude_,
            String sponsor_id_,
            String start_time_,
            String end_time_,
            String name_,
            String description_,
            float avgCost_,
            String deadline_,
            int min_num_,
            int max_num_,
            Map<String, String> tagList_,
            String pic_store,
            Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude_);
        map.put("longitude", "" + longitude_);
        map.put("open_id", sponsor_id_);
        map.put("sponsor_id", sponsor_id_);
        map.put("start_time", start_time_);
        map.put("end_time", end_time_);
        map.put("description", "" + description_);
        map.put("name", name_);
        map.put("min_num", "" + min_num_);
        map.put("max_num", "" + max_num_);
        map.put("avg_cost", "" + avgCost_);
        map.put("deadline", deadline_);
        map.put("pic_store", pic_store);
        //图片！！
        JSONObject result = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : tagList_.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("tag", result.toString());
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/add_exercise"
                , map, callback);
    }

    public static void upload(float latitude_, float longitude_, int sponsor_id_, String start_time_,
                              String end_time_, String name_, String description_,
                              float avgCost_, String deadline_, int min_num_, int max_num_,
                              Map<String, String> tagList_, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude_);
        map.put("longitude", "" + longitude_);
        map.put("sponsor_id", "" + sponsor_id_);
        map.put("start_time", start_time_);
        map.put("end_time", end_time_);
        map.put("description", "" + description_);
        map.put("name", name_);
        map.put("min_num", "" + min_num_);
        map.put("max_num", "" + max_num_);
        map.put("avg_cost", "" + avgCost_);
        map.put("deadline", deadline_);
        //图片！！
        JSONObject result = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : tagList_.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("tag", result.toString());
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/add_exercise"
                , map, callback);
    }

    public void downloadPic(String url)
    {
        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {

                    }
                });
    }

    public void addExercise_tag(String tagid, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("tag_id", "" + tagid);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE_TAG + "/add_exer_tag"
                , map, callback);
    }

    public void deleteExercise_tag(int tag_id, Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("tag_id", "" + tag_id);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE_TAG + "/del_tag"
                , map, callback);
    }

    public void queryExercise_tag(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("exercise_id", "" + id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE_TAG + "/query_exer_tag"
                , map, callback);
    }

    public void updateStartTime(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("id", "" + id);
        map.put("start_time", "" + start_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chg_start_time"
                , map, callback);
    }

    public void updateEndTime(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("end_time", "" + end_time);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chg_end_time"
                , map, callback);
    }

    public void updateName(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("name", "" + name);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chg_name"
                , map, callback);
    }

    public void updatePlace(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chg_location"
                , map, callback);
    }

    public void updateStatus(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("status", "" + getStatus());
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/chg_status"
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

    public void queryAllAttend(Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("activity_id", "" + id);
        NetworkTools.doRequest(NetworkTools.URL_ATTENDENCY + "/query_usrforActi", map, callback);
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