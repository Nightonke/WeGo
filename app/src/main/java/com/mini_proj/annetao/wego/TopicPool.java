package com.mini_proj.annetao.wego;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bran on 2016/7/9.
 */
public class TopicPool {
    private static HashMap<String, String> topicMap;
    private static TopicPool topicPool = null;

    public TopicPool() {
        topicMap = new HashMap<>();
        topicPool = this;
    }

    TopicPool getTopicPool() {
        if (topicPool == null)
            return new TopicPool();
        return topicPool;
    }

    public void queryPlaceTopic(float latitude_lower_bound, float latitude_upper_bound,
                                float longitude_lower_bound, float longitude_upper_bound,
                                Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("latitude_lower_bound", "" + latitude_lower_bound);
        map.put("latitude_upper_bound", "" + latitude_upper_bound);
        map.put("longitude_lower_bound", "" + longitude_lower_bound);
        map.put("longitude_upper_bound", "" + longitude_upper_bound);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/query_acti"
                , map, success, failed);
    }

    public void queryPlaceTopicWithTag(float latitude_lower_bound, float latitude_upper_bound,
                                       float longitude_lower_bound, float longitude_upper_bound, String tag,
                                       Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("latitude_lower_bound", "" + latitude_lower_bound);
        map.put("latitude_upper_bound", "" + latitude_upper_bound);
        map.put("longitude_lower_bound", "" + longitude_lower_bound);
        map.put("longitude_upper_bound", "" + longitude_upper_bound);
        map.put("tag", "" + tag);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/query_tagActi"
                , map, success, failed);
    }

    public void queryTopicWithSponsor(int sponsor_id,
                                      Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("sponsor_id", "" + sponsor_id);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/userActi"
                , map, success, failed);
    }

    public void queryTopicWithSponsorAndTime(int sponsor_id, String time_lower_bound, String time_upper_bound,
                                             Runnable success, Runnable failed) {
        Map<String, String> map = new HashMap<>();
        map.put("sponsor_id", "" + sponsor_id);
        map.put("time_lower_bound", "" + time_lower_bound);
        map.put("time_upper_bound", "" + time_upper_bound);
        NetworkTools.getNetworkTools().doRequest(NetworkTools.URL_ACTIVITY + "/query_userActiTime"
                , map, success, failed);
    }
}
