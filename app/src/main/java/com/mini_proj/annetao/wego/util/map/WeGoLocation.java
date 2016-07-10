package com.mini_proj.annetao.wego.util.map;

import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

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
    public String address;

    public WeGoLocation() {
    }

    public WeGoLocation(SuggestionResultObject.SuggestionData sd) {
        this.id = sd.id;
        this.title = sd.title;
        this.province = sd.province;
        this.city = sd.city;
        this.district = sd.district;
        this.type = sd.type;
        this.location = sd.location;
        this.address = sd.address;
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
        this.address = strs[8];
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
        str+=address;
        return str;
    }
}
