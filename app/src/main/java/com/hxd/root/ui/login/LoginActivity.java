package com.hxd.root.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.view.View;

import com.hxd.root.R;
import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.databinding.ActivityLoginBinding;
import com.hxd.root.utils.BaseTools;
import com.hxd.root.utils.CommonUtils;
import com.hxd.root.utils.DebugUtil;
import com.hxd.root.view.LongPressView;
import com.hxd.root.vmodel.login.LoginNavigator;
import com.hxd.root.vmodel.login.LoginViewModel;
import com.thejoyrun.router.Router;

/**
 * @author Cazaea
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginNavigator {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        showContentView();

        viewModel = new LoginViewModel(this);
        viewModel.setNavigator(this);
        bindingView.setLogin(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新开发者模式
        refreshDevelopStatus();
    }

    public void login(View view) {
        viewModel.login();
    }

    public void goRegister(View view) {
        viewModel.goRegister();
    }

    public void goRecover(View view) {
        viewModel.goRecover();
    }

    @SuppressLint("SetTextI18n")
    public void refreshDevelopStatus() {
        if (DebugUtil.isDebug) {
            bindingView.tvVersion.setText(CommonUtils.getString(R.string.string_developer_mode_on, "Version:" + BaseTools.getVersionName()));
            bindingView.tvVersion.setSingleClickListener(() -> Router.startActivity(LoginActivity.this, Constants.ROUTER_TOTAL_HEAD + "develop"));
        } else {
            bindingView.tvVersion.setText("Version:" + BaseTools.getVersionName());
            bindingView.tvVersion.setLongPressListener(() -> Router.startActivity(LoginActivity.this, Constants.ROUTER_TOTAL_HEAD + "develop"));
        }
    }

    /**
     * 注册或登录成功
     */
    @Override
    public void loadSuccess() {
        finish();
    }

    /**
     * 进入页面
     *
     * @param mContext
     */
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
