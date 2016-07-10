package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import java.lang.ref.SoftReference;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private int[] titles = new int[]{R.id.home_title, R.id.discovery_title, R.id.subscribe_title, R.id.message_title, R.id.me_title};
    private int lastTitlePosition = 0;
    private int[] buttons = new int[]{R.id.home_toggle_view, R.id.discovery_toggle_view, R.id.subscribe};
    private int lastButtonPosition = 0;

    private SoftReference<FragmentHome> fragmentHomeSoftReference;
    private SoftReference<FragmentDiscovery> fragmentDiscoverySoftReference;
    private SoftReference<FragmentSubscribe> fragmentSubscribeSoftReference;

    private View homeToggleViewButton;
    private View discoveryToggleViewButton;
    private View subscribeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findView(R.id.view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (position == lastTitlePosition) return;
                YoYo.with(Techniques.FadeOutUp)
                        .duration(300)
                        .playOn(findViewById(titles[lastTitlePosition]));
                YoYo.with(Techniques.BounceInUp)
                        .duration(700)
                        .withListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                findView(titles[position]).setVisibility(View.VISIBLE);
                            }
                        })
                        .playOn(findViewById(titles[position]));

                if (position < 3) {
                    if (lastButtonPosition < 3) YoYo.with(Techniques.FadeOutRight).duration(300).playOn(findViewById(buttons[lastButtonPosition]));
                    YoYo.with(Techniques.BounceInUp)
                            .duration(700)
                            .withListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    findView(buttons[position]).bringToFront();
                                    findView(buttons[position]).setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            })
                            .playOn(findViewById(buttons[position]));
                } else {
                    if (lastButtonPosition < 3) YoYo.with(Techniques.FadeOutRight).duration(300).playOn(findViewById(buttons[lastButtonPosition]));
                }
                lastTitlePosition = position;
                lastButtonPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        fragmentHomeSoftReference = new SoftReference<>(new FragmentHome());
                        return fragmentHomeSoftReference.get();
                    case 1:
                        fragmentDiscoverySoftReference = new SoftReference<>(new FragmentDiscovery());
                        return fragmentDiscoverySoftReference.get();
                    case 2:
                        fragmentSubscribeSoftReference = new SoftReference<>(new FragmentSubscribe());
                        return fragmentSubscribeSoftReference.get();
                    case 3: return new FragmentMessage();
                    case 4: return new FragmentMe();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        findView(R.id.tab_home).setOnClickListener(this);
        findView(R.id.tab_discovery).setOnClickListener(this);
        findView(R.id.tab_subscribe).setOnClickListener(this);
        findView(R.id.tab_message).setOnClickListener(this);
        findView(R.id.tab_me).setOnClickListener(this);
        homeToggleViewButton = findView(R.id.home_toggle_view);
        homeToggleViewButton.setOnClickListener(this);
        discoveryToggleViewButton = findView(R.id.discovery_toggle_view);
        discoveryToggleViewButton.setOnClickListener(this);
        subscribeButton = findView(R.id.subscribe);
        subscribeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab_discovery:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tab_subscribe:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tab_message:
                viewPager.setCurrentItem(3, true);
                break;
            case R.id.tab_me:
                viewPager.setCurrentItem(4, true);
                break;
            case R.id.home_toggle_view:
                toggleView();
                break;
            case R.id.discovery_toggle_view:
                toggleView();
                break;
            case R.id.subscribe:
                subScribe();
                break;
        }
    }

    private void toggleView() {
        if (viewPager.getCurrentItem() == 0 && fragmentHomeSoftReference != null && fragmentHomeSoftReference.get() != null) fragmentHomeSoftReference.get().toggleView();
        if (viewPager.getCurrentItem() == 1 && fragmentDiscoverySoftReference != null && fragmentDiscoverySoftReference.get() != null) fragmentDiscoverySoftReference.get().toggleView();
    }

    private void subScribe() {
        if (viewPager.getCurrentItem() == 2 && fragmentSubscribeSoftReference != null && fragmentSubscribeSoftReference.get() != null) fragmentSubscribeSoftReference.get().subscribe();
    }
}
