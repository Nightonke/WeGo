package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentMessage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_message, container, false);

        UserInf.getUserInf().queryMyNotice(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("Wego", e.toString() + " " + id);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("Wego", response + " " + id);
            }
        });

        ExercisePool.getTopicPool().queryTopicWithSponsor(1,new StringCallback() {
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
