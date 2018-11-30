//package com.hxd.root.vmodel.login;
//
//import android.annotation.SuppressLint;
//import android.arch.lifecycle.ViewModel;
//import android.databinding.ObservableField;
//import android.util.Log;
//
//import com.hxd.root.base.BaseActivity;
//import com.hxd.root.bean.login.LoginBean;
//import com.hxd.root.data.UserUtil;
//import com.hxd.root.data.room.Injection;
//import com.hxd.root.http.HttpClient;
//import com.hxd.root.utils.ToastUtil;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import rx.Subscription;
//
///**
// * 作 者： Cazaea
// * 日 期： 2018/11/19
// * 邮 箱： wistorm@sina.com
// */
//public class TestViewModel extends ViewModel {
//
//    String TAG = "Hello";
//
//    @SuppressLint("StaticFieldLeak")
//    private BaseActivity activity;
//    private LoginNavigator navigator;
//
//    public final ObservableField<String> account = new ObservableField<>();
//    public final ObservableField<String> password = new ObservableField<>();
//
//    public TestViewModel(BaseActivity activity) {
//        this.activity = activity;
//    }
//
//    public void setNavigator(LoginNavigator navigator) {
//        this.navigator = navigator;
//    }
//
//    private void login() {
//        Subscription subscribe = HttpClient.Builder.getBaseServer().login(account.get(), password.get())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe(new rx.Observer<LoginBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(LoginBean bean) {
//                        if (bean != null && bean.getData() != null && bean.getCode() == 1000) {
//                            Injection.get().addData(bean.getData());
//                            UserUtil.handleLoginSuccess();
//                            navigator.loadSuccess();
//                        } else {
//                            if (bean != null) {
//                                ToastUtil.showLong(bean.getMsg());
//                            }
//                        }
//                    }
//                });
//        activity.addSubscription(subscribe);
//    }
//
//}