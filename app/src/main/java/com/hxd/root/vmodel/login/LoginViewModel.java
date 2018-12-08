package com.hxd.root.vmodel.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.data.UserUtil;
import com.hxd.root.data.room.Injection;
import com.hxd.root.data.room.User;
import com.hxd.root.http.HttpClient;
import com.hxd.root.http.base.BaseSubscriber;
import com.hxd.root.http.exception.ResponseThrowable;
import com.hxd.root.http.rxutils.RxUtils;
import com.hxd.root.utils.ToastUtil;

/**
 * @author Cazaea
 * @date 2018/5/7
 * @Description
 */
public class LoginViewModel extends ViewModel {

    /**
     * 表单数据
     */
    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    /**
     * 用户登录
     */
    public MutableLiveData<Boolean> login() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyUserInfo()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getBaseServer()
                .login(account.get(), password.get())
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new BaseSubscriber<User>() {
                    @Override
                    public void onError(ResponseThrowable e) {
                        ToastUtil.showShort(e.getMessage());
                        data.setValue(false);
                    }

                    @Override
                    public void onResult(User pUser) {
                        Injection.get().addData(pUser);
                        UserUtil.handleLoginSuccess();
                        data.setValue(true);
                    }
                });
        return data;
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

}
