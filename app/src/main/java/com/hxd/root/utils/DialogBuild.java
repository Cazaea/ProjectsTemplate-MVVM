package com.hxd.root.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.hxd.root.R;
import com.hxd.root.data.UserUtil;
import com.hxd.root.data.room.Injection;
import com.hxd.root.data.room.User;
import com.hxd.root.data.room.UserDataCallback;
import com.hxd.root.view.OnLoginListener;

/**
 * @author Cazaea
 * @data 2018/2/27
 * @Description
 */

public class DialogBuild {

    /**
     * 显示单行文字的AlertDialog
     */
    public static void show(View v, String title, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
        TextView titleTop = view.findViewById(R.id.title_top);
        titleTop.setText(title);
        builder.setView(view);
        builder.setPositiveButton("查看详情", clickListener);
        builder.show();
    }

    /**
     * 显示选项的AlertDialog
     */
    public static void showItems(View v, String content) {
        String[] items = {"复制", "分享"};
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    BaseTools.copy(content);
                    ToastUtil.showShort("复制成功");
                    break;
                case 1:
                    ShareUtils.share(v.getContext(), content);
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    /**
     * 用于账号登录
     */
    public static void showItems(View v, OnLoginListener listener) {
        Injection.get().getSingleBean(new UserDataCallback() {
            @Override
            public void onDataNotAvailable() {
                String[] items = {"GitHub账号", "濠寓账号"};
                showLoginDialog(v, items, listener, false);
            }

            @Override
            public void getData(User bean) {
                String[] items = {"GitHub账号", "退出账号（" + bean.getAccount() + "）"};
                showLoginDialog(v, items, listener, true);
            }
        });

    }

    /**
     * 弹出登录窗口
     *
     * @param view
     * @param items    显示信息
     * @param listener 登录回调
     * @param isLogin  是否已登陆
     */
    private static void showLoginDialog(View view, String[] items, OnLoginListener listener, boolean isLogin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("账号登录");
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    listener.loginGitHub();
                    break;
                case 1:
                    if (isLogin) {
                        Injection.get().deleteAllData();
                        UserUtil.handleLoginFailure();
                        ToastUtil.showLong("退出成功");
                    } else {
                        listener.loginMain();
                    }
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

}
