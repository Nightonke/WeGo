package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.mini_proj.annetao.wego.util.Utils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentHome extends Fragment implements AllExerciseAdapter.OnExerciseSelectListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean shownMapView = false;

    private WegoRelativeLayout wegoRelativeLayout;
    private MapView mapView;
    private View listViewLayout;
    private SuperRecyclerView listView;
    private AllExerciseAdapter allExerciseAdapter;
    private TextView loadingTip;
    private QQMapSupporter qqMapSupporter;
    private LinearLayout mapLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_home, container, false);

        wegoRelativeLayout = (WegoRelativeLayout) messageLayout.findViewById(R.id.base);
        listView = (SuperRecyclerView) messageLayout.findViewById(R.id.list_view);
        listView.setRefreshListener(this);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        allExerciseAdapter = new AllExerciseAdapter(this, ExercisePool.getTopicPool().getAllExercise());
        listView.setAdapter(allExerciseAdapter);

        mapView = (MapView) messageLayout.findViewById(R.id.map_view);
        qqMapSupporter = new QQMapSupporter(getActivity(),mapView,QQMapSupporter.QQ_MAP_TYPE_EXERCISES);
        listViewLayout = messageLayout.findViewById(R.id.list_view_layout);
        loadingTip = (TextView) messageLayout.findViewById(R.id.loading_tip);

        shownMapView = false;

        onRefresh();

        return messageLayout;
    }

    @Override
    public void onRefresh() {
        ExercisePool.getTopicPool().queryPlaceTopic(-1, -1, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loadingTip.setText("加载失败，点击标签重试");
            }

            @Override
            public void onResponse(String response, int id) {
                loadingTip.setVisibility(View.GONE);
                Log.d("Wego", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    ExercisePool.getTopicPool().clearAllExercises();
                    for (int i = 0; i < data.length(); i++) {
                        Exercise exercise = new Exercise(data.getJSONObject(i));
                        if (exercise.getTagId() != -1) ExercisePool.getTopicPool().addExerciseToMap(Tag.value(exercise.getTagId()).toString(), exercise);
                    }
                    //
                    allExerciseAdapter = new AllExerciseAdapter(FragmentHome.this, ExercisePool.getTopicPool().getAllExercise());
                    listView.setAdapter(allExerciseAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(shownMapView) {
            mapView.onRestart();
            Log.e("wego_map","onRestart");
            mapView.onStart();
            Log.e("wego_map","onStart");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(shownMapView) {
            mapView.onResume();
            qqMapSupporter.isMapLoaded = true;
            Log.e("wego_map","onResume");
            qqMapSupporter.initialMapView();
            if(qqMapSupporter.isMapLoaded) qqMapSupporter.updateExerciseMarkers();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.e("wego_map","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        qqMapSupporter.isMapLoaded = false;
        Log.e("wego_map","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        qqMapSupporter.isMapLoaded = false;
        Log.e("wego_map","onDestroy");
    }

    @Override
    public void onSelect(int p) {
        Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
        intent.putExtra("position", p);
        intent.putExtra("tag_id","-1");
        startActivity(intent);
    }

    public void toggleView() {
        shownMapView = !shownMapView;
        wegoRelativeLayout.setShowingMap(shownMapView);
        if (shownMapView) {
            qqMapSupporter.initialMapView();
            if(!qqMapSupporter.isMapLoaded){
                mapView.onRestart();
                Log.e("wego_map","onRestart");
                mapView.onStart();
                Log.e("wego_map","onStart");
                mapView.onResume();
                Log.e("wego_map","onResume");
                qqMapSupporter.isMapLoaded = true;
            }

            YoYo.with(Techniques.BounceInUp).duration(700).playOn(mapView);
            YoYo.with(Techniques.FadeOutUp)
                    .withListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mapView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            listView.setVisibility(View.GONE);
                        }
                    })
                    .duration(300).playOn(listViewLayout);
            if(qqMapSupporter.isMapLoaded) qqMapSupporter.updateExerciseMarkers();

        } else {
            YoYo.with(Techniques.BounceInUp).duration(700).playOn(listViewLayout);
            YoYo.with(Techniques.FadeOutUp)
                    .withListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            listView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mapView.setVisibility(View.GONE);
                        }
                    })
                    .duration(300).playOn(mapView);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        wegoRelativeLayout.setViewPager(viewPager);
    }

    public void scrollToTop() {
        listView.getRecyclerView().smoothScrollToPosition(0);
    }
}
