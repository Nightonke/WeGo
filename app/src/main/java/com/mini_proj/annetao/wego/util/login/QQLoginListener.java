package com.mini_proj.annetao.wego.util.login;

/**
 * Created by Administrator on 16/7/8.
 */
public interface QQLoginListener {

    /**
     * @param result 结果码
     * @param response 成功为JSONObject，失败为UiError
     */
    public void onQQLoginResult(String result,Object response);
}

