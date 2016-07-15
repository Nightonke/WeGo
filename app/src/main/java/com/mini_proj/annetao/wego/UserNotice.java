package com.mini_proj.annetao.wego;

import com.mini_proj.annetao.wego.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by bran on 2016/7/11.
 */
public class UserNotice {
    private int id;
    private String user_id;
    private int exercise_id;
    private String notice_content;
    private String time;
    private int status;

    public UserNotice(int id, String user_id, int exercise_id,
                      String notice_content, String time, int status) {
        this.id = id;
        this.user_id = user_id;
        this.exercise_id = exercise_id;
        this.notice_content = notice_content;
        this.time = getStandardTimeString(time);
        this.status = status;
    }

    public String getStandardTimeString(String time) {
        time = time.replace("T", " ");
        time = time.replace(".000Z", "");
        return time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getTime() {
        return Utils.addEightTime(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
