package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.mini_proj.annetao.wego.util.Utils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.tencent.tencentmap.mapsdk.maps.MapView;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentHome extends Fragment implements ExerciseAdapter.OnExerciseSelectListener {

    private boolean shownMapView = false;

    private WegoRelativeLayout wegoRelativeLayout;
    private MapView mapView;
    private SuperRecyclerView listView;
    private ExerciseAdapter adapter;
    private QQMapSupporter qqMapSupporter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_home, container, false);

        wegoRelativeLayout = (WegoRelativeLayout) messageLayout.findViewById(R.id.base);
        listView = (SuperRecyclerView) messageLayout.findViewById(R.id.list_view);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        adapter = new ExerciseAdapter(this);
        listView.setAdapter(adapter);

        mapView = (MapView) messageLayout.findViewById(R.id.map_view);
        qqMapSupporter = new QQMapSupporter(getActivity(),mapView,QQMapSupporter.QQ_MAP_TYPE_EXERCISES);
        qqMapSupporter.initialMapView();

        return messageLayout;
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

    public void setViewPager(ViewPager viewPager) {
        wegoRelativeLayout.setViewPager(viewPager);
    }
}
