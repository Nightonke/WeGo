package com.mini_proj.annetao.wego;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by huangweiping on 16/7/10.
 */
public class User {

    public static final String PREFERENCES_NAME = "VALUE";

    private boolean login = false;
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
