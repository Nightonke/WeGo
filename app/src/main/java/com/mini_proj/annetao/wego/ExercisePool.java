package com.mini_proj.annetao.wego;

import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/9.
 */
public class ExercisePool {
    private static Map<String, ArrayList<Exercise>> exerciseMap;
    private static ExercisePool exercisePool = null;

    private ExercisePool() {
        exerciseMap = new HashMap<>();
        exercisePool = this;
    }

    public static ExercisePool getTopicPool() {
        if (exercisePool == null)
            return new ExercisePool();
        return exercisePool;
    }

    public ArrayList<Exercise> getExerciseList(String tag) {
        return exerciseMap.get(tag);
    }

    public ArrayList<Exercise> getAllExercise() {
        ArrayList<Exercise> list = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Exercise>> entry : exerciseMap.entrySet()) {
            list.addAll(entry.getValue());
        }
        return list;
    }

    public void addExerciseToMap(String tag, Exercise exercise) {
        if (exerciseMap.get(tag) == null)
            exerciseMap.put(tag, new ArrayList<Exercise>());
        exerciseMap.get(tag).add(exercise);
    }

    public void queryPlaceTopic(float latitude, float longitude,
                                Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_nearby_exercise"
                , map, callback);
    }

    public void queryPlaceTopicWithTag(float latitude, float longitude, String tag,
                                       Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("latitude", "" + latitude);
        map.put("longitude", "" + longitude);
        map.put("tag", "" + tag);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_nearby_tag_exercise"
                , map, callback);
    }

    public void queryTopicWithSponsor(int sponsor_id,
                                      Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("sponsor_id", "" + sponsor_id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_user_exercise"
                , map, callback);
    }

    public void queryTopicWithOpenId(String openId,
                                      Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("sponsor_id", "" + openId);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_user_exercise"
                , map, callback);
    }

    public void queryTopicWithSponsorAndTime(int sponsor_id, String time_lower_bound, String time_upper_bound,
                                             Callback callback) {
        Map<String, String> map = new HashMap<>();
        map.putAll(NetworkTools.paramsMap);
        map.put("sponsor_id", "" + sponsor_id);
        map.put("time_lower_bound", "" + time_lower_bound);
        map.put("time_upper_bound", "" + time_upper_bound);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_EXERCISE + "/query_user_current_exercise"
                , map, callback);
    }

    public ArrayList<Exercise> getTestExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(1, 39.906901f, 0, "2016-07-07 20:00", "2016-07-08 06:00", "通宵三国杀", 116.397972f, "即将到来", "2016-07-07 20:00", "status", "https://modao.cc/uploads2/images/409/4094481/raw_1467809037.png", 100f, "deadline", 100, null));
        exercises.add(new Exercise(2, 31.247241f, 0, "2016-07-07 20:00", "2016-07-08 06:00", "通宵狼人杀", 121.492696f, "即将到来", "2016-07-07 20:00", "status", "http://ww3.sinaimg.cn/crop.0.0.690.387.1000.562/006tT5Uqjw1f5bqxf3cisj30j60bzaas.jpg", 100f, "deadline", 100, null));
        exercises.add(new Exercise(3, 36.666574f, 0, "2016-07-07 20:00", "2016-07-08 06:00", "通宵LOL", 117.028908f, "即将到来", "2016-07-07 20:00", "status", "http://4.bp.blogspot.com/-F4e4ArhbKL0/UoC7b4qnraI/AAAAAAAAACA/WcMCqYcmF5g/w1200-h630-p-nu/tumblr_static_22924_league_of_legends.jpg", 100f, "deadline", 100, null));
        return exercises;
    }
}