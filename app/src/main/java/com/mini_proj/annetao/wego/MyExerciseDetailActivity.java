package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MyExerciseDetailActivity extends BaseActivity implements TitleLayout.OnTitleActionListener, View.OnClickListener {

    private TitleLayout titleLayout;
    private ScrollView scrollView;
    private KenBurnsView kenBurnsView;
    private TextView title;
    private TextView time;
    private View mapButton;
    private TextView average;
    private TextView people;
    private TextView peopleDetail;
    private TextView detail;
    private TextView location;
    private TextView map;
    private ExpandedListView expandedListView;
    private LinearLayout peopleDetailLayout;
    private String tagId = "-2";
    private MaterialDialog dialog;

    private Exercise exercise;
    private int position = -1;

    private Handler recoveryPeopleDetailTextHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(peopleDetail!=null)
                peopleDetail.setText("点击查看");

            super.handleMessage(msg);
        }
    };

    private MaterialDialog.SingleButtonCallback recoveryPeopleDetailTextCallback
            = new MaterialDialog.SingleButtonCallback(){
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Message msg = new Message();
            recoveryPeopleDetailTextHandler.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exercise_detail);

        position = getIntent().getIntExtra("position", -1);
        if (position == -1) {
            finish();
            return;
        }
        exercise = ExercisePool.getTopicPool().getMySubscribeExercises().get(position);

        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        scrollView = findView(R.id.scroll_view);
        kenBurnsView = findView(R.id.image);
        Picasso.with(mContext).load(exercise.getPic_store()).into(kenBurnsView);
        title = findView(R.id.title);
        title.setText(exercise.getName());
        time = findView(R.id.time);
        time.setText(exercise.getStart_time() + " ~ " + exercise.getEnd_time());
        map = findView(R.id.map);
        map.setText(exercise.getAddress());
        map.setSelected(true);
        mapButton = findView(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        average = findView(R.id.average);
        average.setText(exercise.getAvg_cost()+"");
        people = findView(R.id.people);
        people.setText(exercise.getAttendencyNum()+"/"+exercise.getMinNum());
        peopleDetailLayout = findView(R.id.people_detail_layout);
        peopleDetailLayout.setOnClickListener(this);
        peopleDetail = findView(R.id.people_detail);
        detail = findView(R.id.detail);
        detail.setText(exercise.getDescription());
        location=findView(R.id.map);

    }

    @Override
    public void clickTitleBack() {
        finish();
    }

    @Override
    public void doubleClickTitle() {
        scrollView.smoothScrollBy(0, 0);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void clickTitleEdit() {

    }

    private void openMap() {
        Intent intent = new Intent(this, TencentMapActivity.class);
        intent.putExtra("map_type", QQMapSupporter.QQ_MAP_TYPE_ONE_EXERCISE);
        Log.e("wego_tagid",tagId);
        intent.putExtra("tag_id", tagId);
        intent.putExtra("position",position);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.people_detail_layout:
                peopleDetail.setText("加载中...");
                exercise.queryAllAttend(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        recoveryPeopleDetailTextHandler.sendMessage(new Message());
                        if (mContext == null) return;
                        if (dialog != null) dialog.dismiss();
                        dialog = new MaterialDialog.Builder(mContext)
                                .title("获取活动失败")
                                .content("请稍后再试")
                                .cancelable(false)
                                .positiveText("确定")
                                .onPositive(recoveryPeopleDetailTextCallback)
                                .show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        recoveryPeopleDetailTextHandler.sendMessage(new Message());
                        try {
                            Log.e("Wego",response.toString());
                            if (mContext == null) return;
                            if (dialog != null) dialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("data")) {
                                JSONArray userJa = jsonObject.getJSONArray("data");
                                String[] showStr = new String[userJa.length()];

                                for (int i = 0; i < userJa.length(); i++) {
                                    showStr[i] = "姓名：" + userJa.getJSONObject(i).getString("nickname")
                                            + " 电话：" + userJa.getJSONObject(i).getString("phone");

                                }


                                dialog = new MaterialDialog.Builder(mContext)
                                        .title("参与人信息")
                                        .items(showStr)
                                        .positiveText("确定")
                                        .onPositive(recoveryPeopleDetailTextCallback)
                                        .show();
                            }
                            else{
                                dialog = new MaterialDialog.Builder(mContext)
                                        .title("参与人信息")
                                        .content("暂无参与人")
                                        .positiveText("确定")
                                        .onPositive(recoveryPeopleDetailTextCallback)
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


                break;
        }
    }
}
