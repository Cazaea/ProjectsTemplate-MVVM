package com.hxd.root.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Cazaea
 * @date 2017/12/30
 * @description 注意：
 * 1、建值如果为 String id; 使用getWid(),则会报错！！！
 * 2、有一个key就要有对应的get和set
 * 3、List<>不能简单使用，需要处理
 */

@Entity(tableName = "User")
public class User {

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

    /**
     * 通过主键来标识删除的
     * 主键递增：PrimaryKey(autoGenerate = true)
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "account")
    private String account;
    @ColumnInfo(name = "nick_name")
    private String nick_name;
    @ColumnInfo(name = "pic_path")
    private String pic_path;
    @ColumnInfo(name = "real_name")
    private String real_name;
    @ColumnInfo(name = "referee_tel")
    private String referee_tel;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "idcard")
    private String idcard;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "create_time")
    private String create_time;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "version")
    private String version;
    @ColumnInfo(name = "platform")
    private String platform;
    @ColumnInfo(name = "device_uid")
    private String device_uid;
    @ColumnInfo(name = "weixin_flag")
    private String weixin_flag;
    @ColumnInfo(name = "last_login_time")
    private String last_login_time;
    @ColumnInfo(name = "remark")
    private String remark;
    @ColumnInfo(name = "type_code")
    private String type_code;
    @ColumnInfo(name = "type_name")
    private String type_name;
    @ColumnInfo(name = "bank_card")
    private String bank_card;
    @ColumnInfo(name = "card_phone")
    private String card_phone;
    @ColumnInfo(name = "open_bank")
    private String open_bank;
    @ColumnInfo(name = "card_user")
    private String card_user;
    @ColumnInfo(name = "balance0")
    private String balance0;
    @ColumnInfo(name = "balance1")
    private String balance1;
    @ColumnInfo(name = "balance2")
    private String balance2;
    @ColumnInfo(name = "balance3")
    private String balance3;
    @ColumnInfo(name = "money_history")
    private String money_history;
    @ColumnInfo(name = "token")
    private String token;

    @NonNull
    public String getId() {
        return TextUtils.isEmpty(id) ? "-1" : id;
    }

    public void setId(@NonNull String id) {
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
        return TextUtils.isEmpty(token) ? "-1" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(@NonNull String id, String account, String nick_name, String pic_path, String real_name,
                String referee_tel, String phone, String idcard, String gender, String create_time,
                String status, String version, String platform, String device_uid, String weixin_flag,
                String last_login_time, String remark, String type_code, String type_name, String bank_card,
                String card_phone, String open_bank, String card_user, String balance0, String balance1,
                String balance2, String balance3, String money_history, String token) {
        this.id = id;
        this.account = account;
        this.nick_name = nick_name;
        this.pic_path = pic_path;
        this.real_name = real_name;
        this.referee_tel = referee_tel;
        this.phone = phone;
        this.idcard = idcard;
        this.gender = gender;
        this.create_time = create_time;
        this.status = status;
        this.version = version;
        this.platform = platform;
        this.device_uid = device_uid;
        this.weixin_flag = weixin_flag;
        this.last_login_time = last_login_time;
        this.remark = remark;
        this.type_code = type_code;
        this.type_name = type_name;
        this.bank_card = bank_card;
        this.card_phone = card_phone;
        this.open_bank = open_bank;
        this.card_user = card_user;
        this.balance0 = balance0;
        this.balance1 = balance1;
        this.balance2 = balance2;
        this.balance3 = balance3;
        this.money_history = money_history;
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", pic_path='" + pic_path + '\'' +
                ", real_name='" + real_name + '\'' +
                ", referee_tel='" + referee_tel + '\'' +
                ", phone='" + phone + '\'' +
                ", idcard='" + idcard + '\'' +
                ", gender='" + gender + '\'' +
                ", create_time='" + create_time + '\'' +
                ", status='" + status + '\'' +
                ", version='" + version + '\'' +
                ", platform='" + platform + '\'' +
                ", device_uid='" + device_uid + '\'' +
                ", weixin_flag='" + weixin_flag + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                ", remark='" + remark + '\'' +
                ", type_code='" + type_code + '\'' +
                ", type_name='" + type_name + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", card_phone='" + card_phone + '\'' +
                ", open_bank='" + open_bank + '\'' +
                ", card_user='" + card_user + '\'' +
                ", balance0='" + balance0 + '\'' +
                ", balance1='" + balance1 + '\'' +
                ", balance2='" + balance2 + '\'' +
                ", balance3='" + balance3 + '\'' +
                ", money_history='" + money_history + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

//    @Override
//    public String toString() {
//        return "{\n" +
//                "        \"id\":\"1005\",\n" +
//                "        \"account\":\"15010267528\",\n" +
//                "        \"nick_name\":\"Cazaea\",\n" +
//                "        \"pic_path\":\"http://imgcdn.huixdou.com/\",\n" +
//                "        \"real_name\":\"陈晓乐\",\n" +
//                "        \"referee_tel\":\"18352867739\",\n" +
//                "        \"phone\":\"15010267528\",\n" +
//                "        \"idcard\":\"\",\n" +
//                "        \"gender\":\"男\",\n" +
//                "        \"create_time\":\"1525512016\",\n" +
//                "        \"status\":\"0\",\n" +
//                "        \"version\":\"1.0\",\n" +
//                "        \"platform\":\"iOS\",\n" +
//                "        \"device_uid\":\"A9D5E8CF-5118-4013-B124-D28CCFDCA874\",\n" +
//                "        \"weixin_flag\":\"0\",\n" +
//                "        \"last_login_time\":\"1527575336\",\n" +
//                "        \"remark\":\"\",\n" +
//                "        \"type_code\":\"0\",\n" +
//                "        \"type_name\":\"普通会员\",\n" +
//                "        \"bank_card\":\"6228760805001758591\",\n" +
//                "        \"card_phone\":\"15010267528\",\n" +
//                "        \"open_bank\":\"江苏银行\",\n" +
//                "        \"card_user\":\"陈晓乐\",\n" +
//                "        \"balance0\":\"22222.22\",\n" +
//                "        \"balance1\":\"2222.22\",\n" +
//                "        \"balance2\":\"222.22\",\n" +
//                "        \"balance3\":\"22.22\",\n" +
//                "        \"money_history\":\"22222.22\",\n" +
//                "        \"token\":\"cDR3cXo5YlJ0aWp3RHpUU0VhSm9RNkxIUFc2RHJOcUlkTkFXNDVkUUNqOGRjNE9Id3NQWGY4OFZpV0c5WHhjSlg3NzRKRGVoQ2Y0PQ_c_c\"\n" +
//                "    }";
//    }

}
