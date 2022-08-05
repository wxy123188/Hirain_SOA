package com.hirain.hirain.service;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.hirain.hirain.MainActivity;
import com.hirain.hirain.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicService extends Service {


    private static final String TAG = "MusicService";
    private MusicBinder musicBinder=new MusicBinder();

    private static int pos;
    private List<Song> songList=new ArrayList<>();
    private List<Integer> musicList=new ArrayList<>();



    private OnMediaStateListener mListener;

    //定义播放模式
    private boolean singlePlay=false;//单曲循环
    private boolean randomPlay=false;//随机播放
    private boolean sequencePlay=false;//顺序播放

    public static int getPos() {
        return pos;
    }
    //播放状态监听
    public interface OnMediaStateListener {

        //准备完成
        void onPrepared();

        //当前播放进度
        void onSeekUpdate(int curTimeInt);

        //播放完成
        void onCompletion();

        boolean onError();

    }
    public void setOnMediaStateListener(OnMediaStateListener listener) {
        mListener = listener;
    }
    public  class MusicBinder extends Binder {
        public void setList(List<Song> onClick){
            songList=onClick;
        }

        public void setOnClick(OnMediaStateListener onClick){
            mListener=onClick;
        }

        //设置单曲循环
        public void setSinglePlay(){
            singlePlay=true;
            randomPlay=false;
            sequencePlay=false;
        }

        //设置随机播放
        public void setRandomPlay(){
            singlePlay=false;
            randomPlay=true;
            sequencePlay=false;
        }

        //设置顺序播放
        public void setSequencePlay(){
            singlePlay=false;
            randomPlay=false;
            sequencePlay=true;
        }

        //调用该方法时传入list和position两个参数，并开始播放
        public void setMediaPlayer(int position){
            try {
                pos=position;
                //重置MediaPlayer
                mediaPlayer.reset();
//                mediaPlayer.setDataSource(songList.get(pos).getPath());
                AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(songList.get(pos).getMusicPath());
                try {
                    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //让MediaPlayer处于准备状态,异步准备资源防止卡顿
                mediaPlayer.prepareAsync();
                //调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        if(mListener!=null){
                            mediaPlayer.start();
                            mListener.onPrepared();
                        }
                    }
                });
                //对音乐播放状态进行监听，如果播放完毕就播放下一首
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(sequencePlay==true) {
                            nextMusic();
                        }else if(singlePlay==true){
                            setMediaPlayer(pos);
                        }else if(randomPlay==true){
                            Random random=new Random();
                            int i=random.nextInt(songList.size());
                            setMediaPlayer(i);
                        }else {
                            nextMusic();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //跳转到指定的时间
        public void seek(int pos){
            mediaPlayer.seekTo(pos);
        }

        //playMusic()方法控制音乐的播放与暂停
        public void playMusic(){

            if(mediaPlayer!=null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {

                    mediaPlayer.start();
                }

            }
        }

        //nextMusic()方法控制音乐播放下一曲
        public void nextMusic(){
            if(mediaPlayer!=null) {
                if (pos == songList.size() - 1) {
                    pos = 0;
                } else {
                    pos++;
                }
                setMediaPlayer(pos);

            }
        }

        //previousMusic()方法控制音乐播放上一曲
        public void previousMusic(){
            if(mediaPlayer!=null) {
                if (pos == 0) {
                    pos = songList.size() - 1;
                } else {
                    pos--;
                }
                setMediaPlayer(pos);

            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    public  static MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        songList= MainActivity.getSongList();
//        //音乐暂时写死，内存里没有
//        mediaPlayer.reset();
//        try {
//            AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(songList.get(pos).getMusicPath());
//
//            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
//            //让MediaPlayer处于准备状态,异步准备资源防止卡顿
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //恢复上次退出时的音乐状态
        SharedPreferences preferences = getSharedPreferences("Last Information", MODE_PRIVATE);
        try {
                pos=preferences.getInt("position",0);
                AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(preferences.getInt("path", songList.get(0).getMusicPath()));
                mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
                mediaPlayer.prepareAsync();
                mediaPlayer.seekTo(preferences.getInt("currentPosition", 0));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    if(mListener!=null){
                        mListener.onPrepared();
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

   @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {


        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * saveLastMusic()方法用于保存退出服务时当前播放歌曲的播放进度
     */
    public void saveLastMusic(){
        SharedPreferences.Editor editor=getSharedPreferences("Last Information",MODE_PRIVATE).edit();
        editor.putInt("position",pos);
        editor.putInt("currentPosition",mediaPlayer.getCurrentPosition());
        editor.putInt("duration",mediaPlayer.getDuration());
        editor.putInt("path",songList.get(pos).getMusicPath());
        editor.apply();
    }

    /**
     * 在服务销毁时，释放相关资源，保存音乐状态信息
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        saveLastMusic();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        stopForeground(true);
    }
}