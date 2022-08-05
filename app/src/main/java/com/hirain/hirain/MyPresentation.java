package com.hirain.hirain;

import android.app.Presentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyPresentation extends Presentation {
    Context context;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            initTime();
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


    public MyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        context = outerContext;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event){
        Log.i("wxy", "getMessage: "+event.toString());
        if(event instanceof String){
            modeName.setText((String)event);
        }else if(event instanceof List){
            List<Double> list= (List<Double>) event;
            int aDouble = (int) (list.get(0)*1.0);

            tireSpeedTv.setText(aDouble+"");
            gearTv.setText(list.get(1)+"");
            int spped= (int) (list.get(2)*3.6);
            speedTv.setText(spped+"");
            powerTv.setText("8.0");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView =getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //隐藏导航栏和状态栏
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_left);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        initView();
    }


    public void setTime(String time){
        systemTime.setText(time);
    }

    private void initView() {

        speedTv = findViewById(R.id.speed_tv);
        tireSpeedTv = findViewById(R.id.tire_speed_tv);
        gearTv = findViewById(R.id.gear_tv);
        powerTv = findViewById(R.id.power_tv);
        modeName = findViewById(R.id.car_mode_name);
        electricTV = findViewById(R.id.electric_quantity_num);
        systemTime = findViewById(R.id.system_time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String path = "fonts" + File.separator + "digital-7.ttf";
        AssetManager am = context.getAssets();
        Typeface tf = Typeface.createFromAsset(am, path);
        systemTime.setTypeface(tf);

    }
    private void initTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(currentTime);
        String path = "fonts" + File.separator + "digital-7.ttf";
        AssetManager am =context.getAssets();
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




}

