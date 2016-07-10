package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mini_proj.annetao.wego.util.Utils;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener {

    private EditText name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView(R.id.name_layout).setOnClickListener(this);
        name = findView(R.id.name);
        findView(R.id.clear_name).setOnClickListener(this);
        findView(R.id.password_layout).setOnClickListener(this);
        password = findView(R.id.password);
        findView(R.id.clear_password).setOnClickListener(this);
        findView(R.id.login).setOnClickListener(this);
        findView(R.id.qq).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_layout:
                name.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showKeyboard(name, LoginActivity.this);
                    }
                });
                break;
            case R.id.clear_name:
                name.setText("");
                onClick(findView(R.id.name_layout));
                break;
            case R.id.password_layout:
                password.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showKeyboard(password, LoginActivity.this);
                    }
                });
                break;
            case R.id.clear_password:
                password.setText("");
                onClick(findView(R.id.password_layout));
                break;
            case R.id.login:

                break;
            case R.id.qq:

                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
