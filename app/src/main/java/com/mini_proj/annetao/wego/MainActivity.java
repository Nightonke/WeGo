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

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private int[] titles = new int[]{R.id.home_title, R.id.discovery_title, R.id.subscribe_title, R.id.message_title, R.id.me_title};
    private int lastTitlePosition = 0;

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
                                findViewById(titles[position]).setVisibility(View.VISIBLE);
                            }
                        })
                        .playOn(findViewById(titles[position]));
                lastTitlePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return new FragmentHome();
                    case 1: return new FragmentDiscovery();
                    case 2: return new FragmentSubscribe();
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
        }
    }
}
