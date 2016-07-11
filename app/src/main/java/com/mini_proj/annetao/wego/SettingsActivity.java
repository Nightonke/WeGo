package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends BaseActivity
        implements
        TitleLayout.OnTitleActionListener,
        View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


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
        // save to server
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthday_layout:

                break;
            case R.id.manager:

                break;
        }
    }
}
