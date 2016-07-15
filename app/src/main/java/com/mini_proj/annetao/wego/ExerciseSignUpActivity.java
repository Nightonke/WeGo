package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mini_proj.annetao.wego.util.Utils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import okhttp3.Call;

public class ExerciseSignUpActivity extends BaseActivity
        implements
        TitleLayout.OnTitleActionListener,
        View.OnClickListener {

    private TitleLayout titleLayout;
    private TextView name;
    private TextView phone;
    private TextView remark;
    private TextView sign_in;
    private int exercise_id;

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sign_up);
        exercise_id=getIntent().getIntExtra("exercise_id",0);
        Log.d("Wego,exercise_id",exercise_id+"");
        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        name = findView(R.id.name);
        phone=findView(R.id.phone);

        remark=findView(R.id.remark);
        sign_in=findView(R.id.sign_in);
        name.setText("");
        findView(R.id.name_layout).setOnClickListener(this);
        findView(R.id.phone_layout).setOnClickListener(this);
        findView(R.id.remark_layout).setOnClickListener(this);
        findView(R.id.sign_in).setOnClickListener(this);

    }

    @Override
    public void clickTitleBack() {
        finish();
    }

    @Override
    public void doubleClickTitle() {

    }

    @Override
    public void clickTitleEdit() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_layout:
                new MaterialDialog.Builder(this)
                        .title("姓名")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                name.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("请输入姓名", name.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.phone_layout:
                new MaterialDialog.Builder(this)
                        .title("联系方式")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                phone.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("联系方式", phone.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.remark_layout:
                new MaterialDialog.Builder(this)
                        .title("备注")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                remark.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("备注", remark.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.sign_in:
                if (phone.getText().toString().length() != 11 || phone.getText().toString().charAt(0) != '1') {
                    Utils.toastImmediately("电话号码不合法");
                } else {
                    dialog = new MaterialDialog.Builder(mContext)
                            .title("报名中")
                            .content("请稍候...")
                            .cancelable(false)
                            .progress(true, 0)
                            .show();
                    UserInf.getUserInf().addUserAttend("" + exercise_id, name.getText() + "", phone.getText() + "", new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("WeGo", e.toString());
                            dialog = new MaterialDialog.Builder(mContext)
                                    .title("报名失败")
                                    .content("报名失败，网络异常")
                                    .positiveText("确定")
                                    .show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (dialog != null) dialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("result").equals("400")) {
                                    Utils.toastImmediately("报名失败，网络异常");
                                } else {
                                    Utils.toastImmediately("报名成功");
                                    finish();
                                }
                            } catch (JSONException e) {
                                Log.e("WeGo", e.toString());
                            }
                        }
                    });
                }
        }
    }
}
