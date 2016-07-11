package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentDiscovery extends Fragment {

    private TagGroup tagGroup;
    private RecyclerView recyclerView;
    private boolean shownMapView = false;
    private RecyclerView listView;
    private MapView mapView;
    private QQMapSupporter qqMapSupporter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_discovery, container, false);

        messageLayout.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FilterActivity.class));
            }
        });

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
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);


        mapView = (MapView) messageLayout.findViewById(R.id.map_view);
        qqMapSupporter = new QQMapSupporter(getActivity(),mapView,QQMapSupporter.QQ_MAP_TYPE_EXERCISES);
        qqMapSupporter.initialMapView();

        return messageLayout;
    }

    public void toggleView() {
        shownMapView = !shownMapView;
        if (shownMapView) {
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
            if(qqMapSupporter.isMapLoaded) qqMapSupporter.updateExerciseMarker();

        } else {
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
        Utils.toast(tag.toString());


    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
