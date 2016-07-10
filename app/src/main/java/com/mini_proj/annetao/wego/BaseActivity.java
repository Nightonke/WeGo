package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mini_proj.annetao.wego.util.Utils;

/**
 * Created by huangweiping on 16/7/9.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setMiuiStatusBarDarkMode(this, false);
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

}
