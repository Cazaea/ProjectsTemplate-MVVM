package com.hxd.root.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hxd.root.R;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.databinding.ActivityRegisterBinding;
import com.hxd.root.vmodel.login.RegisterViewModel;
import com.thejoyrun.router.RouterActivity;

/**
 * @author Cazaea
 */
@RouterActivity("register")
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding>{

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");
        showContentView();

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        bindingView.setRegister(viewModel);
    }

    public void getCode(View view) {
        viewModel.getCode();
    }

    public void register(View view) {
        viewModel.register().observe(this,this::loadSuccess);
    }

    /**
     * 注册或登录成功
     */
    public void loadSuccess(Boolean aBoolean) {
        if (aBoolean != null && aBoolean) {
            finish();
        }
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
    }

}
