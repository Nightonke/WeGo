package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mini_proj.annetao.wego.util.Utils;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.zhy.http.okhttp.callback.StringCallback;

import me.gujun.android.taggroup.TagGroup;
import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentDiscovery extends Fragment implements ExerciseInTagAdapter.OnExerciseSelectListener {

    private WegoRelativeLayout wegoRelativeLayout;

    private TagGroup tagGroup;
    private RecyclerView recyclerView;
    private ExerciseInTagAdapter adapter;
    private boolean shownMapView = false;
    private View listViewLayout;
    private RecyclerView listView;
    private TextView loadingTip;
    private MapView mapView;
    private QQMapSupporter qqMapSupporter;
    private boolean autoSelect = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_discovery, container, false);

        messageLayout.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FilterActivity.class));
            }
        });

        wegoRelativeLayout = (WegoRelativeLayout) messageLayout.findViewById(R.id.base);
        tagGroup = (TagGroup) messageLayout.findViewById(R.id.tag_group);
        tagGroup.setTags(Tag.getAllTagName());
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                for (int i = 0; i < tagGroup.getTags().length; i++) {
                    if (tag.equals(tagGroup.getTags()[i])) {
                        tagGroup.getChildAt(i).setBackgroundResource(R.drawable.shape_rounded_tag);
                        tagGroup.getChildAt(i).setPadding(Utils.dp2px(20), Utils.dp2px(2), Utils.dp2px(20), Utils.dp2px(2));
                        ((TextView)tagGroup.getChildAt(i)).setTextColor(Color.WHITE);
                        ColorStateList csl = ContextCompat.getColorStateList(getContext(), R.color.tag_select_text_color);
                        if (csl != null) ((TextView)tagGroup.getChildAt(i)).setTextColor(csl);
                    } else {
                        tagGroup.getChildAt(i).setBackgroundResource(0);
                        ((TextView)tagGroup.getChildAt(i)).setTextColor(ContextCompat.getColor(getContext(), R.color.tag));
                    }
                }
                chooseTag(tag);
            }
        });
        for (int i = 0; i < tagGroup.getTags().length; i++) {
            tagGroup.getChildAt(i).setBackgroundResource(0);
            ((TextView)tagGroup.getChildAt(i)).setTextColor(ContextCompat.getColor(getContext(), R.color.tag));
        }

        listView = (RecyclerView) messageLayout.findViewById(R.id.list_view);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        adapter = new ExerciseInTagAdapter(this);
        listView.setAdapter(adapter);

        listViewLayout = messageLayout.findViewById(R.id.list_view_layout);
        loadingTip = (TextView) messageLayout.findViewById(R.id.loading_tip);

        mapView = (MapView) messageLayout.findViewById(R.id.map_view);
        qqMapSupporter = new QQMapSupporter(getActivity(),mapView,QQMapSupporter.QQ_MAP_TYPE_EXERCISES);

        shownMapView = false;
        return messageLayout;
    }

    @Override
    public void onSelect(int p) {
        Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
        intent.putExtra("position", p);
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
                    .duration(300).playOn(listView);
            if(qqMapSupporter.isMapLoaded) qqMapSupporter.updateExerciseMarkers();

        } else {
            qqMapSupporter.isMapLoaded = false;
            YoYo.with(Techniques.BounceInUp).duration(700).playOn(listView);
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

    public void chooseTag(String tagName) {
        Tag tag = Tag.value(tagName);
        Utils.toastImmediately("选择 " + tagName + " 标签中…");
        Utils.toast(tag.toString());

        loadingTip.setVisibility(View.VISIBLE);
        ExercisePool.getTopicPool().queryPlaceTopicWithTag(-1, -1, tag.v + "", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loadingTip.setText("加载失败，点击标签重试");
            }

            @Override
            public void onResponse(String response, int id) {
                loadingTip.setVisibility(View.GONE);
                Log.d("Wego", response);
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

    public void autoSelect() {
        if (autoSelect) {
            autoSelect = false;
            tagGroup.post(new Runnable() {
                @Override
                public void run() {
                    tagGroup.getChildAt(0).performClick();
                }
            });
        }
    }

    public void setViewPager(ViewPager viewPager) {
        wegoRelativeLayout.setViewPager(viewPager);
    }
}
