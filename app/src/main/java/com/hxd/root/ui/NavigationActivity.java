package com.hxd.root.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.github.paolorotolo.appintro.AppIntro;
import com.hxd.root.R;
import com.hxd.root.SampleSlide;
import com.hxd.root.app.Constants;
import com.hxd.root.utils.CommonUtils;
import com.hxd.root.utils.SPUtils;

public class NavigationActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();
        if (!SPUtils.getBoolean(Constants.IS_FIRST, true)) {
            enterTransition();
            return;
        }

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SampleSlide.newInstance(R.layout.fragment_slide_first));
        addSlide(SampleSlide.newInstance(R.layout.fragment_slide_second));
        addSlide(SampleSlide.newInstance(R.layout.fragment_slide_third));

        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.
//        SliderPage sliderPage1 = new SliderPage();
//        sliderPage1.setTitle("第一");
//        sliderPage1.setDescription("这是应用介绍页");
//        sliderPage1.setImageDrawable(R.mipmap.ic_launcher);
//        sliderPage1.setBgColor(CommonUtils.getColor(R.color.colorPrimary));
//        addSlide(AppIntroFragment.newInstance(sliderPage1));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(CommonUtils.getColor(R.color.colorTransparent));
        setSeparatorColor(CommonUtils.getColor(R.color.colorAppBackground));
        showSeparator(false);

        // Hide Skip/Done button.
        setDoneText("立即体验");
        setColorDoneText(CommonUtils.getColor(R.color.colorAccent));
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

        // 监听Pager滑动
        setPagerListener();

    }

    /**
     * 进入过渡页面
     */
    private void enterTransition() {
        // 保存启动信息
        SPUtils.putBoolean(Constants.IS_FIRST, false);
        Intent intent = new Intent(this, TransitionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        enterTransition();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        enterTransition();
    }

    @Override
    protected void onPageSelected(int position) {
        // 定制底部导航圆点
        int[] colorsActive = CommonUtils.getArrayColor(R.array.array_dot_active);
        int[] colorsInactive = CommonUtils.getArrayColor(R.array.array_dot_inactive);
        setIndicatorColor(colorsActive[position], colorsInactive[position]);
        super.onPageSelected(position);
    }

    //=============================================================================================
    //=======================================自定义滑动进入页面=======================================
    //=============================================================================================

    /**
     * 点击滑动位置
     */
    private float mPosX, mPosY, mCurPosX, mCurPosY;

    /**
     * Item位置变换
     */
    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    /**
     * 监听Pager滑动
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setPagerListener() {
        pager.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    mPosY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:

                    if (mCurPosX - mPosX > 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
                        // 向左滑动
                        int current = getItem(-1);
                        if (current >= 0) {
                            pager.setCurrentItem(current);
                        }
                    } else if (mCurPosX - mPosX < 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
                        // 向右滑动
                        int current = getItem(+1);
                        if (current < slidesNumber) {
                            pager.setCurrentItem(current);
                        } else {
                            // 如果已经是最后一条数据，再滑动，进入广告页
                            enterTransition();
                        }
                    }

                    break;
            }
            return true;
        });
    }

}