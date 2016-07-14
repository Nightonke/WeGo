package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentMessage extends Fragment {

    private MaterialDialog dialog;
    private ArrayList<UserNotice> noticeArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_message, container, false);
        noticeArrayList=new ArrayList<>();
        /*
        dialog = new MaterialDialog.Builder(getActivity())
                .title("sdfsdf")
                .content("dsfsdf")
                .progress(true, 0)
                .cancelable(false)
                .show();
        */
        UserInf.getUserInf().queryMyNotice(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("Wego", e.toString() + " " + id);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("Wego", response + " " + id);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array=object.getJSONArray("data");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonMsg=array.getJSONObject(i);
                        int id_=jsonMsg.getInt("id");
                        String user_id=jsonMsg.getString("user_id");
                        int exercise_id=jsonMsg.getInt("exercise_id");
                        String notice_content=jsonMsg.getString("notice_content");
                        String time=jsonMsg.getString("time");
                        int status=jsonMsg.getInt("status");
                        UserNotice notice=new UserNotice(id_,user_id,exercise_id,notice_content,time,status);
                        noticeArrayList.add(notice);
                    }
                    Log.d("Wego",  "noticeArrayList size: " + noticeArrayList.size());
                }
                catch(JSONException e)
                {
                    Log.e("WeGo",e.toString());
                }

            }
        });

        ExercisePool.getTopicPool().queryTopicWithSponsor("1",new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("Wego", e.toString() + " " + id);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("Wego", response + " " + id);
            }
        });

        return messageLayout;
    }

}
