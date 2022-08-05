package com.hirain.hirain.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.hirain.hirain.R;
import com.hirain.hirain.utils.ModelBaseUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.hirain.hirain.R.mipmap.bofang;
import static com.hirain.hirain.R.mipmap.hshijing;
import static com.hirain.hirain.R.mipmap.nodengguang;
import static com.hirain.hirain.R.mipmap.nohshijing;
import static com.hirain.hirain.R.mipmap.zuoyiquanbi;


public class AlluseFragment extends Fragment implements View.OnClickListener {

    //左右连个视频播放器
    private VideoView leftVideo;
    private VideoView rightVideo;
    //线路启动和停止
    private TextView modeStart;
    private TextView modeStop;
    //线路 1,2,3,4
    public boolean isPlay = false;
    private RelativeLayout line1Rl, line3Rl, line2Rl, line4Rl;
    private ImageView line1Iv, line2Iv, line3Iv, line4Iv;
    private TextView line1Tv, line2Tv, line3Tv, line4Tv;
    String projectPath = "H:/modelBase/Project/test/test.proj";
    String oscPath = "H:/modelBase/Project/test/roads/test/test.xosc";
    String host="192.168.0.218";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alluse, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        initData(R.raw.video6, R.raw.video3);
        initListener();
    }

    private void initListener() {
        rightVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                rightVideo.start();
            }
        });
        leftVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                leftVideo.start();
            }
        });
    }

    private void initData(int leftPath, int rightPath) {
        if (leftVideo.isPlaying()) {
            leftVideo.pause();
        }
        if (rightVideo.isPlaying()) {
            rightVideo.pause();
        }
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + leftPath);
        leftVideo.setVideoURI(uri);

//        leftVideo.start();
        Uri uri2 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + rightPath);
        rightVideo.setVideoURI(uri2);

//        rightVideo.start();
    }

    private void initView() {

        leftVideo = getActivity().findViewById(R.id.video_left);
        rightVideo = getActivity().findViewById(R.id.video_right);

        modeStart = getActivity().findViewById(R.id.drive_mode_start);
        modeStop = getActivity().findViewById(R.id.drive_mode_stop);
        //切换路线

        line1Rl = getActivity().findViewById(R.id.drive_line1_rl);
        line1Iv = getActivity().findViewById(R.id.drive_line1_iv);
        line1Tv = getActivity().findViewById(R.id.drive_line1_tv);
        line2Rl = getActivity().findViewById(R.id.drive_line2_rl);
        line2Tv = getActivity().findViewById(R.id.drive_line2_tv);
        line2Iv = getActivity().findViewById(R.id.drive_line2_iv);
        line3Rl = getActivity().findViewById(R.id.drive_line3_rl);
        line3Tv = getActivity().findViewById(R.id.drive_line3_tv);
        line3Iv = getActivity().findViewById(R.id.drive_line3_iv);
        line4Rl = getActivity().findViewById(R.id.drive_line4_rl);
        line4Iv = getActivity().findViewById(R.id.drive_line4_iv);
        line4Tv = getActivity().findViewById(R.id.drive_line4_tv);

        line1Rl.setOnClickListener(this);
        line2Rl.setOnClickListener(this);
        line3Rl.setOnClickListener(this);
        line4Rl.setOnClickListener(this);

        modeStart.setOnClickListener(this);
        modeStop.setOnClickListener(this);
        selectLines(1);


    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event) {
        /*if(event==1){
            playVideo();
        }else {
            pauseVideo();
        }*/
    }

    public void pauseVideo() {
        if (rightVideo != null) {
            rightVideo.pause();
        }
        if (leftVideo != null) {
            leftVideo.pause();
        }


    }

    public void playVideo() {
        if (!isPlay) {
            leftVideo.requestFocus();
            rightVideo.requestFocus();
            isPlay = true;

        }
        rightVideo.start();
        leftVideo.start();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    //选择路线
    public void selectLines(int lineNum) {
        line1Rl.setBackground(null);
        line2Rl.setBackground(null);
        line3Rl.setBackground(null);
        line4Rl.setBackground(null);
        line1Tv.setTextColor(getResources().getColor(R.color.color_cfcfcf));
        line2Tv.setTextColor(getResources().getColor(R.color.color_cfcfcf));
        line3Tv.setTextColor(getResources().getColor(R.color.color_cfcfcf));
        line4Tv.setTextColor(getResources().getColor(R.color.color_cfcfcf));
        line4Iv.setImageResource(R.mipmap.dirve_line1);
        line2Iv.setImageResource(R.mipmap.drive_line2);
        line3Iv.setImageResource(R.mipmap.drive_line3);
        line1Iv.setImageResource(R.mipmap.drive_line4);
        switch (lineNum) {
            case 1:
                line1Rl.setBackgroundResource(R.mipmap.dirve_line_bg);
                line1Iv.setImageResource(R.mipmap.drive_line4_sel);
                line1Tv.setTextColor(getResources().getColor(R.color.color_8fd8f6));

                break;
            case 2:
                line2Rl.setBackgroundResource(R.mipmap.dirve_line_bg);
                line2Iv.setImageResource(R.mipmap.drive_line2_sel);
                line2Tv.setTextColor(getResources().getColor(R.color.color_8fd8f6));
                break;
            case 3:
                line3Rl.setBackgroundResource(R.mipmap.dirve_line_bg);
                line3Iv.setImageResource(R.mipmap.drive_line3_sel);
                line3Tv.setTextColor(getResources().getColor(R.color.color_8fd8f6));
                break;
            case 4:
                line4Rl.setBackgroundResource(R.mipmap.dirve_line_bg);
                line4Iv.setImageResource(R.mipmap.dirve_line1);
                line4Tv.setTextColor(getResources().getColor(R.color.color_8fd8f6));
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drive_mode_start:
                ModelBaseUtils.getmInstance().init(host,projectPath,oscPath);
//                rightVideo.start();
//                leftVideo.start();
                break;
            case R.id.drive_mode_stop:
                ModelBaseUtils.getmInstance().stop();
//                rightVideo.pause();
//                leftVideo.pause();
                break;
            case R.id.drive_line1_rl:
                initData(R.raw.video6, R.raw.video3);
                isPlay = false;
                playVideo();
                selectLines(1);
                break;
            case R.id.drive_line2_rl:
                initData(R.raw.video3, R.raw.video2);
                isPlay = false;
                playVideo();
                selectLines(2);
                break;
            case R.id.drive_line3_rl:
                initData(R.raw.video2, R.raw.video3);
                isPlay = false;
                playVideo();
                selectLines(3);
                break;
            case R.id.drive_line4_rl:

                initData(R.raw.video4, R.raw.video6);
                isPlay = false;
                playVideo();
                selectLines(4);
                break;
        }
    }
}