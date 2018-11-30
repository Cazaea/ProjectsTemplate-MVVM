package com.hxd.root.bean.login;

import com.hxd.root.data.room.User;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description
 */

public class LoginBean {

    /**
     * {
     * "code":1000,
     * "msg":"登录成功",
     * "data":{}
     * }
     */

    private User data;
    private int code;
    private String msg;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String errorMsg) {
        this.msg = errorMsg;
    }

    public static class DataBean {

        /**
         * id : 2
         * account : 18352867739
         * nick_name : 18352867739
         * pic_path : http://imgcdn.huixdou.com/
         * real_name : 王伟
         * referee_tel : null
         * phone : 18352867739
         * idcard :
         * gender : 男
         * create_time : 1525512016
         * status : 0
         * version : 1.0
         * platform : iOS
         * device_uid : A9D5E8CF-5118-4013-B124-D28CCFDCA874
         * weixin_flag : 0
         * last_login_time : 1527575336
         * remark :
         * type_code : 0
         * type_name : 普通会员
         * bank_card : 6222021104007744930
         * card_phone : 18352867739
         * open_bank : 中国工商银行
         * card_user : 王伟
         * balance0 : 1.19
         * balance1 : 0.00
         * balance2 : 0.00
         * balance3 : 0.00
         * money_history : 1.19
         * token : cDR3cXo5YlJ0aWp3RHpUU0VhSm9RNkxIUFc2RHJOcUlkTkFXNDVkUUNqOGRjNE9Id3NQWGY4OFZpV0c5WHhjSlg3NzRKRGVoQ2Y0PQ_c_c
         */

        private String id;
        private String account;
        private String nick_name;
        private String pic_path;
        private String real_name;
        private String referee_tel;
        private String phone;
        private String idcard;
        private String gender;
        private String create_time;
        private String status;
        private String version;
        private String platform;
        private String device_uid;
        private String weixin_flag;
        private String last_login_time;
        private String remark;
        private String type_code;
        private String type_name;
        private String bank_card;
        private String card_phone;
        private String open_bank;
        private String card_user;
        private String balance0;
        private String balance1;
        private String balance2;
        private String balance3;
        private String money_history;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getReferee_tel() {
            return referee_tel;
        }

        public void setReferee_tel(String referee_tel) {
            this.referee_tel = referee_tel;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getDevice_uid() {
            return device_uid;
        }

        public void setDevice_uid(String device_uid) {
            this.device_uid = device_uid;
        }

        public String getWeixin_flag() {
            return weixin_flag;
        }

        public void setWeixin_flag(String weixin_flag) {
            this.weixin_flag = weixin_flag;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public String getCard_phone() {
            return card_phone;
        }

        public void setCard_phone(String card_phone) {
            this.card_phone = card_phone;
        }

        public String getOpen_bank() {
            return open_bank;
        }

        public void setOpen_bank(String open_bank) {
            this.open_bank = open_bank;
        }

        public String getCard_user() {
            return card_user;
        }

        public void setCard_user(String card_user) {
            this.card_user = card_user;
        }

        public String getBalance0() {
            return balance0;
        }

        public void setBalance0(String balance0) {
            this.balance0 = balance0;
        }

        public String getBalance1() {
            return balance1;
        }

        public void setBalance1(String balance1) {
            this.balance1 = balance1;
        }

        public String getBalance2() {
            return balance2;
        }

        public void setBalance2(String balance2) {
            this.balance2 = balance2;
        }

        public String getBalance3() {
            return balance3;
        }

        public void setBalance3(String balance3) {
            this.balance3 = balance3;
        }

        public String getMoney_history() {
            return money_history;
        }

        public void setMoney_history(String money_history) {
            this.money_history = money_history;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
