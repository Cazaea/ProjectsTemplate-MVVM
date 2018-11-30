package com.hxd.root.bean.product.buy;

import android.databinding.BaseObservable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/8/24
 * 邮 箱： wistorm@sina.com
 */
public class ProductInfoBean extends BaseObservable {

    /**
     * code : 1000
     * msg : 获取成功
     * data : {"buy_money":"47162","annual_rate":"6.66"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * buy_money : 47162
         * annual_rate : 6.66
         */

        private String buy_money;
        private String annual_rate;

        public String getBuy_money() {
            return buy_money;
        }

        public void setBuy_money(String buy_money) {
            this.buy_money = buy_money;
        }

        public String getAnnual_rate() {
            return annual_rate;
        }

        public void setAnnual_rate(String annual_rate) {
            this.annual_rate = annual_rate;
        }
    }
}
