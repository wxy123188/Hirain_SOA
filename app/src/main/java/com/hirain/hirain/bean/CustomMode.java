package com.hirain.hirain.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

//存储自定义模式实体类
@SuppressLint("ParcelCreator")
public class CustomMode implements Parcelable {
    //音量
    public int volume;
    //座椅
    public int chairMode;   //0,1,2,3
    //灯光  模式
    public int lightMode;    //0,1,2,3
    //氛围灯
    public int atmosphereLamp;    //0,1,2,3
    //后视镜状态  开启0 折叠1
    public int  rmType=1;
    //后视镜的选择 0 左 1右
    public int rmStates;
    //左后视镜的状态
    public int rmLeft;
    //右后视镜状态
    public int rmRight;

    public CustomMode(Parcel source) {
        volume = source.readInt();
        chairMode = source.readInt();
        lightMode = source.readInt();
        atmosphereLamp = source.readInt();
        rmType = source.readInt();
        rmStates = source.readInt();
        rmRight = source.readInt();
        rmLeft = source.readInt();
    }

    public CustomMode(int volume, int chairMode, int lightMode, int atmosphereLamp, int rmType, int rmStates, int rmLeft, int rmRight) {
        this.volume = volume;
        this.chairMode = chairMode;
        this.lightMode = lightMode;
        this.atmosphereLamp = atmosphereLamp;
        this.rmType = rmType;
        this.rmStates = rmStates;
        this.rmLeft = rmLeft;
        this.rmRight = rmRight;
    }

    /**
     * 负责反序列化
     */
    public static final Creator<CustomMode> CREATOR = new Creator<CustomMode>() {
        /**
         * 从序列化对象中，获取原始的对象
         * @param source
         * @return
         */
        @Override
        public CustomMode createFromParcel(Parcel source) {
            return new CustomMode(source);
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public CustomMode[] newArray(int size) {
            return new CustomMode[0];
        }
    };


    public CustomMode() {
    }


    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getChairMode() {
        return chairMode;
    }

    public void setChairMode(int chairMode) {
        this.chairMode = chairMode;
    }

    public int getLightMode() {
        return lightMode;
    }

    public void setLightMode(int lightMode) {
        this.lightMode = lightMode;
    }

    public int getAtmosphereLamp() {
        return atmosphereLamp;
    }

    public void setAtmosphereLamp(int atmosphereLamp) {
        this.atmosphereLamp = atmosphereLamp;
    }

    public int getRmType() {
        return rmType;
    }

    public void setRmType(int rmType) {
        this.rmType = rmType;
    }

    public int getRmStates() {
        return rmStates;
    }

    public void setRmStates(int rmStates) {
        this.rmStates = rmStates;
    }

    public int getRmLeft() {
        return rmLeft;
    }

    public void setRmLeft(int rmLeft) {
        this.rmLeft = rmLeft;
    }

    public int getRmRight() {
        return rmRight;
    }

    public void setRmRight(int rmRight) {
        this.rmRight = rmRight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeInt(volume);
       parcel.writeInt(chairMode);
       parcel.writeInt(lightMode);
       parcel.writeInt(atmosphereLamp);
       parcel.writeInt(rmType);
       parcel.writeInt(rmStates);
       parcel.writeInt(rmLeft);
       parcel.writeInt(rmRight);

    }
}
