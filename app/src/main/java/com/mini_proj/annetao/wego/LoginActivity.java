package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mini_proj.annetao.wego.util.Utils;
import com.mini_proj.annetao.wego.util.login.QQLoginListener;
import com.mini_proj.annetao.wego.util.login.QQLoginSupporter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener {

    private EditText name;
    private EditText password;
    private QQLoginSupporter qs;
    private TextView btn;
    private BaseUiListener loginListener;
    public static String QQ_LOGIN_APP_ID = "1105456541";
    public static String QQ_LOGIN_RESULT_COMPLETE = "util.login.qqloginlistener.qqloginresult.complete";
    public static String QQ_LOGIN_RESULT_CANCEL = "util.login.qqloginlistener.qqloginresult.cancel";
    public static String QQ_LOGIN_RESULT_ERROR = "util.login.qqloginlistener.qqloginresult.error";
    Tencent mTencent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        qs = new QQLoginSupporter(this);
        btn = findView(R.id.qq);
        if(User.getInstance().isLogin()){
            btn.setText("欢迎，请稍候...");
            weGoLogin();
        }
        else{
            findView(R.id.qq).setOnClickListener(this);
        }

        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.Tada).duration(700).playOn(findView(R.id.wego));
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qq:
                qqLogin();
                break;
        }
    }

    private void normalLogin() {
    }

    private void qqLogin() {

        btn.setText("加载中，请稍候...");


        mTencent = Tencent.createInstance(QQ_LOGIN_APP_ID, getApplicationContext());
        loginListener = new BaseUiListener();
        mTencent.login(this,"all",loginListener);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * QQ登陆回调
     * @param result
     * @param response
     */
    public void onQQLoginResult(String result, Object response) {
        if(result.equals(QQLoginSupporter.QQ_LOGIN_RESULT_COMPLETE)) {
            //TODO 存储登录数据
            JSONObject jsonResponse = (JSONObject) response;
            try {
                User.getInstance().setOpenId(jsonResponse.getString(Constants.PARAM_OPEN_ID));
                User.getInstance().setToken(jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            initOpenidAndToken((JSONObject) response);
            updateUserInfo();


        }
        else{
            Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT);
        }

    }

    public  void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    private void updateUserInfo() {

        IUiListener listener = new IUiListener() {

            @Override
            public void onError(UiError e) {
                Toast.makeText(LoginActivity.this,"获取头像失败",Toast.LENGTH_SHORT).show();
                weGoLogin();

            }

            /**
             * 请求QQ昵称和头像的回调
             */
            @Override
            public void onComplete(final Object response) {
                JSONObject json = (JSONObject)response;
                Log.e("avatorurl",response.toString());
                if(json.has("figureurl")) {
                    try {
                        User.getInstance().setAvatorUrl(json.getString("figureurl_qq_2"));
                        if(User.getInstance().getName()==null||User.getInstance().getName().equals(""))
                            User.getInstance().setName(json.getString("nickname"));
                        if(User.getInstance().getGender()==-1) {
                            String genderStr = json.getString("gender");
                            if (genderStr.equals("男"))
                                User.getInstance().setGender(User.GENDER_MALE);
                            else if (genderStr.equals("女"))
                                User.getInstance().setGender(User.GENDER_FEMALE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                weGoLogin();


            }

            @Override
            public void onCancel() {

            }
        };
        UserInfo mInfo = new UserInfo(this, mTencent.getQQToken());
        mInfo.getUserInfo(listener);


    }
    private void goToRegistry(){
        startActivity(new Intent(mContext, UserInformationActivity.class));
    }
    private void weGoLogin() {
        UserInf.getUserInf().doLogin(User.getInstance().getOpenId(),new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

            }

            /**
             * 后台登陆成功的回调
             */
            @Override
            public void onResponse(String response, int id) {
                try {
                    Log.d("Wego", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("result").equals(NetworkTools.RESULT_FAILED))
                        goToRegistry();
                    else {
                        JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                        Log.d("Wego_resulttojson", data.toString());
                        User.getInstance().updateByJsonResult(data,jsonObject);
                        User.getInstance().setLogin(true);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("Wego", response + " " + id);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Log.e("openid",response.toString());
            onQQLoginResult(QQ_LOGIN_RESULT_COMPLETE,response);

            doComplete(response);

        }

        protected void doComplete(Object values) {

        }

        @Override

        public void onError(UiError e) {

            onQQLoginResult(QQ_LOGIN_RESULT_ERROR,e);

        }

        @Override

        public void onCancel() {

            onQQLoginResult(QQ_LOGIN_RESULT_CANCEL,null);

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 官方文档没没没没没没没没没没没这句代码, 但是很很很很很很重要, 不然不会回调!
        btn.setText("欢迎，请稍候...");
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, loginListener);
            }
        }
    }
}
