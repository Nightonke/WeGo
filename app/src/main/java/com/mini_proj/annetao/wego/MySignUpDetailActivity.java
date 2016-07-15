package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MySignUpDetailActivity extends BaseActivity implements TitleLayout.OnTitleActionListener, View.OnClickListener {

    private TitleLayout titleLayout;
    private ScrollView scrollView;
    private KenBurnsView kenBurnsView;
    private TextView title;
    private TextView time;
    private View mapButton;
    private TextView average;
    private TextView people;
    private TextView detail;
    private TextView location;
    private TextView map;
    private ExpandedListView expandedListView;
    private LinearLayout peopleLayout;
    private String tagId = "-1";
    private MaterialDialog dialog;

    private Exercise exercise;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign_up_detail);

        position = getIntent().getIntExtra("position", -1);
        tagId = getIntent().getStringExtra("tag_id");
        if (position == -1) {
            finish();
            return;
        }
        exercise = ExercisePool.getTopicPool().getMySignUpExercises().get(position);

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
        peopleLayout = findView(R.id.people_layout);
        peopleLayout.setOnClickListener(this);
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
        new MaterialDialog.Builder(mContext)
                .title("取消参加活动")
                .content("确定取消参加 " + exercise.getName() + " 吗？")
                .positiveText("确定")
                .negativeText("取消")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which.equals(DialogAction.POSITIVE)) {
                            UserInf.getUserInf().deleteUserAttend(exercise.getId() + "", new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.d("Wego", e.toString());
                                    dialog.dismiss();
                                    new MaterialDialog.Builder(mContext)
                                            .title("取消失败")
                                            .content("取消参加活动 " + exercise.getName() + " 失败，网络异常。")
                                            .positiveText("确定")
                                            .show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.d("Wego", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String result = jsonObject.getString("result");
                                        if ("200".equals(result)) {
                                            ExercisePool.getTopicPool().deleteMySignUpExercises(exercise);
                                            dialog.dismiss();
                                            new MaterialDialog.Builder(mContext)
                                                    .title("取消成功")
                                                    .content("取消参加活动 " + exercise.getName() + " 成功。")
                                                    .positiveText("确定")
                                                    .onAny(new MaterialDialog.SingleButtonCallback() {
                                                        @Override
                                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                            dialog.dismiss();
                                                            finish();
                                                        }
                                                    })
                                                    .show();
                                        } else {
                                            dialog.dismiss();
                                            new MaterialDialog.Builder(mContext)
                                                    .title("取消失败")
                                                    .content("取消参加活动 " + exercise.getName() + " 失败，网络异常。")
                                                    .positiveText("确定")
                                                    .show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                })
                .show();
    }

    private void openMap() {
        Intent intent = new Intent(this, TencentMapActivity.class);
        intent.putExtra("map_type", QQMapSupporter.QQ_MAP_TYPE_ONE_EXERCISE);
        intent.putExtra("tag_id", tagId);
        intent.putExtra("position",position);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        dialog = new MaterialDialog.Builder(mContext)
                .title("获取参与人信息中")
                .content("请稍候...")
                .cancelable(false)
                .progress(true, 0)
                .show();
        switch(v.getId()){
            case R.id.people_layout:
                exercise.queryAllAttend(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (mContext == null) return;
                        if (dialog != null) dialog.dismiss();
                        dialog = new MaterialDialog.Builder(mContext)
                                .title("获取活动失败")
                                .content("请稍后再试")
                                .cancelable(false)
                                .positiveText("确定")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        finish();
                                    }
                                })
                                .show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e("Wego",response.toString());
                            if (mContext == null) return;
                            if (dialog != null) dialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("data")) {
                                JSONArray userJa = jsonObject.getJSONArray("data");
                                String[] showStr = new String[userJa.length()];

                                for (int i = 0; i < userJa.length(); i++) {
                                    showStr[i] = "昵称：" + userJa.getJSONObject(i).getString("nickname")
                                            + "  联系方式：" + userJa.getJSONObject(i).getString("phone");

                                }


                                dialog = new MaterialDialog.Builder(mContext)
                                        .title("参与人信息")
                                        .items(showStr)
                                        .positiveText("确定")
                                        .show();
                            }
                            else{
                                dialog = new MaterialDialog.Builder(mContext)
                                        .title("参与人信息")
                                        .content("暂无参与人")
                                        .positiveText("确定")
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
