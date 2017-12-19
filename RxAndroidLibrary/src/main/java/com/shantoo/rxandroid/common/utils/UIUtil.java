package com.shantoo.rxandroid.common.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.shantoo.rxandroid.RxAndroid;

public class UIUtil {

    private static Toast mToast;

    private static Context getContext() {
        return RxAndroid.getContext();
    }

    public static View createView(@LayoutRes int resource) {
        return LayoutInflater.from(getContext()).inflate(resource, null);
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), "", duration);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public static int dp2px(float dpValue) {
        return dip2px(dpValue);
    }

    public static int px2dp(float dpValue) {
        return px2dip(dpValue);
    }

    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到屏幕的高
     */
    public static int getScreenHeight() {
        int height = ManagerUtil.getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 得到屏幕的宽
     */
    public static int getScreenWidth() {
        int width = ManagerUtil.getWindowManager().getDefaultDisplay().getWidth();
        return width;
    }
}
