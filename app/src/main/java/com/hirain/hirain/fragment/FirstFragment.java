package com.hirain.hirain.fragment;

import android.app.AlarmManager;
import android.app.Presentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.hardware.display.DisplayManager;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hirain.hirain.MainActivity;
import com.hirain.hirain.MyPresentation;
import com.hirain.hirain.ShowPresentation;
import com.hirain.hirain.bean.CustomMode;
import com.hirain.hirain.bean.ModelItem;
import com.hirain.hirain.bean.UdpBean;
import com.hirain.hirain.dialog.DialogUtils;
import com.hirain.hirain.service.MusicService;
import com.hirain.hirain.MyAdapter;
import com.hirain.hirain.R;
import com.hirain.hirain.Song;
import com.hirain.hirain.bean.event.EditModeEvent;
import com.hirain.hirain.utils.MMkvUtils;
import com.hirain.hirain.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.zip.Checksum;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Context.MODE_PRIVATE;
import static com.hirain.hirain.R.mipmap.ic_launcher_round;
import static com.hirain.hirain.R.mipmap.my;
import static com.hirain.hirain.R.mipmap.nodengguang;
import static com.hirain.hirain.R.mipmap.nohshijing;
import static com.hirain.hirain.R.mipmap.zuoyiquanbi;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FirstFragment extends Fragment implements View.OnClickListener {
    //todo 模式按钮
    private Button musis,show;
    //todo 自定义模式
    private RelativeLayout firstfrag;
    private Button tianjia;
    private RelativeLayout view1,view2,view3,view4,view5;
    private TextView textView1,textView2,textView3,textView4,textView5;
    private Button viewset1,viewset2,viewset3,viewset4,viewset5;
    private Button shezhi1,shezhi2,shezhi3,shezhi4,shezhi5;
    private Button shancu1,shancu2,shancu3,shancu4,shancu5;
    private RecyclerView recyclerView;


    //todo 音乐卡片
    private Runnable runnable;


    private List<Integer> listbanner;//轮播图图片
    private ImageButton bofang,zanting,shangyiso,xiayiso;
    private ImageView mv;
    private SeekBar seekBar;  //进度条
    private PopupWindow popupWindow;
    private List<Song> songList=new ArrayList<>();

    private Animation animation;
    private MusicService.MusicBinder binder;
    //绑定服务
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(MusicService.MusicBinder) service;


            binder.setOnClick(new MusicService.OnMediaStateListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onPrepared() {

//                mediaPlayerUtil.start();
                    seekBar.setMax(MusicService.mediaPlayer.getDuration());

                    musicDuration.setText(formatTime(MusicService.mediaPlayer.getDuration()));
                    updateProgress();
                    //设置进度条的最大时长

                }

                @Override
                public void onSeekUpdate(int curTimeInt) {
                    seekBar.setProgress(curTimeInt);
                }
                //播放完毕
                @Override
                public void onCompletion() {
                    //自动下一首
                }

                @Override
                public boolean onError() {
                    return true;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            // 展示给进度条和当前时间
            int progress = MusicService.mediaPlayer
                    .getCurrentPosition();
            musicProgress.setText(formatTime(progress));
            seekBar.setProgress(progress);
//            tvPlayTime.setText(parseTime(progress));
            // 继续定时发送数据
            updateProgress();
            return true;
        }
    });
    private TextView musicName;
    private TextView musicProgress;
    private TextView musicDuration;
    private MyAdapter myAdapter;
    //温度
    private TextView temperatureTv;
    //时间
    private TextView timeTv;
    //日期
    private TextView dateTv;
    //氛围灯模式
    private TextView lightMode;
    private ShowPresentation showPresentation;

    //展示模式 ，音乐模式是否打开 默认关闭
    public boolean isShowMode;
    public boolean isMusicMode;



    private void updateProgress() {
        // 使用Handler每间隔1s发送一次空消息，通知进度条更新
        Message msg = Message.obtain();// 获取一个现成的消息
        // 使用MediaPlayer获取当前播放时间除以总时间的进度
        int progress = MusicService.mediaPlayer.getCurrentPosition();
        msg.arg1 = progress;
        mHandler.sendMessageDelayed(msg, 1000);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event){
        if(event instanceof EditModeEvent){
            EditModeEvent editModeEvent= (EditModeEvent) event;

            switch (editModeEvent.getEditState()) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    //取消编辑

                    break;
                case 3:
                    //保存编辑更新自定义模式的列表
                    Set<String> strings = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
                    ArrayList<ModelItem> data = new ArrayList<>();

                    for (String str : strings) {
                        ModelItem modelItem = new ModelItem();
                        modelItem.setModeName(str);
                        data.add(modelItem);
                    }
                    myAdapter.mList.clear();
                    myAdapter.mList.addAll(data);
                    myAdapter.notifyDataSetChanged();
                    break;
                case 4:
                    //保存新模式
                    Set<String> set = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
                    ArrayList<ModelItem> datas = new ArrayList<>();

                    for (String str : set) {
                        ModelItem modelItem = new ModelItem();
                        modelItem.setModeName(str);
                        datas.add(modelItem);
                    }
                    myAdapter.mList.clear();
                    myAdapter.mList.addAll(datas);
                    myAdapter.notifyDataSetChanged();
                    break;

            }

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //进度条

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        Intent intent=new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent,connection,BIND_AUTO_CREATE);
        songList= MainActivity.getSongList();

        initView();

        initCustomMode();


        //获取本地音乐
//        initMusic();
        //点击事件
        initClick();
        //图片动画
        animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(10000);
        animation.setRepeatCount(Animation.INFINITE);//无限循环
        animation.setInterpolator(new LinearInterpolator());//匀速运动插值器
        mv.setAnimation(animation);
    }

    private void initCustomMode() {

        //从本地获取
        Set<String> strings = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
        if(strings==null){
            strings=new HashSet<>();
        }
        ArrayList<ModelItem> data = new ArrayList<>();

        for (String str : strings) {
            ModelItem modelItem = new ModelItem();
            modelItem.setModeName(str);
            data.add(modelItem);
        }


        recyclerView = getActivity().findViewById(R.id.my_re_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
    }

    private void initClick() {
        bofang.setOnClickListener(this);
        shangyiso.setOnClickListener(this);
        xiayiso.setOnClickListener(this);
        musis.setOnClickListener(this);
        show.setOnClickListener(this);
        lightMode.setOnClickListener(this);
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//需要做个判断，判断是否超过多少多少条
                //添加新模式  ---设置名字----，跳转到第二个页面
             /*   MainActivity activity = (MainActivity) getActivity();
                activity.selectMenu(R.id.radio_2);*/
                DialogUtils.customEditView(getActivity(), R.string.set_model, R.string.dialog_cancle, R.string.dialg_confirm,
                        new DialogUtils.onClickListener() {
                            @Override
                            public void leftClickListener() {

                            }

                            @Override
                            public void rightClickListener(String text) {
                                Set<String> mode = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
                                if(text.isEmpty()){
                                    int index=1;
                                    text="自定义模式"+index;
                                    text= checkModeName(text, index, mode);
                                    mode.add(text);
                                }else {
                                    boolean add = mode.add(text);
                                    if(!add){
                                        ToastUtil.showToast(getActivity(),getResources().getString(R.string.repeat_name));
                                        return;
                                    }
                                }
                                Log.i("wxy", "rightClickListener: "+mode.toString());

                                MMkvUtils.getmInstance().encodeSet(MMkvUtils.MODE,mode);
                                EventBus.getDefault().post(new EditModeEvent(text,4));
                            }
                        });
            }
        });

        myAdapter.setOnItemListenter(new MyAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                ModelItem modelItem = myAdapter.mList.get(position);

                DialogUtils.modeTipView(getActivity(), new DialogUtils.onClickListener() {
                    @Override
                    public void leftClickListener() {

                    }

                    @Override
                    public void rightClickListener(String text) {
                        isMusicMode=false;
                        isShowMode=false;
                        if(myAdapter.mList.get(position).isSel){
                            myAdapter.mList.get(position).setSel(false);
                        }else {
                            for (int i = 0; i < myAdapter.mList.size(); i++) {
                                myAdapter.mList.get(i).setSel(false);
                            }
                            myAdapter.mList.get(position).setSel(true);


                        myAdapter.notifyDataSetChanged();
                        startLeft(2,null);

                        CustomMode customMode = MMkvUtils.getmInstance().decodeParcelable(modelItem.getModeName());
                        if(customMode==null){
                            customMode=new CustomMode();
                        }
                        //更改氛围灯
                        showAtmosphereLamp(customMode.getAtmosphereLamp());
                            EventBus.getDefault().post(modelItem.getModeName());
                        EventBus.getDefault().post(new EditModeEvent(modelItem.getModeName(),5));
                        //确认开启
                        ToastUtil.showToast(getActivity(),getResources().getString(R.string.mode_start,modelItem.getModeName()));
                        }
                    }
                });
            }
        });

        myAdapter.setOnMenuItemListenter(new MyAdapter.OnMenuItemClickListener() {
            @Override
            public void editClick(int position) {
                //编辑跳转到 第二个页面

                //跳转到第二个页面
                EventBus.getDefault().post(new EditModeEvent(myAdapter.mList.get(position).getModeName(),0));
            }

            @Override
            public void delClick(int position) {
                //删除本地存储的自定义模式
                String modeName = myAdapter.mList.get(position).getModeName();
                Set<String> strings = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
                strings.remove(modeName);
                MMkvUtils.getmInstance().encodeSet(MMkvUtils.MODE,strings);
            }
        });

        //进度条的拖动联动音乐的播放进度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    //获取进度条改变后的位置并播放
                    MusicService.mediaPlayer.seekTo(i);
                    bofang.setImageResource(R.mipmap.pause);
                }





            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                MusicService.mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicService.mediaPlayer.start();

            }
        });

    }

    public void showAtmosphereLamp(int atmosphereLamp) {
        switch (atmosphereLamp) {
            case 0:
                lightMode.setText(R.string.close);
                break;
            case 1:
                lightMode.setText(R.string.atmospherelamp_1);
                break;
            case 2:
                lightMode.setText(R.string.atmospherelamp_2);
                break;
            case 3:
                lightMode.setText(R.string.atmospherelamp_3);
                break;
            case 4:
                lightMode.setText(R.string.atmospherelamp_4);
                break;
        }

    }

    /*
    *
    *  type  1: 展示模式
    *  type  2: 默认模式
    * */

    private void startLeft(int type, CustomMode customMode) {
        DisplayManager displayManager = (DisplayManager) getActivity().getSystemService(Context.DISPLAY_SERVICE);

        if(type==1){
            if(showPresentation==null){
                showPresentation = new ShowPresentation(getActivity(), displayManager.getDisplay(1));
            }
            showPresentation.show();
        }else {
            MainActivity mainActivity = (MainActivity) getActivity();
            if(mainActivity.myPresentation==null){
                mainActivity.myPresentation = new MyPresentation(getActivity(), displayManager.getDisplay(1));
            }
            if(showPresentation!=null&&showPresentation.isShowing()){
                showPresentation.dismiss();
            }
            if(!mainActivity.myPresentation.isShowing()){
                mainActivity.myPresentation.show();
            }



        }


    }


    public String checkModeName(String name, int index, Set<String> mode){
        boolean isRepeat=mode.add(name);
        while (!isRepeat){
            index++;
            name="自定义模式"+index;
            isRepeat=mode.add(name);

        }


        return name;
    }


    //初始化控件
    public void initView(){
        //模式按钮
        musis=getActivity().findViewById(R.id.musisbutton);
        show=getActivity().findViewById(R.id.showbutton);
        //自定义模式
        tianjia=getActivity().findViewById(R.id.firsttianjia);
        firstfrag=getActivity().findViewById(R.id.firstfragme);


        textView2=getActivity().findViewById(R.id.text2);

        temperatureTv = getActivity().findViewById(R.id.temperature_tv);
        timeTv = getActivity().findViewById(R.id.time_tv);
        dateTv = getActivity().findViewById(R.id.date_tv);
        lightMode = getActivity().findViewById(R.id.light_mode);
        String path = "fonts" + File.separator + "digital-7.ttf";
        AssetManager am =getActivity().getAssets();
        Typeface tf = Typeface.createFromAsset(am, path);
        temperatureTv.setTypeface(tf);
        timeTv.setTypeface(tf);
        dateTv.setTypeface(tf);

        //音乐卡片
        listbanner =new ArrayList<>();//mv图片
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        seekBar=getActivity().findViewById(R.id.mediautil);
        bofang=getActivity().findViewById(R.id.bofang);
        musicName = getActivity().findViewById(R.id.music_name);
        musicProgress = getActivity().findViewById(R.id.music_progress);
        musicDuration = getActivity().findViewById(R.id.music_duration);



        shangyiso=getActivity().findViewById(R.id.shangyis);
        xiayiso=getActivity().findViewById(R.id.xiayiso);
        zanting=getActivity().findViewById(R.id.zanting);
        mv=getActivity().findViewById(R.id.imagemv);
        //获取上一次的播放进度
        SharedPreferences preferences = getActivity().getSharedPreferences("Last Information", MODE_PRIVATE);
        int pos=preferences.getInt("position",0);
        int duration=preferences.getInt("duration",songList.get(0).getDuration());
        seekBar.setMax(duration);

        seekBar.setProgress(preferences.getInt("currentPosition",0));
        musicName.setText(songList.get(pos).getSong());
        int currentPosition = preferences.getInt("currentPosition", 0);
        musicProgress.setText(formatTime(currentPosition));
        musicDuration.setText(formatTime(duration));

    }
    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }
    }


    public void initTime(){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(currentTime);
        timeTv.setText(formatter.format(date));
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy.MM.dd");
        Date date2 = new Date(currentTime);
        dateTv.setText(formatter2.format(date2));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
        mHandler.removeCallbacksAndMessages(null);
        if(showPresentation!=null&&showPresentation.isShowing()){
            showPresentation.dismiss();
        }

    }

    //取消开启的模式  --更改了车辆设置
    public void cancleMode(){
        for (int i = 0; i < myAdapter.mList.size(); i++) {
            myAdapter.mList.get(i).setSel(false);
        }
        myAdapter.notifyDataSetChanged();
        EventBus.getDefault().post("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bofang:

                //播放
                binder.playMusic();
                if(!MusicService.mediaPlayer.isPlaying()){

                    bofang.setImageResource(R.mipmap.play);
                }else {

                    bofang.setImageResource(R.mipmap.pause);
                }
                break;
            case R.id.shangyis:
                //上一首
                binder.previousMusic();
                musicName.setText(songList.get(MusicService.getPos()).getSong());
                bofang.setImageResource(R.mipmap.pause);
                break;
            case R.id.xiayiso:
                //下一首
                binder.nextMusic();
                musicName.setText(songList.get(MusicService.getPos()).getSong());

                bofang.setImageResource(R.mipmap.pause);
                break;
            case R.id.musisbutton:
                //y音乐模式
                DialogUtils.modeTipView(getActivity(), new DialogUtils.onClickListener() {
                    @Override
                    public void leftClickListener() {

                    }

                    @Override
                    public void rightClickListener(String text) {
                        isShowMode=false;
                        isMusicMode=true;
                        //确认开启
                        ToastUtil.showToast(getActivity(),getResources().getString(R.string.mode_start,"音乐模式"));
                        EventBus.getDefault().post("音乐模式");
                    }
                });
                break;
            case R.id.showbutton:
                //展示模式
                DialogUtils.modeTipView(getActivity(), new DialogUtils.onClickListener() {
                    @Override
                    public void leftClickListener() {
                    }

                    @Override
                    public void rightClickListener(String text) {
                        isShowMode=true;
                        isMusicMode=false;
                        startLeft(1,null);
                        //确认开启
                        ToastUtil.showToast(getActivity(),getResources().getString(R.string.mode_start,"展示模式"));
                        EventBus.getDefault().post("展示模式");
                    }
                });
                break;
            case R.id.light_mode:
                //氛围灯模式  跳转到车辆设置---灯光
                 MainActivity mainActivity= (MainActivity) getActivity();
                 mainActivity.selectMenu(R.id.radio_2);
                 mainActivity.carsetFragment.switchType(3);
                break;
        }
    }
}