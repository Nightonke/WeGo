package com.mini_proj.annetao.wego;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mini_proj.annetao.wego.util.Utils;

/**
 * Created by huangweiping on 16/7/9.
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setMiuiStatusBarDarkMode(this, false);
        mContext = this;
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

}
