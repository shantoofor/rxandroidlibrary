package com.shantoo.rxandroid;

import android.content.Context;

public class RxAndroid {

    private static Context mContext;
    private static RxAndroid rxAndroid;

    private RxAndroid() {}

    /**
     * 单一实例
     */
    public static RxAndroid init(Context context) {
        mContext = context;
        if (rxAndroid == null) {
            synchronized (RxAndroid.class) {
                rxAndroid = new RxAndroid();
            }
        }
        return rxAndroid;
    }

    public static Context getContext() {
        return mContext;
    }
}
