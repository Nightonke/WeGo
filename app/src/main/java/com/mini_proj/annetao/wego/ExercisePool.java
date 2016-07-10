package com.mini_proj.annetao.wego;

import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/9.
 */
public class ExercisePool {
    private static HashMap<String, String> topicMap;
    private static ExercisePool exercisePool = null;

    public ExercisePool() {
        topicMap = new HashMap<>();
        exercisePool = this;
    }

    ExercisePool getTopicPool() {
        if (exercisePool == null)
            return new ExercisePool();
        return exercisePool;
    }

    public void queryPlaceTopic(float latitude_lower_bound, float latitude_upper_bound,
                                float longitude_lower_bound, float longitude_upper_bound,
                                Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("latitude_lower_bound", "" + latitude_lower_bound);
        map.put("latitude_upper_bound", "" + latitude_upper_bound);
        map.put("longitude_lower_bound", "" + longitude_lower_bound);
        map.put("longitude_upper_bound", "" + longitude_upper_bound);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_acti"
                , map, callback);
    }

    public void queryPlaceTopicWithTag(float latitude_lower_bound, float latitude_upper_bound,
                                       float longitude_lower_bound, float longitude_upper_bound, String tag,
                                       Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("latitude_lower_bound", "" + latitude_lower_bound);
        map.put("latitude_upper_bound", "" + latitude_upper_bound);
        map.put("longitude_lower_bound", "" + longitude_lower_bound);
        map.put("longitude_upper_bound", "" + longitude_upper_bound);
        map.put("tag", "" + tag);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_tagActi"
                , map, callback);
    }

    public void queryTopicWithSponsor(int sponsor_id,
                                      Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("sponsor_id", "" + sponsor_id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/userActi"
                , map, callback);
    }

    public void queryTopicWithSponsorAndTime(int sponsor_id, String time_lower_bound, String time_upper_bound,
                                             Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("sponsor_id", "" + sponsor_id);
        map.put("time_lower_bound", "" + time_lower_bound);
        map.put("time_upper_bound", "" + time_upper_bound);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_userActiTime"
                , map, callback);
    }
}
