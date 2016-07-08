package com.mini_proj.annetao.wego.util.login;

import android.app.Activity;
import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by Administrator on 16/7/8.
 */
public class QQLoginSupporter {
    private Tencent mTencent;
    private QQLoginListener activity;
    public static String QQ_LOGIN_APP_ID = "1105456541";
    public static String QQ_LOGIN_RESULT_COMPLETE = "util.login.qqloginlistener.qqloginresult.complete";
    public static String QQ_LOGIN_RESULT_CANCEL = "util.login.qqloginlistener.qqloginresult.cancel";
    public static String QQ_LOGIN_RESULT_ERROR = "util.login.qqloginlistener.qqloginresult.error";
    public QQLoginSupporter(QQLoginListener activity) {
        this.activity = activity;
        mTencent = Tencent.createInstance(QQ_LOGIN_APP_ID, ((Activity)activity).getApplicationContext());

    }
    public void login(){
        BaseUiListener baseUiListener = new BaseUiListener();
        mTencent.login((Activity) activity,"all",baseUiListener);
    }
    public void logout(){
        mTencent.logout((Activity)activity);
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {

            activity.onQQLoginResult(QQ_LOGIN_RESULT_COMPLETE,response);

            doComplete(response);

        }

        protected void doComplete(Object values) {

        }

        @Override

        public void onError(UiError e) {

            activity.onQQLoginResult(QQ_LOGIN_RESULT_ERROR,e);

        }

        @Override

        public void onCancel() {

            activity.onQQLoginResult(QQ_LOGIN_RESULT_CANCEL,null);

        }

    }
}
