package com.hxd.root.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hxd.root.R;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.databinding.ActivityRecoverBinding;
import com.hxd.root.vmodel.login.LoginNavigator;
import com.hxd.root.vmodel.login.RecoverViewModel;
import com.thejoyrun.router.RouterActivity;

/**
 * @author Cazaea
 */
@RouterActivity("recover")
public class RecoverActivity extends BaseActivity<ActivityRecoverBinding> implements LoginNavigator {

    private RecoverViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        setTitle("找回密码");
        showContentView();

        viewModel = new RecoverViewModel(this);
        viewModel.setNavigator(this);
        bindingView.setRecover(viewModel);
    }

    public void getCode(View view) {
        viewModel.getCode();
    }

    public void recover(View view) {
        viewModel.recover();
    }

    /**
     * 注册或登录成功
     */
    @Override
    public void loadSuccess() {
        finish();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, RecoverActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
