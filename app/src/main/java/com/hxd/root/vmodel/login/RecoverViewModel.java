package com.hxd.root.vmodel.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.app.Constants;
import com.hxd.root.bean.CommonBean;
import com.hxd.root.bean.login.ImageCodeBean;
import com.hxd.root.http.HttpClient;
import com.hxd.root.http.base.BaseSubscriber;
import com.hxd.root.http.exception.ResponseThrowable;
import com.hxd.root.http.rxutils.RxUtils;
import com.hxd.root.utils.ToastUtil;

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
                .getImageCode(account.get(), Constants.CODE_TYPE_RECOVER_PSW)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new BaseSubscriber<ImageCodeBean>() {
                    @Override
                    public void onError(ResponseThrowable e) {
                        ToastUtil.showShort(e.getMessage());
                    }

                    @Override
                    public void onResult(ImageCodeBean pCodeBean) {
                        ToastUtil.showShort("图形验证码获取成功！\n" + pCodeBean.getId() + "\n" + pCodeBean.getPic_path());
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
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new BaseSubscriber<CommonBean>() {
                    @Override
                    public void onError(ResponseThrowable e) {
                        ToastUtil.showShort(e.getMessage());
                        data.setValue(false);
                    }

                    @Override
                    public void onResult(CommonBean pCommonBean) {
                        ToastUtil.showShort("" + pCommonBean);
                        data.setValue(true);
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
