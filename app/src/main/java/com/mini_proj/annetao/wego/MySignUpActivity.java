package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mini_proj.annetao.wego.util.Utils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

public class MySignUpActivity extends BaseActivity
        implements
        SwipeRefreshLayout.OnRefreshListener,
        ExerciseAdapter.OnExerciseSelectListener,
        TitleLayout.OnTitleActionListener {

    private TitleLayout title;
    private MaterialDialog dialog;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private SuperRecyclerView listView;
    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign_up);

        title = findView(R.id.title);
        title.setOnTitleActionListener(this);

        listView = findView(R.id.list_view);
        listView.setRefreshListener(this);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        listView.getSwipeToRefresh().setColorSchemeResources(R.color.colorAccent);
        LinearLayoutManager mManager = new LinearLayoutManager(mContext);
        listView.setLayoutManager(mManager);
        adapter = new ExerciseAdapter(this, exercises);
        listView.setAdapter(adapter);

        loadExercise();
    }

    @Override
    public void onRefresh() {
        loadExercise();
    }

    @Override
    public void onSelect(int p) {

    }

    private void loadExercise() {
        dialog = new MaterialDialog.Builder(mContext)
                .title("获取活动中")
                .content("请稍候...")
                .cancelable(false)
                .progress(true, 0)
                .negativeText("取消")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();

        ExercisePool.getTopicPool().queryTopicWithOpenId(User.getInstance().getOpenId(), new StringCallback() {
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
                if (mContext == null) return;
                if (dialog != null) dialog.dismiss();
                Log.d("Wego", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    exercises.clear();
                    for (int i = 0; i < data.length(); i++) {
                        Exercise exercise = new Exercise(data.getJSONObject(i), Exercise.ATTENDENCY_TYPE);
                        exercises.add(exercise);
                    }
                    adapter = new ExerciseAdapter(MySignUpActivity.this, exercises);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void clickTitleBack() {
        finish();
    }

    @Override
    public void doubleClickTitle() {
        listView.getRecyclerView().smoothScrollToPosition(0);
    }

    @Override
    public void clickTitleEdit() {

    }
}
