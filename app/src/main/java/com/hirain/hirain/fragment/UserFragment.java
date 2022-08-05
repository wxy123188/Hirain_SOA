package com.hirain.hirain.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hirain.hirain.R;


public class UserFragment extends Fragment {



    int  duration= 4000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }




    private void lightRightAnim(ImageView imageView, float x, float y) {

        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f,15f,20f,0f,0f,-10f,-15f,-20f,-15f,-10f,0f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    public void lightLeftAnim(ImageView imageView,int x,int y,float... values){
        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f,-15f,-20f,0f,0f,10f,15f,20f,15f,10f,0f);

        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    public void lightAlpha(ImageView imageView){
        imageView.setVisibility(View.GONE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f,0f,0f,-0.8f,1f,1f,0.8f,0f,0f,0f);
        alpha.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(alpha);
        animationSet.setDuration(duration);

//        animationSet.start();
    }
    // 动画实际执行安然
    private void startPropertyAnim(ImageView imageView) {
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。

        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", -50f,-40f,-30f,-20f,-10f,0f,0f,20f,40f,60f,80f,100f,120f,140f,160f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "translationX", 250f,200f,150f,100f,-50f,-100f,-200f,-300f,-400f,-500f,-600f,-700f,-800f,-900f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleY", 0.1f,0.4f,0.6f,0.7f,1f,1f,1.2f,1.2f,1.2f,1.4f,1.4f,1.4f,1.4f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleX", 0.1f,0.4f,0.6f,0.7f,1f,1f,1.2f,1.2f,1.2f,1.4f,1.4f,1.4f,1.4f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f,0.1f,0.2f,0.3f,0.6f,0.7f,0.9f,1f,1f,1f,1f,1f);

        translationY.setRepeatCount(Animation.INFINITE);
        translationX.setRepeatCount(Animation.INFINITE);
        alpha.setRepeatCount(Animation.INFINITE);
        scaleX.setRepeatCount(Animation.INFINITE);
        scaleY.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(translationY,translationX,scaleY,scaleX,alpha);
        animationSet.setDuration(duration);
        animationSet.start();
    }

}