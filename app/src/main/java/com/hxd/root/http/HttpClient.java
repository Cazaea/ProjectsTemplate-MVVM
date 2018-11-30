package com.hxd.root.http;

import com.hxd.root.bean.CommonBean;
import com.hxd.root.bean.ConfigBean;
import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.bean.lease.LeaseBean;
import com.hxd.root.bean.mine.PayFrontBean;
import com.hxd.root.bean.login.RegisterBean;
import com.hxd.root.bean.UpdateBean;
import com.hxd.root.bean.product.ProductBean;
import com.hxd.root.bean.product.buy.ProductInfoBean;
import com.hxd.root.bean.product.pay.OrderInfoBean;
import com.hxd.root.bean.product.pay.PayChannelBean;
import com.hxd.root.bean.product.pay.PayInfoBean;
import com.hxd.root.bean.login.LoginBean;
import com.hxd.root.data.room.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Cazaea
 * @date 16/11/21
 * <p>
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {

        /**
         * 选择服务器
         */
        public static HttpClient getBaseServer() {
            return HttpUtils.getInstance().getServer(HttpClient.class, HttpUtils.API_ROOT);
        }

        /**
         * 第三方更新服务器
         */
        public static HttpClient getUpdateServer() {
            return HttpUtils.getInstance().getServer(HttpClient.class, HttpUtils.API_UPDATE);
        }
    }



    //********************************************************************************************************************//
    //****************************************************主体UI展示******************************************************//
    //********************************************************************************************************************//

    /**
     * 资讯信息
     */
    @GET("/app/home/loadInformation")
    Observable<HomeInfoBean> getHomeInfo();

    /**
     * 项目信息
     */
    @GET("/app/home/loadProject")
    Observable<ProductBean> getProductInfo();

    /**
     * 租赁信息
     */
    @GET("/app/lehouse/list")
    Observable<LeaseBean> getLeaseInfo();

    /**
     * 个人信息
     */
    @GET("/app/vmmember/getData")
    Observable<User> getUserInfo();

    //********************************************************************************************************************//
    //****************************************************登录相关操作*****************************************************//
    //********************************************************************************************************************//

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("/app/login/check")
    Observable<LoginBean> login(@Field("account") String account, @Field("password") String password);

    /**
     * 注册
     *
     * @param account     账号
     * @param password    密码
     * @param code        验证码
     * @param referee_tel 推荐人手机号
     */
    @FormUrlEncoded
    @POST("/app/login/register")
    Observable<RegisterBean> register(@Field("account") String account, @Field("password") String password, @Field("code") String code, @Field("referee_tel") String referee_tel);

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @param type  验证码类型：0注册 1修改密码 2找回密码 3付款 4绑定银行卡
     */
//    @FormUrlEncoded
    @GET("/app/login/getCode")
    Observable<CommonBean> getCode(@Query("phone") String phone, @Query("type") String type);

    /**
     * 修改登录密码
     *
     * @param old_pwd 旧密码
     * @param new_pwd 新密码
     */
    @FormUrlEncoded
    @POST("/app/vmmember/changeLoginPassword")
    Observable<CommonBean> changePsw(@Field("old_pwd") String old_pwd, @Field("new_pwd") String new_pwd);

    /**
     * 找回登录密码
     *
     * @param account  账号
     * @param code     验证码
     * @param password 新密码
     */
    @FormUrlEncoded
    @POST("/app/login/findPassword")
    Observable<CommonBean> findPsw(@Field("account") String account, @Field("code") String code, @Field("password") String password);

    //********************************************************************************************************************//
    //****************************************************产品操作相关*****************************************************//
    //********************************************************************************************************************//

    /**
     * 购买
     *
     * @param product_id 产品ID
     * @param money      购买金额
     */
    @FormUrlEncoded
    @POST("/app/ororder/buy")
    Observable<CommonBean> buy(@Field("product_id") String product_id, @Field("money") String money);

    /**
     * 赎回
     *
     * @param product_id 产品ID
     * @param money      赎回金额
     */
    @FormUrlEncoded
    @POST("/app/ororder/redeem")
    Observable<CommonBean> redeem(@Field("product_id") String product_id, @Field("money") String money);

    /**
     * 提现
     *
     * @param money 提现金额
     */
    @FormUrlEncoded
    @POST("/app/vmmembercash/cash")
    Observable<CommonBean> withdraw(@Field("money") String money);

    /**
     * 充值
     *
     * @param money 充值金额
     */
    @FormUrlEncoded
    @POST("/app/ororder/recharge")
    Observable<PayFrontBean> recharge(@Field("money") String money);

    /**
     * 支付订单确认
     *
     * @param product_id 产品ID
     * @param money      订单金额（二次确认，避免他人操作影响订单支付）
     * @description 如果余额够支付：
     * <p>                  <1>通过本地支付调用购买
     * <p>                  <2>如果操作中，余额变化，不够支付，判断接口调用成功后是否返回URL
     * <p>                      1>是：跳转第三方支付
     * <p>                      2>否：余额支付成功
     */
    @FormUrlEncoded
    @POST("/app/ororder/confirm")
    Observable<PayFrontBean> orderConfirm(@Field("product_id") String product_id, @Field("money") String money);

    /**
     * 产品相关信息(可购买金额&七日年化收益率)
     *
     * @param product_id 产品ID
     */
    @FormUrlEncoded
    @POST("/app/ororder/getBuyInfo")
    Observable<ProductInfoBean> productInfo(@Field("product_id") String product_id);

    //********************************************************************************************************************//
    //****************************************************第三方支付相关****************************************************//
    //********************************************************************************************************************//

    /**
     * 支付, 返回订单详情(返回amount分)
     *
     * @param order_number 订单编号
     * @param pay_channel  支付渠道
     */
    @FormUrlEncoded
    @POST("/app/payment/payData")
    Observable<PayInfoBean> pay(@Field("order_number") String order_number, @Field("pay_channel") String pay_channel);

    /**
     * 查询支付订单详情
     *
     * @param order_number 订单编号(返回money元)
     */
    @FormUrlEncoded
    @POST("/app/payment/payData")
    Observable<OrderInfoBean> orderInfo(@Field("order_number") String order_number);

    /**
     * 获取支付渠道
     */
    @FormUrlEncoded
    @POST("/app/payment/payData")
    Observable<PayChannelBean> payChannel();

    //********************************************************************************************************************//
    //****************************************************配置信息相关*****************************************************//
    //********************************************************************************************************************//

    /**
     * 获取配置信息
     */
    @GET("/app/config/getConfigData")
    Observable<ConfigBean> getConfigInfo();

    /**
     * 版本检测
     */
    @GET("/app/config/checkUpdate")
    Observable<com.hxd.root.bean.mine.UpdateBean> updateInfo();

    /**
     * @param id       应用id
     * @param apiToken token
     */
    @GET("latest/{id}")
    Observable<UpdateBean> checkUpdate(@Path("id") String id, @Query("api_token") String apiToken);

}