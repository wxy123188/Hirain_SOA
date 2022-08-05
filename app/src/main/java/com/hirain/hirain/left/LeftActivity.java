package com.hirain.hirain.left;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hirain.hirain.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LeftActivity extends Activity {


    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            init();
        }
    };
    private TextView speedTv;      //速度
    private TextView tireSpeedTv;  //转速
    private TextView gearTv;       //档位
    private TextView powerTv;      //电耗
    private TextView modeName;     //选中模式名字
    private TextView electricTV;   //电量
    private TextView systemTime;   //时间
    private Timer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);

        initView();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,1000,30000);
    }

    private void initView() {

        speedTv = findViewById(R.id.speed_tv);
        tireSpeedTv = findViewById(R.id.tire_speed_tv);
        gearTv = findViewById(R.id.gear_tv);
        powerTv = findViewById(R.id.power_tv);
        modeName = findViewById(R.id.carse_mode_name);
        electricTV = findViewById(R.id.electric_quantity_num);
        systemTime = findViewById(R.id.system_time);


    }
    private void init() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(currentTime);
        String path = "fonts" + File.separator + "digital-7.ttf";
        AssetManager am =getAssets();
        Typeface tf = Typeface.createFromAsset(am, path);
        systemTime.setTypeface(tf);
        systemTime.setText(formatter.format(date));

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //todo 隐藏导航栏和状态栏
        if (hasFocus){
            View decorView =getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            //隐藏导航栏和状态栏
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler=null;
        timer.cancel();
        timer=null;
    }
}
