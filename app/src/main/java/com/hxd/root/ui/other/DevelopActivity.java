package com.hxd.root.ui.other;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hxd.root.R;
import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseActivity;
import com.hxd.root.databinding.ActivityDevelopBinding;
import com.hxd.root.utils.DebugUtil;
import com.hxd.root.utils.SPUtils;
import com.hxd.root.vmodel.other.DevelopViewModel;
import com.thejoyrun.router.RouterActivity;

@RouterActivity("develop")
public class DevelopActivity extends BaseActivity<ActivityDevelopBinding> implements Switch.OnCheckedChangeListener {

    private DevelopViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);
        setTitle("开发人员选项");
        setBarColor(R.color.colorDevelopToolbar);
        showContentView();

        viewModel = new DevelopViewModel(this);
        bindingView.setDevelop(viewModel);
        // 监听Switch按钮
        bindingView.switch1.setOnCheckedChangeListener(this);
        bindingView.switch1.setChecked(DebugUtil.isDebug);
    }

    public void selectBeta(View view) {
        viewModel.selectBeta();
    }

    public void selectFormal(View view) {
        viewModel.selectFormal();
    }

    public void defineSelect(View view) {
        viewModel.defineSelect();
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 更新数据
        DebugUtil.isDebug = isChecked;
        // 分情况处理
        if (isChecked) {
            bindingView.llAllConfig.setVisibility(View.VISIBLE);
        } else {
            bindingView.llAllConfig.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        SPUtils.putBoolean(Constants.IS_DEBUG, DebugUtil.isDebug);
        super.onDestroy();
    }

}
