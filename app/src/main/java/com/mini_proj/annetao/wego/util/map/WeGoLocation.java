package com.mini_proj.annetao.wego.util.map;

import com.mini_proj.annetao.wego.Exercise;
import com.mini_proj.annetao.wego.Tag;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16/7/10.
 */
public class WeGoLocation {
    public String id;
    public String title;
    public String province;
    public String city;
    public String district;
    public int type;
    public Location location;
    public String disc;

    public WeGoLocation() {
    }

    public WeGoLocation(Exercise e) {
        location = new Location();
        location.lat(e.getLatitude());
        location.lng(e.getLongitude());
        String name = e.getName();
        if(name.length()>4){
            name = name.substring(0,4)+"..";
        }
        title = "活动名—"+name;
        disc = "所属标签—"+Tag.value(e.getTagId()).toString()+"\n"+" 点击查看详情";


    }

    public WeGoLocation(SuggestionResultObject.SuggestionData sd) {
        this.id = sd.id;
        this.title = sd.title;
        this.province = sd.province;
        this.city = sd.city;
        this.district = sd.district;
        this.type = sd.type;
        this.location = sd.location;
        this.disc = sd.address;
    }
    public WeGoLocation(LatLng latlng) {

        this.location = new Location();
        location.lat((float)latlng.latitude);
        location.lng((float)latlng.longitude);
    }

    public LatLng getLatLng(){
        if(null == location) return null;
        LatLng latLng = new LatLng(location.lat,location.lng);
        return latLng;
    }
    public WeGoLocation(String str) {
        String[] strs = str.split(",");
        this.id = strs[0];
        this.title = strs[1];
        this.province = strs[2];
        this.city = strs[3];
        this.district = strs[4];
        this.type = Integer.parseInt(strs[5]);
        this.location = new Location();
        location.lat(Float.parseFloat(strs[6]));
        location.lng(Float.parseFloat(strs[7]));
        this.disc = strs[8];
    }

    @Override
    public String toString() {
        String str="";
        str+=id+",";
        str+=title+",";
        str+=province+",";
        str+=city+",";
        str+=district+",";
        str+=type+",";
        str+=location.lat+",";
        str+=location.lng+",";
        str+= disc;
        return str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}
