package com.hirain.hirain.utils;

import android.os.Parcelable;
import android.util.ArraySet;

import com.hirain.hirain.bean.CustomMode;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MMkvUtils {
    public static String MODE ="mode";
    private static MMkvUtils mInstance;
    private  MMKV mmkv;

    private MMkvUtils() {
        mmkv = MMKV.defaultMMKV();
    }

    public static MMkvUtils getmInstance() {
        if (mInstance == null) {
            synchronized (MMkvUtils.class) {
                if (mInstance == null) {
                    mInstance = new MMkvUtils();
                }
            }
        }
        return mInstance;
    }

    public void encodeString(String key,String value){
        mmkv.encode(key,value);
    }
    public void encodeInt(String key,int value){
        mmkv.encode(key,value);
    }
    public void encodeParcelable(String key, Parcelable value){
        mmkv.encode(key,value);

    }
    public void encodeSet(String key, Set value){
        mmkv.encode(key,value);

    }
    public Set<String> decodeSet(String key){
        Set<String> strings = mmkv.decodeStringSet(key);
        if(strings==null){
            strings=new HashSet<>();
        }
        return strings;

    }
    public String decodeString(String key){
        return mmkv.decodeString(key);

    }
    public int decodeInt(String key,int value){
        return mmkv.decodeInt(key);
    }
    public CustomMode decodeParcelable(String key){
        return mmkv.decodeParcelable(key,CustomMode.class);
    }
}
