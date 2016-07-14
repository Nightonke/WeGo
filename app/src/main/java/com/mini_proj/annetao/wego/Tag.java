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
            case "桌游": return BOARD_GAME;
            case "摄影": return PHOTOGRAPHY;
            case "郊游": return TRAVEL;
            case "公益": return COMMONWEAL;
            case "音乐会": return CONCERT;
            case "戏剧": return DRAMA;
            case "电影": return FILM;
            case "读书": return READING;
            case "展览": return SHOW;
            case "外语": return FOREIGN_LANGUAGE;
            case "桌球": return SNOOKER;
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

    public static String getPicUrlById(int tag) {
        String[] picUrlArray = {
                "",//NONE(-1),
                "http://ghy.swufe.edu.cn/images/imageData/life/2009/10/14/%C0%BA%C7%F2.jpg",//BASKETBALL(0),
                "http://pic23.nipic.com/20120919/10197997_083116891001_2.jpg",//FOOTBALL(1),
                "http://pic21.nipic.com/20120531/6863287_032401854000_2.jpg",//BADMINTON(2),
                "http://pic25.nipic.com/20121118/11029734_035104413136_2.jpg",//TABLE_TENNIS(3),
                "http://img15.3lian.com/2015/f3/07/d/138.jpg",//SWIMMING(4),
                "http://www.wan-ka.com/upload/image/201501/b28c830c-5b06-48bf-8fd4-9c0a7b63747f.png",//RUNNING(5),
                "http://img01.taopic.com/140921/318765-1409210H95759.jpg",//BODY_BUILDING(6),
                "http://s2.nuomi.bdimg.com/upload/deal/2014/3/V_L/730487-1396067910093.jpg",//BOARD_GAME(7),
                "http://img.taopic.com/uploads/allimg/120410/8881-12041016030559.jpg",//PHOTOGRAPHY(8),
                "http://www.wxjy.com.cn/UploadFiles/zt/2014/3/201403181616353675.JPG",//TRAVEL(9),
                "http://jiangsu.china.com.cn/uploadfile/2014/0801/20140801022812757.jpg",//COMMONWEAL(10),
                "http://img1.gtimg.com/ent/pics/hv1/72/48/1311/85260087.jpg",//CONCERT(11),
                "http://images.china.cn/attachement/jpg/site1000/20071114/000cf1bdcc9708a514dd03.jpg",//DRAMA(12),
                "https://d13yacurqjgara.cloudfront.net/users/31752/screenshots/2262418/movie-time_1x.png",//FILM(13),
                "http://img2.imgtn.bdimg.com/it/u=2312699018,3601099396&fm=21&gp=0.jpg",//READING(14),
                "http://pic.58pic.com/58pic/15/45/36/31P58PIC37n_1024.jpg",//SHOW(15),
                "http://files.eduuu.com/img/2013/09/27/153421_524534fd3f87a.png",//FOREIGN_LANGUAGE(16),
                "http://pic41.nipic.com/20140514/4135003_132354015000_2.jpg",//SNOOKER(17);
        };
        return picUrlArray[tag+1];
    }

}