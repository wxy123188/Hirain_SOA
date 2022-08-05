package com.hirain.hirain;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ShowPresentation extends Presentation {
    Context context;
    int duration =3500;

    public ShowPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        context = outerContext;
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
        setContentView(R.layout.activity_show_model);
        initView();
    }

    private void initView() {

        ImageView car = findViewById(R.id.car);
        ImageView light1 = findViewById(R.id.light1);
        ImageView light11 = findViewById(R.id.light1_2);
        ImageView light2 = findViewById(R.id.light2);
        ImageView light22 = findViewById(R.id.light2_2);
        ImageView light3 = findViewById(R.id.light3);
        ImageView light33 = findViewById(R.id.light3_3);
        ImageView light4 = findViewById(R.id.light4);
        ImageView light44 = findViewById(R.id.light4_4);
        ImageView light5 = findViewById(R.id.light5);
        ImageView light55 = findViewById(R.id.light5_5);
        ImageView light6 = findViewById(R.id.light6);
        ImageView light66 = findViewById(R.id.light6_6);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) light2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) light4.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) light6.getLayoutParams();

        lightLeftAnim(light1, 0, 0);
        lightLeftAnim(light3, 0, 0);
        lightLeftAnim(light5, 0, 0);
        //view 宽度暂时写死，应该动态获取
        lightRightAnim(light2, 1062, 0);
        lightRightAnim(light4, 545, 0);
        lightRightAnim(light6, 365, 0);

        lightAlpha(light11);
        lightAlpha(light22);
        lightAlpha(light33);
        lightAlpha(light44);
        lightAlpha(light55);
        lightAlpha(light66);

        startPropertyAnim(car);


    }


    private void lightRightAnim(ImageView imageView, float x, float y) {

        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation",  5f,15f, 25f, 0f, 0f, -5f, -15f, -25f, -15f,-5f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    public void lightLeftAnim(ImageView imageView, int x, int y, float... values) {
        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation",  -5f,-15f, -25f, 0f, 0f, 5f, 15f, 25f, 15f,5f);

        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    public void lightAlpha(ImageView imageView) {
        imageView.setVisibility(View.GONE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 0f, 0f, -0.8f, 1f, 1f, 0.8f, 0f, 0f, 0f);
        alpha.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(alpha);
        animationSet.setDuration(duration);

//        animationSet.start();
    }

    // 动画实际执行安然
    private void startPropertyAnim(ImageView imageView) {
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。

        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", -50f, -40f, -30f, -20f, -10f, 0f, 0f, 20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "translationX", 250f, 200f, 150f, 100f, -50f, -100f, -200f, -300f, -400f, -500f, -600f, -700f, -800f, -900f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleY", 0.1f, 0.4f, 0.6f, 0.7f, 1f, 1f, 1.2f, 1.2f, 1.2f, 1.4f, 1.4f, 1.4f, 1.4f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleX", 0.1f, 0.4f, 0.6f, 0.7f, 1f, 1f, 1.2f, 1.2f, 1.2f, 1.4f, 1.4f, 1.4f, 1.4f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 0.1f, 0.2f, 0.3f, 0.6f, 0.7f, 0.9f, 1f, 1f, 1f, 1f, 1f);

        translationY.setRepeatCount(Animation.INFINITE);
        translationX.setRepeatCount(Animation.INFINITE);
        alpha.setRepeatCount(Animation.INFINITE);
        scaleX.setRepeatCount(Animation.INFINITE);
        scaleY.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(translationY, translationX, scaleY, scaleX, alpha);
        animationSet.setDuration(duration);
        animationSet.start();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //todo 隐藏导航栏和状态栏
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            //隐藏导航栏和状态栏
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }
}

