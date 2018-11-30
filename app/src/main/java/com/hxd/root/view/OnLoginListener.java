package com.hxd.root.view;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description 登录的回调接口
 */
public interface OnLoginListener {

    // 账号主登录接口
    void loginMain();

    // 第三方GitHub账号登录
    void loginGitHub();
}
