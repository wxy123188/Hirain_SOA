package com.hirain.hirain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    //歌手
    private String singer;

    //歌名
    private String song;

    //歌曲的地址
    private String path;
    private int musicPath;

    //歌曲的时长
    private int duration;

    //歌曲的大小
    private long size;

    //歌曲的专辑图片
    private Bitmap image;

    public int getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(int musicPath) {
        this.musicPath = musicPath;
    }

    //为变量设置getter and setter方法
    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.singer);
        dest.writeString(this.song);
        dest.writeString(this.path);
        dest.writeInt(this.duration);
        dest.writeLong(this.size);
        dest.writeParcelable(this.image, flags);
    }

    public Song() {
    }

    protected Song(Parcel in) {
        this.singer = in.readString();
        this.song = in.readString();
        this.path = in.readString();
        this.duration = in.readInt();
        this.size = in.readLong();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}

