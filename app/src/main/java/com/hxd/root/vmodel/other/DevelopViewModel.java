package com.hxd.root.vmodel.other;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.hxd.root.app.Constants;
import com.hxd.root.app.RootApplication;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.http.HttpUtils;
import com.hxd.root.utils.SPUtils;
import com.hxd.root.utils.ToastUtil;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description 开发者模式, 可切换主服务器
 */

public class DevelopViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;

    public final ObservableField<String> server = new ObservableField<>();

    public DevelopViewModel(BaseActivity activity) {
        this.activity = activity;
        server.set(HttpUtils.API_ROOT);
    }

//    @Bindable
//    public boolean getDebugStatus(){
//        return DebugUtil.isDebug;
//    }
//
//    @Bindable
//    public int getSaleVisible() {
//        return DebugUtil.isDebug ? View.VISIBLE : View.GONE;
//    }

    /**
     * 选择测试服务器
     */
    public void selectBeta() {
        server.set(HttpUtils.API_BETA);
    }

    /**
     * 选择正式服务器
     */
    public void selectFormal() {
        server.set(HttpUtils.API_FORMAL);
    }

    /**
     * 确定所选服务器
     */
    public void defineSelect() {
        if (verifyServer()) {
            // 保存新数据
            SPUtils.putString(Constants.CUSTOM_SERVER, server.get());
            // 弹框提示
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            // 返回无效
            builder.setCancelable(false)
                    .setTitle("重启应用")
                    .setMessage(String.format("服务器的地址已改为\n\t%s", server.get()))
                    .setNegativeButton("立即重启", (dialogInterface, i) ->
                            // 重启应用
                            RootApplication.getInstance().reStartApp());
            builder.show();
        } else {
            ToastUtil.showShort("请选择或输入服务器！");
        }
    }

    /**
     * 服务器地址不为空
     */
    private boolean verifyServer() {
        return !TextUtils.isEmpty(server.get());
    }

}
