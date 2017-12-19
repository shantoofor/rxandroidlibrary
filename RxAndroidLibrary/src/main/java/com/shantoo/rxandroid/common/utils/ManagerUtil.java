package com.shantoo.rxandroid.common.utils;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import com.shantoo.rxandroid.RxAndroid;

public class ManagerUtil {

    private static Context getContext(){
       return RxAndroid.getContext();
    }

    public static WindowManager getWindowManager(){
       return (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public static TelephonyManager getTelephonyManager(){
        return (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static PackageManager getPackageManager(){
        return getContext().getPackageManager();
    }

    public static WifiManager getWifiManager(){
        return (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static KeyguardManager getKeyguardManager(){
        return (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
    }
}
