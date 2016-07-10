package com.mini_proj.annetao.wego.util;

/**
 * Created by huangweiping on 16/7/9.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Window;

import com.github.johnpersano.supertoasts.SuperToast;
import com.mini_proj.annetao.wego.MyApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 工具单例类
 */
public class Utils {

    /**
     * MIUI下可以把状态栏的字体颜色改一下，以免看不清
     * 在API23或以上，在style里面进行了设置
     *
     * @param activity
     * @param darkMode
     * @return
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }

    private static String lastToast = "";

    /**
     * Toast通用类，会等上一次的toast消失再发送
     * @param text
     */
    public static void toast(String text) {
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(MyApplication.getAppContext());
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.RED);
        superToast.show();
    }

    /**
     * Toast通用类，立刻发送，如果有上一次的toast，让其消失
     * @param text
     */
    public static void toastImmediately(String text) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(MyApplication.getAppContext());
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.RED);
        superToast.show();
    }

    public static int dp2px(float dp){
        Resources resources = MyApplication.getAppContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }
}
