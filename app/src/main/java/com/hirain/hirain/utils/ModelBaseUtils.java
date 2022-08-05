package com.hirain.hirain.utils;

import android.util.Log;

import com.hirain.csw.modelbase.Modelbase;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
*   modelBase
* */
public class ModelBaseUtils {

    private static ModelBaseUtils modelBaseUtils;
    List<String> keys = new ArrayList<>();
    private Modelbase modelbase;
    private Timer timer;
    private Thread thread;

    private ModelBaseUtils() {
        modelBaseUtils = new ModelBaseUtils();
    }

    public static ModelBaseUtils getmInstance() {
        if (modelBaseUtils == null) {
            synchronized (ModelBaseUtils.class) {
                if (modelBaseUtils == null) {
                    modelBaseUtils = new ModelBaseUtils();
//                    new Modelbase()
                }
            }
        }
        return modelBaseUtils;
    }

    //初始化modelBase
    public void init(String host,String projectPath,String oscPath){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("wxy", "run: aaa");
                modelbase = new Modelbase();
//                modelbase.Init("192.168.0.218");
                modelbase.Init(host);
//                String projectPath = "H:/modelBase/Project/test/test.proj";
//                String oscPath = "H:/modelBase/Project/test/roads/test/test.xosc";
                modelbase.StartEx(projectPath, oscPath);


            }
        });

        thread.start();
        getData();
    }
    public void stop(){
        if(modelbase!=null){
            modelbase.Stop();
            timer.cancel();
            thread.stop();
        }
    }

    public void getData(){
        if(timer==null){
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //转速
                    String engine="TypeA_PowTra_Spd_Engine";
                    //档位
                    String gear="TypeA_PowTra_GearPos_Gbox";
                    //速度  m/s
                    String speed="TypeA_SprungMass_Vx_Veh_V";
                    keys.add(engine);
                    keys.add(gear);
                    keys.add(speed);
                    List<Double> doubles = modelbase.GetValues(keys);
                    double value=0;
                    if(doubles!=null&&doubles.size()>0){
                        EventBus.getDefault().post(doubles);
                    }

                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }



    }

}
