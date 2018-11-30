package com.hxd.root.vmodel.login;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.bean.CommonBean;
import com.hxd.root.bean.login.RegisterBean;
import com.hxd.root.http.HttpClient;
import com.hxd.root.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description
 */

public class RegisterViewModel extends ViewModel {

    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> code = new ObservableField<>();
    public final ObservableField<String> referee_tel = new ObservableField<>();

    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private LoginNavigator navigator;

    public RegisterViewModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void setNavigator(LoginNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * 获取验证码
     */
    public void getCode() {
        if (!verifyPhoneInfo()) {
            return;
        }
        HttpClient.Builder.getBaseServer().getCode(account.get(), Constants.CODE_TYPE_REGISTER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonBean bean) {
                        if (bean != null && bean.getData() != null && bean.getCode() == 1000) {
                            ToastUtil.showShort(bean.getMsg());
                        } else {
                            if (bean != null) {
                                ToastUtil.showLong(bean.getMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 用户注册
     */
    public void register() {
        if (!verifyRegisterInfo()) {
            return;
        }
        HttpClient.Builder.getBaseServer().register(account.get(), password.get(), code.get(), referee_tel.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean bean) {
                        if (bean != null && bean.getData() != null && bean.getCode() == 1000) {
                            ToastUtil.showShort(bean.getMsg());
                        } else {
                            if (bean != null) {
                                ToastUtil.showLong(bean.getMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 获取验证码验证
     *
     * @return 是否满足操作
     */
    private boolean verifyPhoneInfo() {
        if (TextUtils.isEmpty(account.get())) {
            ToastUtil.showShort("请输入手机号");
            return false;
        }
        return true;
    }

    /**
     * 验证注册内容
     *
     * @return 是否满足操作
     */
    private boolean verifyRegisterInfo() {
        if (TextUtils.isEmpty(account.get())) {
            ToastUtil.showShort("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showShort("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(code.get())) {
            ToastUtil.showShort("请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(referee_tel.get())) {
            ToastUtil.showShort("请输入邀请人手机号");
            return false;
        }
        return true;
    }

    public void onDestroy() {
        navigator = null;
    }
}
