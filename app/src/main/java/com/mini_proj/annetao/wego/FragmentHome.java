package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentHome extends Fragment implements ExerciseAdapter.OnExerciseSelectListener {

    private boolean shownMapView = false;

    private View mapView;
    private SuperRecyclerView listView;
    private ExerciseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (SuperRecyclerView) messageLayout.findViewById(R.id.list_view);
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        adapter = new ExerciseAdapter(this);
        listView.setAdapter(adapter);

        mapView = messageLayout.findViewById(R.id.map_view);

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
}
