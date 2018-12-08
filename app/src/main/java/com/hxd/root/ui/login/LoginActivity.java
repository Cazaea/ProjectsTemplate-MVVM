package com.hxd.root.ui.login;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hxd.root.R;
import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.databinding.ActivityLoginBinding;
import com.hxd.root.utils.BaseTools;
import com.hxd.root.utils.CommonUtils;
import com.hxd.root.utils.DebugUtil;
import com.hxd.root.vmodel.login.LoginViewModel;
import com.thejoyrun.router.Router;

/**
 * @author Cazaea
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        showContentView();

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        bindingView.setLogin(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新开发者模式
        refreshDevelopStatus();
    }

    @SuppressLint("SetTextI18n")
    public void refreshDevelopStatus() {
        if (DebugUtil.isDebug()) {
            bindingView.tvVersion.setText(CommonUtils.getString(R.string.string_developer_mode_on, "Version:" + BaseTools.getVersionName()));
            bindingView.tvVersion.setSingleClickListener(() -> Router.startActivity(LoginActivity.this, Constants.ROUTER_TOTAL_HEAD + "develop"));
        } else {
            bindingView.tvVersion.setText("Version:" + BaseTools.getVersionName());
            bindingView.tvVersion.setLongPressListener(() -> Router.startActivity(LoginActivity.this, Constants.ROUTER_TOTAL_HEAD + "develop"));
        }
    }

    /**
     * 用户登录
     */
    public void login(View view) {
        viewModel.login().observe(this, this::loadSuccess);
    }

    /**
     * 手机号快速注册
     */
    public void goRegister(View view) {
        RegisterActivity.start(LoginActivity.this);
    }

    /**
     * 用户找回密码
     */
    public void goRecover(View view) {
        RecoverActivity.start(LoginActivity.this);
    }

    /**
     * 注册或登录成功
     */
    public void loadSuccess(Boolean aBoolean) {
        if (aBoolean != null && aBoolean) {
            finish();
        }
    }

    /**
     * 进入页面
     */
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

}
