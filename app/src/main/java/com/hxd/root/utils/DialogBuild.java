package com.hxd.root.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hxd.root.R;
import com.hxd.root.data.UserUtil;
import com.hxd.root.data.room.Injection;
import com.hxd.root.data.room.User;
import com.hxd.root.data.room.UserDataCallback;
import com.hxd.root.view.OnLoginListener;

import java.util.Objects;

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

    public static void show(View v, String title, String buttonText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("提示");
        builder.setMessage(title);
        builder.setPositiveButton(buttonText, clickListener);
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

    /**
     * 编辑收藏网址
     */
    public static void show(View v, String name, String link, OnEditClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("编辑");
        View inflate = View.inflate(v.getContext(), R.layout.dialog_eidt_url, null);
        builder.setView(inflate);
        AppCompatEditText etName = inflate.findViewById(R.id.et_name);
        AppCompatEditText etLink = inflate.findViewById(R.id.et_link);
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setSelection(name.length());
        }
        etLink.setText(link);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("编辑完成", (dialog, which) -> {
            String name1 = Objects.requireNonNull(etName.getText()).toString().trim();
            String link1 = Objects.requireNonNull(etLink.getText()).toString().trim();
            if (TextUtils.isEmpty(name1)) {
                ToastUtil.showLong("请输入名称");
                return;
            }
            if (TextUtils.isEmpty(link1)) {
                ToastUtil.showLong("请输入链接");
                return;
            }
            listener.onClick(name1, link1);
        });
        builder.show();
    }

    public interface OnEditClickListener {
        void onClick(String name, String link);
    }

}
