package com.mini_proj.annetao.wego;

/**
 * Created by bran on 2016/7/14.
 */
public class AttendencyUser {
    private String name;
    private String birthday;
    private String gender;
    private String credit;
    private String attendTime;
    private String exercise_id;
    private String nickname;
    private String phone;

    public AttendencyUser(String name,String birthday,String gender,
                          String credit,String attendTime,String exercise_id,
                          String nickname,String phone)
    {
        this.setName(name);
        this.setBirthday(birthday);
        this.setGender(gender);
        this.setCredit(credit);
        this.setAttendTime(attendTime);
        this.setExercise_id(exercise_id);
        this.setNickname(nickname);
        this.setPhone(phone);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(String attendTime) {
        this.attendTime = attendTime;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
