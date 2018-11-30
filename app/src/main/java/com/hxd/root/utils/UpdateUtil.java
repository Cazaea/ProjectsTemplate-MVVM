package com.hxd.root.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.hxd.root.BuildConfig;
import com.hxd.root.bean.UpdateBean;
import com.hxd.root.http.HttpClient;

import java.lang.reflect.Field;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cazaea
 * @data 2018/2/8
 * @Description 更新提醒
 */
public class UpdateUtil {

    final private static String APK_UPDATE_ID = "5bd02d58959d696348d0d0f3";
    final private static String ACCOUNT_UPDATE_TOKEN = "5c5cbd9772b995661a3f00b72e429233";

    /**
     * 检查更新
     * BuildConfig.UPDATE_TOKEN
     *
     * @param activity
     * @param isShowToast 是否弹出SnackBar提示
     */
    public static void check(final Activity activity, final boolean isShowToast) {

        HttpClient.Builder.getUpdateServer().checkUpdate(APK_UPDATE_ID, ACCOUNT_UPDATE_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isShowToast) {
                            ToastUtil.showLong("已是最新版本~");
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final UpdateBean updateBean) {

                        if (Integer.valueOf(updateBean.getVersion()) <= BuildConfig.VERSION_CODE) {
                            if (isShowToast) {
                                ToastUtil.showLong("已是最新版本~");
//                                Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
                            }
                            return;
                        }

//                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                        // 返回无效
//                        builder.setCancelable(false);
//                        builder.setTitle("发现新版本")
//                                .setMessage(String.format("版本号: %s\n更新时间: %s\n更新内容:\n%s",
//                                        updateBean.getVersionShort(),
//                                        TimeUtil.formatDataTime(Long.valueOf(updateBean.getUpdated_at() + "000")),
//                                        updateBean.getChangelog()));
//                        builder.setNegativeButton("暂不更新", null);
//                        builder.setPositiveButton("立即下载", (dialogInterface, i) -> BaseTools.openLink(activity, updateBean.getInstallUrl()));
//                        builder.show();

//                        StringBuilder _builder = new StringBuilder();
//                        String changeLog = updateBean.getChangelog();
//                        if (changeLog != null && changeLog.contains("\n")) {
//                            String[] base = changeLog.split("\n");
//                            for (String info : base) {
//                                _builder.append(String.format("%s\n", info));
//                            }
//                        }

                        AlertDialog dialog = new AlertDialog.Builder(activity)
                                .setCancelable(false)
                                .setTitle("发现新版本")
                                .setMessage(String.format("更新版本: %s\n更新时间: %s\n\n更新内容:\n%s",
                                        updateBean.getVersionShort(),
                                        TimeUtil.formatDataTime(Long.valueOf(updateBean.getUpdated_at() + "000")),
                                        updateBean.getChangelog()))
                                .setNegativeButton("暂不更新", null)
                                .setPositiveButton("立即下载", (dialogInterface, i) -> BaseTools.openLink(activity, updateBean.getInstallUrl()))
                                .create();
                        dialog.show();

                        // 修改“确认”、“取消”按钮的字体大小
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(16);
                        try {
                            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                            mAlert.setAccessible(true);
                            Object mAlertController = mAlert.get(dialog);
                            // 通过反射修改title字体大小和颜色
                            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                            mTitle.setAccessible(true);
                            TextView mTitleView = (TextView) mTitle.get(mAlertController);
                            mTitleView.setTextSize(22);
                            mTitleView.setTextColor(Color.BLACK);
                            // 通过反射修改message字体大小和颜色
                            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                            mMessage.setAccessible(true);
                            TextView mMessageView = (TextView) mMessage.get(mAlertController);
                            mMessageView.setTextSize(15);
                            mMessageView.setTextColor(Color.DKGRAY);
                        } catch (IllegalAccessException | NoSuchFieldException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
//        addSubscription(get);
    }
}
