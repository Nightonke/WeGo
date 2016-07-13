package com.mini_proj.annetao.wego;

import android.support.annotation.NonNull;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MySubscribeActivity extends BaseActivity {

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);

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

            }
        });
    }
}
