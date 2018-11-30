package com.hxd.root.vmodel.login;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.bean.login.LoginBean;
import com.hxd.root.data.UserUtil;
import com.hxd.root.data.room.Injection;
import com.hxd.root.http.HttpClient;
import com.hxd.root.utils.ToastUtil;
import com.thejoyrun.router.Router;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description
 */

public class LoginViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private LoginNavigator navigator;

    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void setNavigator(LoginNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * 用户登录
     */
    public void login() {
        if (!verifyUserInfo()) {
            return;
        }
        HttpClient.Builder.getBaseServer()
                .login(account.get(), password.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        if (bean != null && bean.getData() != null && bean.getCode() == 1000) {
                            Injection.get().addData(bean.getData());
                            UserUtil.handleLoginSuccess();
                            navigator.loadSuccess();
                        } else {
                            if (bean != null) {
                                ToastUtil.showLong(bean.getMsg());
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        ToastUtil.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                .subscribe(new BaseSubscriber<LoginBean>(activity) {
//                    @Override
//                    public void onError(ResponseThrowable e) {
//                        ToastUtil.showShort(e.getCode()+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResult(LoginBean bean) {
//                        Injection.get().addData(bean.getData());
//                        UserUtil.handleLoginSuccess();
//                        navigator.loadSuccess();
//                    }
//
//                });
    }

    /**
     * 用户找回密码
     */
    public void goRecover() {
        Router.startActivity(activity, Constants.ROUTER_TOTAL_HEAD + "recover");
    }

    /**
     * 手机号快速注册
     */
    public void goRegister() {
        Router.startActivity(activity, Constants.ROUTER_TOTAL_HEAD + "register");
    }

    /**
     * 登录验证内容
     *
     * @return 是否满足操作
     */
    private boolean verifyUserInfo() {
        if (TextUtils.isEmpty(account.get())) {
            ToastUtil.showShort("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showShort("请输入密码");
            return false;
        }
        return true;
    }

    public void onDestroy() {
        navigator = null;
    }
}
