package com.hirain.hirain;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class MyApp  extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        //初始化  mmkv
        MMKV.initialize(this);

    }
}
