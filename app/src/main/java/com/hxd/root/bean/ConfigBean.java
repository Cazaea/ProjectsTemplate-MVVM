package com.hxd.root.bean;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * 作 者： Cazaea
 * 日 期： 2018/8/24
 * 邮 箱： wistorm@sina.com
 */
public class ConfigBean extends BaseObservable {

    /**
     * code : 1000
     * msg : 获取成功
     * data : {"start_pic_list":[{"title":"首批用户","link_url":"","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1530146829689.jpg"}],"about_us_url":"http://lease.nfc-hxd.com/app/home/aboutUs?user_id=1006&token=abcd","customer_url":"http://p.qiao.baidu.com/cps/chat?siteId=11306222&userId=24558618","agreement_url":"http://lease.nfc-hxd.com/app/home/agreement?user_id=1006&token=abcd","business_url":"http://lease.nfc-hxd.com/app/vmmember/selllog?user_id=1006&token=abcd","recharge_url":"http://lease.nfc-hxd.com/app/ororder/loadPage?user_id=1006&token=abcd","cash_url":"http://lease.nfc-hxd.com/app/vmmembercash/loadPage?user_id=1006&token=abcd","message_url":"http://lease.nfc-hxd.com/app/apmessage/loadPage?user_id=1006&token=abcd","account_url":"http://lease.nfc-hxd.com/app/vmmember/detailIndex?user_id=1006&token=abcd","sign_url":"http://lease.nfc-hxd.com/app/vmmember/signIndex?user_id=1006&token=abcd","register_success_url":"http://lease.nfc-hxd.com/app/login/registerSuccess?user_id=1006&token=abcd","invited_url":"http://lease.nfc-hxd.com/app/Vmmemberreward/index?user_id=1006&token=abcd","audit_flag":0}
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
         * start_pic_list : [{"title":"首批用户","link_url":"","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1530146829689.jpg"}]
         * about_us_url : http://lease.nfc-hxd.com/app/home/aboutUs?user_id=1006&token=abcd
         * customer_url : http://p.qiao.baidu.com/cps/chat?siteId=11306222&userId=24558618
         * agreement_url : http://lease.nfc-hxd.com/app/home/agreement?user_id=1006&token=abcd
         * business_url : http://lease.nfc-hxd.com/app/vmmember/selllog?user_id=1006&token=abcd
         * recharge_url : http://lease.nfc-hxd.com/app/ororder/loadPage?user_id=1006&token=abcd
         * cash_url : http://lease.nfc-hxd.com/app/vmmembercash/loadPage?user_id=1006&token=abcd
         * message_url : http://lease.nfc-hxd.com/app/apmessage/loadPage?user_id=1006&token=abcd
         * account_url : http://lease.nfc-hxd.com/app/vmmember/detailIndex?user_id=1006&token=abcd
         * sign_url : http://lease.nfc-hxd.com/app/vmmember/signIndex?user_id=1006&token=abcd
         * register_success_url : http://lease.nfc-hxd.com/app/login/registerSuccess?user_id=1006&token=abcd
         * invited_url : http://lease.nfc-hxd.com/app/Vmmemberreward/index?user_id=1006&token=abcd
         * audit_flag : 0
         */

        private String about_us_url;
        private String customer_url;
        private String agreement_url;
        private String business_url;
        private String recharge_url;
        private String cash_url;
        private String message_url;
        private String account_url;
        private String sign_url;
        private String register_success_url;
        private String invited_url;
        private int audit_flag;
        private List<StartPicListBean> start_pic_list;

        public String getAbout_us_url() {
            return about_us_url;
        }

        public void setAbout_us_url(String about_us_url) {
            this.about_us_url = about_us_url;
        }

        public String getCustomer_url() {
            return customer_url;
        }

        public void setCustomer_url(String customer_url) {
            this.customer_url = customer_url;
        }

        public String getAgreement_url() {
            return agreement_url;
        }

        public void setAgreement_url(String agreement_url) {
            this.agreement_url = agreement_url;
        }

        public String getBusiness_url() {
            return business_url;
        }

        public void setBusiness_url(String business_url) {
            this.business_url = business_url;
        }

        public String getRecharge_url() {
            return recharge_url;
        }

        public void setRecharge_url(String recharge_url) {
            this.recharge_url = recharge_url;
        }

        public String getCash_url() {
            return cash_url;
        }

        public void setCash_url(String cash_url) {
            this.cash_url = cash_url;
        }

        public String getMessage_url() {
            return message_url;
        }

        public void setMessage_url(String message_url) {
            this.message_url = message_url;
        }

        public String getAccount_url() {
            return account_url;
        }

        public void setAccount_url(String account_url) {
            this.account_url = account_url;
        }

        public String getSign_url() {
            return sign_url;
        }

        public void setSign_url(String sign_url) {
            this.sign_url = sign_url;
        }

        public String getRegister_success_url() {
            return register_success_url;
        }

        public void setRegister_success_url(String register_success_url) {
            this.register_success_url = register_success_url;
        }

        public String getInvited_url() {
            return invited_url;
        }

        public void setInvited_url(String invited_url) {
            this.invited_url = invited_url;
        }

        public int getAudit_flag() {
            return audit_flag;
        }

        public void setAudit_flag(int audit_flag) {
            this.audit_flag = audit_flag;
        }

        public List<StartPicListBean> getStart_pic_list() {
            return start_pic_list;
        }

        public void setStart_pic_list(List<StartPicListBean> start_pic_list) {
            this.start_pic_list = start_pic_list;
        }

        public static class StartPicListBean {
            /**
             * title : 首批用户
             * link_url :
             * pic_path : http://imgcdn.huixdou.com/uploads/temp/201806/1530146829689.jpg
             */

            private String title;
            private String link_url;
            private String pic_path;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public String getPic_path() {
                return pic_path;
            }

            public void setPic_path(String pic_path) {
                this.pic_path = pic_path;
            }
        }
    }
}
