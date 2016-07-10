package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentHome extends Fragment {

    private boolean shownMapView = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_home, container, false);



        return messageLayout;
    }

    public void toggleView() {
        shownMapView = !shownMapView;
        if (shownMapView) {
            YoYo.with(Techniques.BounceInUp).duration(700).playOn(getActivity().findViewById(R.id.map_view));
            YoYo.with(Techniques.FadeOutUp)
                    .withListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            getActivity().findViewById(R.id.map_view).setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            getActivity().findViewById(R.id.list_view).setVisibility(View.GONE);
                        }
                    })
                    .duration(300).playOn(getActivity().findViewById(R.id.list_view));
        } else {
            YoYo.with(Techniques.BounceInUp).duration(700).playOn(getActivity().findViewById(R.id.list_view));
            YoYo.with(Techniques.FadeOutUp)
                    .withListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            getActivity().findViewById(R.id.list_view).setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            getActivity().findViewById(R.id.map_view).setVisibility(View.GONE);
                        }
                    })
                    .duration(300).playOn(getActivity().findViewById(R.id.map_view));
        }
    }

}
