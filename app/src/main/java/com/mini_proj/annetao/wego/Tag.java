package com.mini_proj.annetao.wego;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangweiping on 16/7/10.
 */
public enum Tag {

    BOARD_GAME(0),
    SPORTS(1),
    PHOTOGRAPHY(2),
    COMPETITION(3),
    TRAVEL(4),
    COMMONWEAL(5),
    COURSE(6);

    int v;

    Tag(int v) {
        this.v = v;
    }

    public static Tag value(String tagName) {
        switch (tagName) {
            case "桌游": return BOARD_GAME;
            case "运动": return SPORTS;
            case "摄影": return PHOTOGRAPHY;
            case "赛事": return COMPETITION;
            case "旅行": return TRAVEL;
            case "公益": return COMMONWEAL;
            case "课程": return COURSE;
        }
        return null;
    }

    public static Tag value(int v) {
        switch (v) {
            case 0: return BOARD_GAME;
            case 1: return SPORTS;
            case 2: return PHOTOGRAPHY;
            case 3: return COMPETITION;
            case 4: return TRAVEL;
            case 5: return COMMONWEAL;
            case 6: return COURSE;
        }
        return null;
    }

    @Override
    public String toString() {
        switch (v) {
            case 0: return "桌游";
            case 1: return "运动";
            case 2: return "摄影";
            case 3: return "赛事";
            case 4: return "旅行";
            case 5: return "公益";
            case 6: return "课程";
        }
        return null;
    }

    public static List<String> getAllTagName() {
        ArrayList<String> tagNames = new ArrayList<>();
        tagNames.add("桌游");
        tagNames.add("运动");
        tagNames.add("摄影");
        tagNames.add("赛事");
        tagNames.add("旅行");
        tagNames.add("公益");
        tagNames.add("课程");
        return tagNames;
    }

}