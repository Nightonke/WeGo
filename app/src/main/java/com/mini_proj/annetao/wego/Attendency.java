package com.mini_proj.annetao.wego;

/**
 * Created by bran on 2016/7/10.
 */
public class Attendency {
    private String user_id;
    private String attend_time;

    public Attendency(String id, String attendtime) {
        user_id = id;
        attend_time = attendtime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAttend_time() {
        return attend_time;
    }

    public void setAttend_time(String attend_time) {
        this.attend_time = attend_time;
    }
}
