package com.shantoo.rxandroid.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shantoo.rxandroid.RxAndroid;

import java.util.Map;

/**
 * SharedPreferences工具类(单例模式)
 */
public class SPUtil {
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SPUtil instance;
    private static final String SP_NAME = "common";

    private SPUtil() {
        this.context = RxAndroid.getContext();
        sp = this.context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        editor = sp.edit();
    }

    public static SPUtil getInstance() {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil();
                    return instance;
                }
            }
        }
        return instance;
    }

    public void putBoolean(String key, Boolean value) {
        if (key == null) {
            return;
        }
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        if (key == null) {
            return;
        }
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        if (key == null) {
            return;
        }
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public String getString(String key) {
        return sp.getString(key, null);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public void remove(String key) {
        sp.edit().remove(key).commit();
    }
}