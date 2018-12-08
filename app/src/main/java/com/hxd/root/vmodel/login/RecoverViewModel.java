package com.hxd.root.vmodel.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.app.Constants;
import com.hxd.root.bean.CommonBean;
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

public class RecoverViewModel extends ViewModel {

    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> code = new ObservableField<>();

    /**
     * 获取验证码
     */
    public void getCode() {
        if (!verifyPhoneInfo()) {
            return;
        }
        HttpClient.Builder.getBaseServer()
                .getCode(account.get(), Constants.CODE_TYPE_RECOVER)
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
     * 用户找回密码
     */
    public MutableLiveData<Boolean> recover() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyRecoverInfo()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getBaseServer()
                .findPsw(account.get(), code.get(), password.get())
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
                            data.setValue(true);
                        } else {
                            if (bean != null) {
                                ToastUtil.showLong(bean.getMsg());
                            }
                            data.setValue(false);
                        }
                    }
                });
        return data;
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
    private boolean verifyRecoverInfo() {
        if (TextUtils.isEmpty(account.get())) {
            ToastUtil.showShort("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(code.get())) {
            ToastUtil.showShort("请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showShort("请输入新密码");
            return false;
        }
        return true;
    }

}
