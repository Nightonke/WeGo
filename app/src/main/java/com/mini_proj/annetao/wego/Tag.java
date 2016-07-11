package com.mini_proj.annetao.wego;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangweiping on 16/7/10.
 */
public enum Tag {

    NONE(-1),
    BASKETBALL(0),
    FOOTBALL(1),
    BADMINTON(2),
    TABLE_TENNIS(3),
    SWIMMING(4),
    RUNNING(5),
    BODY_BUILDING(6),
    BOARD_GAME(7),
    PHOTOGRAPHY(8),
    TRAVEL(9),
    COMMONWEAL(10),
    CONCERT(11),
    DRAMA(12),
    FILM(13),
    READING(14),
    SHOW(15),
    FOREIGN_LANGUAGE(16),
    SNOOKER(17);

    int v;

    Tag(int v) {
        this.v = v;
    }

    public static Tag value(String tagName) {
        switch (tagName) {
            case "篮球": return BASKETBALL;
            case "足球": return FOOTBALL;
            case "羽毛球": return BADMINTON;
            case "乒乓球": return TABLE_TENNIS;
            case "游泳": return SWIMMING;
            case "跑步": return RUNNING;
            case "健身": return BODY_BUILDING;
            case "桌球": return BOARD_GAME;
            case "摄影": return PHOTOGRAPHY;
            case "旅游": return TRAVEL;
            case "公益": return COMMONWEAL;
            case "音乐会": return CONCERT;
            case "戏剧": return DRAMA;
            case "电影": return FILM;
            case "读书": return READING;
            case "展览": return SHOW;
            case "外语": return FOREIGN_LANGUAGE;
            case "台球": return SNOOKER;
        }
        return null;
    }

    public static Tag value(int v) {
        switch (v) {
            case 0: return BASKETBALL;
            case 1: return FOOTBALL;
            case 2: return BADMINTON;
            case 3: return TABLE_TENNIS;
            case 4: return SWIMMING;
            case 5: return RUNNING;
            case 6: return BODY_BUILDING;
            case 7: return BOARD_GAME;
            case 8: return PHOTOGRAPHY;
            case 9: return TRAVEL;
            case 10: return COMMONWEAL;
            case 11: return CONCERT;
            case 12: return DRAMA;
            case 13: return FILM;
            case 14: return READING;
            case 15: return SHOW;
            case 16: return FOREIGN_LANGUAGE;
            case 17: return SNOOKER;
        }
        return null;
    }

    @Override
    public String toString() {
        switch (v) {
            case 0: return "篮球";
            case 1: return "足球";
            case 2: return "羽毛球";
            case 3: return "乒乓球";
            case 4: return "游泳";
            case 5: return "跑步";
            case 6: return "健身";
            case 7: return "桌游";
            case 8: return "摄影";
            case 9: return "郊游";
            case 10: return "公益";
            case 11: return "音乐会";
            case 12: return "戏剧";
            case 13: return "电影";
            case 14: return "读书";
            case 15: return "展览";
            case 16: return "外语";
            case 17: return "桌球";
        }
        return null;
    }

    public static List<String> getAllTagName() {
        ArrayList<String> tagNames = new ArrayList<>();
        tagNames.add("篮球");
        tagNames.add("足球");
        tagNames.add("羽毛球");
        tagNames.add("乒乓球");
        tagNames.add("游泳");
        tagNames.add("跑步");
        tagNames.add("健身");
        tagNames.add("桌游");
        tagNames.add("摄影");
        tagNames.add("郊游");
        tagNames.add("公益");
        tagNames.add("音乐会");
        tagNames.add("戏剧");
        tagNames.add("电影");
        tagNames.add("读书");
        tagNames.add("展览");
        tagNames.add("外语");
        tagNames.add("桌球");
        return tagNames;
    }

}