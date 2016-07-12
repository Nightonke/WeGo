package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mini_proj.annetao.wego.util.Utils;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private int[] titles = new int[]{R.id.home_title, R.id.discovery_title, R.id.subscribe_title, R.id.message_title, R.id.me_title};
    private int lastTitlePosition = 0;
    private int[] buttons = new int[]{R.id.home_toggle_view, R.id.discovery_toggle_view, R.id.subscribe};
    private int lastButtonPosition = 0;
    private TextView[] tabTexts = new TextView[5];
    private ImageView[] tabImages = new ImageView[10];


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

        tabTexts[0] = findView(R.id.icon_home_text);
        tabTexts[1] = findView(R.id.icon_discovery_text);
        tabTexts[2] = findView(R.id.icon_subscribe_text);
        tabTexts[3] = findView(R.id.icon_message_text);
        tabTexts[4] = findView(R.id.icon_me_text);
        tabImages[0] = findView(R.id.icon_home_checked);
        tabImages[1] = findView(R.id.icon_home_unchecked);
        tabImages[2] = findView(R.id.icon_discovery_checked);
        tabImages[3] = findView(R.id.icon_discovery_unchecked);
        tabImages[4] = findView(R.id.icon_subscribe_checked);
        tabImages[5] = findView(R.id.icon_subscribe_unchecked);
        tabImages[6] = findView(R.id.icon_message_checked);
        tabImages[7] = findView(R.id.icon_message_unchecked);
        tabImages[8] = findView(R.id.icon_me_checked);
        tabImages[9] = findView(R.id.icon_me_unchecked);

        viewPager = findView(R.id.view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float dis = position + positionOffset;
                int w = ContextCompat.getColor(mContext, R.color.gray);
                int y = ContextCompat.getColor(mContext, R.color.orange);

                if (dis <= 0) {
                    tabTexts[0].setTextColor(y);
                    tabTexts[1].setTextColor(w);
                    tabTexts[2].setTextColor(w);
                    tabTexts[3].setTextColor(w);
                    tabTexts[4].setTextColor(w);
                    tabImages[0].setAlpha(1.0f);
                    tabImages[2].setAlpha(0.0f);
                    tabImages[4].setAlpha(0.0f);
                    tabImages[6].setAlpha(0.0f);
                    tabImages[8].setAlpha(0.0f);
                } else if (dis <= 1) {
                    tabTexts[0].setTextColor(Utils.changingColor(w, y, 1 - dis));
                    tabTexts[1].setTextColor(Utils.changingColor(w, y, dis));
                    tabTexts[2].setTextColor(w);
                    tabTexts[3].setTextColor(w);
                    tabTexts[4].setTextColor(w);
                    tabImages[0].setAlpha(1.0f - dis);
                    tabImages[2].setAlpha(dis);
                    tabImages[4].setAlpha(0.0f);
                    tabImages[6].setAlpha(0.0f);
                    tabImages[8].setAlpha(0.0f);
                } else if (dis <= 2) {
                    tabTexts[0].setTextColor(w);
                    tabTexts[1].setTextColor(Utils.changingColor(w, y, 1 - (dis - 1)));
                    tabTexts[2].setTextColor(Utils.changingColor(w, y, dis - 1));
                    tabTexts[3].setTextColor(w);
                    tabTexts[4].setTextColor(w);
                    tabImages[0].setAlpha(0.0f);
                    tabImages[2].setAlpha(1 - (dis - 1));
                    tabImages[4].setAlpha(dis - 1);
                    tabImages[6].setAlpha(0.0f);
                    tabImages[8].setAlpha(0.0f);
                } else if (dis <= 3) {
                    tabTexts[0].setTextColor(w);
                    tabTexts[1].setTextColor(w);
                    tabTexts[2].setTextColor(Utils.changingColor(w, y, 1 - (dis - 2)));
                    tabTexts[3].setTextColor(Utils.changingColor(w, y, dis - 2));
                    tabTexts[4].setTextColor(w);
                    tabImages[0].setAlpha(0.0f);
                    tabImages[2].setAlpha(0.0f);
                    tabImages[4].setAlpha(1 - (dis - 2));
                    tabImages[6].setAlpha(dis - 2);
                    tabImages[8].setAlpha(0.0f);
                } else if (dis <= 4) {
                    tabTexts[0].setTextColor(w);
                    tabTexts[1].setTextColor(w);
                    tabTexts[2].setTextColor(w);
                    tabTexts[3].setTextColor(Utils.changingColor(w, y, 1 - (dis - 3)));
                    tabTexts[4].setTextColor(Utils.changingColor(w, y, dis - 3));
                    tabImages[0].setAlpha(0.0f);
                    tabImages[2].setAlpha(0.0f);
                    tabImages[4].setAlpha(0.0f);
                    tabImages[6].setAlpha(1 - (dis - 3));
                    tabImages[8].setAlpha(dis - 3);
                }
            }

            @Override
            public void onPageSelected(final int position) {
                if (position == lastTitlePosition) return;
                YoYo.with(Techniques.FadeOutUp)
                        .duration(300)
                        .withListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                super.onAnimationCancel(animation);
                                Log.d("Wego", "Cancel");
                                findView(titles[lastTitlePosition]).setVisibility(View.GONE);
                            }
                        })
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

                if (viewPager.getCurrentItem() == 0 && fragmentHomeSoftReference != null && fragmentHomeSoftReference.get() != null) fragmentHomeSoftReference.get().setViewPager(viewPager);
                if (viewPager.getCurrentItem() == 1 && fragmentDiscoverySoftReference != null && fragmentDiscoverySoftReference.get() != null) fragmentDiscoverySoftReference.get().setViewPager(viewPager);

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
                        viewPager.post(new Runnable() {
                            @Override
                            public void run() {
                                fragmentHomeSoftReference.get().setViewPager(viewPager);
                            }
                        });
                        return fragmentHomeSoftReference.get();
                    case 1:
                        fragmentDiscoverySoftReference = new SoftReference<>(new FragmentDiscovery());
                        viewPager.post(new Runnable() {
                            @Override
                            public void run() {
                                fragmentDiscoverySoftReference.get().setViewPager(viewPager);
                            }
                        });
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
    public void onBackPressed() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!User.getInstance().isLogin()) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);// 跳到登录页


        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                setPhoto(photos);
            }
        }

        if (resultCode == RESULT_OK && requestCode == QQMapSupporter.SUBSCRIBE_ADDRESS_REQUEST_CODE) {
            if (data != null) {
                String wegoLocStr = data.getStringExtra("wego_location_str");
                if(wegoLocStr != null)
                    setAddress(wegoLocStr);
            }
        }
    }

    private void toggleView() {
        if (viewPager.getCurrentItem() == 0 && fragmentHomeSoftReference != null && fragmentHomeSoftReference.get() != null) {
            fragmentHomeSoftReference.get().toggleView();
            fragmentHomeSoftReference.get().setViewPager(viewPager);
        }
        if (viewPager.getCurrentItem() == 1 && fragmentDiscoverySoftReference != null && fragmentDiscoverySoftReference.get() != null) {
            fragmentDiscoverySoftReference.get().toggleView();
            fragmentDiscoverySoftReference.get().setViewPager(viewPager);
        }
    }

    private void subScribe() {
        if (viewPager.getCurrentItem() == 2 && fragmentSubscribeSoftReference != null && fragmentSubscribeSoftReference.get() != null) fragmentSubscribeSoftReference.get().subscribe();
    }

    private void setPhoto(ArrayList<String> paths) {
        if (viewPager.getCurrentItem() == 2 && fragmentSubscribeSoftReference != null && fragmentSubscribeSoftReference.get() != null) fragmentSubscribeSoftReference.get().setPhoto(paths);
    }
    private void setAddress(String wegolocationStr) {
        if (viewPager.getCurrentItem() == 2 && fragmentSubscribeSoftReference != null && fragmentSubscribeSoftReference.get() != null) fragmentSubscribeSoftReference.get().setAddress(wegolocationStr);
    }


}
