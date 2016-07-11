package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class ExerciseSignUpActivity extends BaseActivity
        implements
        TitleLayout.OnTitleActionListener,
        View.OnClickListener {

    private TitleLayout titleLayout;
    private TextView name;
    private TextView phone;
    private TextView remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sign_up);

        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        name = findView(R.id.name);
        name.setText("");
        findView(R.id.name_layout).setOnClickListener(this);

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
                        .input("您的姓名", name.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.phone_layout:

                break;
            case R.id.remark_layout:

                break;
        }
    }
}
