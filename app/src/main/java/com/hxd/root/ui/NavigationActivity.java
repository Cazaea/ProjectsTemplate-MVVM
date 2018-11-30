//package com.hxd.root.ui;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class NavigationActivity extends AppCompatActivity {
//
//    // 布局控件
//    ViewPager viewPager;
//    LinearLayout layoutDots;
//    // 资源信息
//    private int[] layouts = new int[]{
//            com.hxd.root.R.layout.guide1,
//            com.hxd.root.R.layout.guide2,
//            com.hxd.root.R.layout.guide3
//    };
//
//    // 点击滑动位置
//    private float mPosX, mPosY, mCurPosX, mCurPosY;
//
//    // 判断是否第一次启动，存入最新数据
//    private PreferUtil preferUtil;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mBinding = DataBindingUtil.setContentView(this, com.hxd.root.R.layout.activity_transition);
//        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
//        // 展示图片
//        showImage();
//    }
//
//    @Override
//    protected int setLayoutId() {
//        // 在绑定视图前检查是否第一次运行
//        preferUtil = PreferUtil.getInstance(this);
//        // 如果已存版本号等于当前版本号，跳过引导页
//        if (preferUtil.getSavedVersionCode() == VersionUtil.getVersionCode(this)) {
//            launchSplashScreen();
//        }
//        return com.hxd.root.R.layout.activity_guide;
//    }
//
//    @Override
//    protected void initView() {
//        super.initView();
//        viewPager = findViewById(com.hxd.root.R.id.view_pager);
//        layoutDots = findViewById(com.hxd.root.R.id.layoutDots);
//        // 申请权限
//        requestPermission();
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        // 默认指示器指示第一个
//        addBottomDots(0);
//    }
//
//    @Override
//    protected void setListener() {
//        super.setListener();
//        // ViewPager数据适配
//        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter();
//        viewPager.setAdapter(mPagerAdapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        // 监听ViewPager滑动事件
//        touchListener();
//    }
//
//    /**
//     * 监听ViewPager滑动事件
//     */
//    @SuppressLint("ClickableViewAccessibility")
//    private void touchListener() {
//        viewPager.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//
//                case MotionEvent.ACTION_DOWN:
//                    mPosX = event.getX();
//                    mPosY = event.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    mCurPosX = event.getX();
//                    mCurPosY = event.getY();
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    if (mCurPosX - mPosX > 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
//                        // 向左滑动
//                        int current = getItem(-1);
//                        if (current >= 0) {
//                            viewPager.setCurrentItem(current);
//                        }
//                    } else if (mCurPosX - mPosX < 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
//                        // 向右滑动
//                        int current = getItem(+1);
//                        if (current < layouts.length) {
//                            viewPager.setCurrentItem(current);
//                        } else {
//                            // 如果已经是最后一条数据，再滑动，进入广告页
//                            launchTransition();
//                        }
//                    }
//
//                    break;
//            }
//            return true;
//        });
//    }
//
//    private int getItem(int i) {
//        return viewPager.getCurrentItem() + i;
//    }
//
//    /**
//     * 跳入广告页
//     */
//    private void launchTransition() {
//        // 设置最新版本号
//        preferUtil.putLeastVersionCode();
//        startActivity(new Intent(NavigationActivity.this, TransitionActivity.class));
//        finish();
//    }
//
//    /**
//     * ViewPager滑动监听
//     */
//    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            addBottomDots(position);
//            /*
//            if(position == layouts.length - 1) {
//                btnNext.setText(getString(R.string.start));
//                btnSkip.setVisibility(View.GONE);
//            } else {
//                btnNext.setText(getString(R.string.next));
//                btnSkip.setVisibility(View.VISIBLE);
//            }
//            */
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//        }
//    };
//
//    /**
//     * 添加底部导航按钮
//     *
//     * @param currentPage
//     */
//    private void addBottomDots(int currentPage) {
//        TextView[] dots = new TextView[layouts.length];
//
//        int[] colorsActive = getResources().getIntArray(com.hxd.root.R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(com.hxd.root.R.array.array_dot_inactive);
//
//        layoutDots.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(this);
//            dots[i].setText(Html.fromHtml("&#8226;")); //圆点
//            dots[i].setTextSize(30);
//            dots[i].setTextColor(colorsInactive[currentPage]);
//            layoutDots.addView(dots[i]);
//        }
//
//        if (dots.length > 0) {
//            dots[currentPage].setTextColor(colorsActive[currentPage]);
//        }
//    }
//
//    /**
//     * 适配器
//     */
//    private class ViewPagerAdapter extends PagerAdapter {
//
//        private LayoutInflater layoutInflater;
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            assert layoutInflater != null;
//            View view = layoutInflater.inflate(layouts[position], container, false);
//            container.addView(view);
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return layouts.length;
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            View view = (View) object;
//            container.removeView(view);
//        }
//    }
//
//}
