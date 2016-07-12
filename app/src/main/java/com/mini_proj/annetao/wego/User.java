package com.mini_proj.annetao.wego;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huangweiping on 16/7/10.
 */
public class User {

    public static final String PREFERENCES_NAME = "VALUE";

    private boolean login = false;
    private String tagString = "";
    private int id = -1;
    private String openId = "";
    private String token ="";
    private String name = "";
    private String password = "";
    private int year;
    private int month;
    private int day;
    private int gender = -1;
    private float credit = 0;

    private SharedPreferences mSharedPreferences = null;

    public boolean isLogin() {
        login = getSharedPreferences().getBoolean("LOGIN", false);
        return login;
    }

    public void setLogin(boolean login) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putBoolean("LOGIN", login)
                .commit();
        this.login = login;
    }

    public String getTagString() {
        tagString = getSharedPreferences().getString("TAGSTRING","");
        return tagString;
    }

    public void setTagString(String tagString) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString("TAGSTRING", tagString)
                .commit();
        this.tagString = tagString;
    }

    public void updateByJsonResult(JSONObject jsonObject){
        try {
            if(jsonObject.getString("name")!=null) setName(jsonObject.getString("name"));
            if(jsonObject.getString("password")!=null) setPassword(jsonObject.getString("password"));
            if(jsonObject.getString("birthday")!=null){
                String birthDayStr = jsonObject.getString("birthday");
                String bds[] = birthDayStr.split("-");
                year = Integer.parseInt(bds[0]);
                month = Integer.parseInt(bds[1]);
                day = Integer.parseInt(bds[2]);
                setYear(year);
                setMonth(month);
                setDay(day);
            }
            setGender(jsonObject.getInt("gender"));
            setCredit((float)jsonObject.getDouble("credit"));
            if(jsonObject.getJSONArray("tags")!=null){
                JSONArray ja = jsonObject.getJSONArray("tags");
                String tagString="";
                if(ja.length()>0) tagString+=ja.getInt(0);
                for(int i = 1;i<ja.length();i++){
                    tagString+=","+ja.getInt(i);
                }
                setTagString(tagString);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        id = getSharedPreferences().getInt("ID", -1);
        return id;
    }

    public void setId(int id) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putInt("ID", id)
                .commit();
        this.id = id;
    }

    public String getName() {
        name = getSharedPreferences().getString("NAME", "");
        return name;
    }

    public void setName(String name) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString("NAME", name)
                .commit();
        this.name = name;
    }

    public String getOpenId() {
        openId = getSharedPreferences().getString("OPENID", "");
        return openId;
    }

    public void setOpenId(String openId) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString("OPENID", openId)
                .commit();
        this.openId = openId;
    }

    public String getToken() {
        token = getSharedPreferences().getString("TOKEN", "");
        return token;
    }

    public void setToken(String token) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString("TOKEN", token)
                .commit();
        this.token = token;
    }

    public String getPassword() {
        password = getSharedPreferences().getString("PASSWORD", "");
        return password;
    }

    public void setPassword(String password) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString("PASSWORD", password)
                .commit();
        this.password = password;
    }

    public int getYear() {
        year = getSharedPreferences().getInt("YEAR", -1);
        return year;
    }

    public void setYear(int year) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putInt("YEAR", year)
                .commit();
        this.year = year;
    }

    public int getMonth() {
        month = getSharedPreferences().getInt("MONTH", -1);
        return month;
    }

    public void setMonth(int month) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putInt("MONTH", month)
                .commit();
        this.month = month;
    }

    public int getDay() {
        day = getSharedPreferences().getInt("DAY", -1);
        return day;
    }

    public void setDay(int day) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putInt("DAY", day)
                .commit();
        this.day = day;
    }

    public int getGender() {
        gender = getSharedPreferences().getInt("GENDER", -1);
        return gender;
    }

    public void setGender(int gender) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putInt("GENDER", gender)
                .commit();
        this.gender = gender;
    }

    public float getCredit() {
        credit = getSharedPreferences().getFloat("CREDIT", -1);
        return credit;
    }

    public void setCredit(float credit) {
        MyApplication.getAppContext()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putFloat("CREDIT", credit)
                .commit();
        this.credit = credit;
    }

    private SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) mSharedPreferences = MyApplication.getAppContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences;
    }

    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
