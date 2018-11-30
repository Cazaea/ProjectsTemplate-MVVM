package com.hxd.root.bean.product.pay;

import java.util.List;

/**
 * 作 者： Cazaea
 * 日 期： 2018/9/5
 * 邮 箱： wistorm@sina.com
 */
public class PayChannelBean {

    /**
     * code : 1000
     * msg : 获取成功
     * data : [{"id":"201","type":"17","code":"alipay","name":"支付宝","status":"1","parentid":"-1","parentlist":"-1","deep":"0","childnum":"0","orders":"0","remark":"","icons":null,"delete_flag":"0","delete_time":null,"delete_user_id":null,"pic_path":"http://imgcdn.huixdou.com/uploads/temp/201805/1525498605885.png"},{"id":"202","type":"17","code":"wx","name":"微信(单笔限额￥3000)","status":"1","parentid":"-1","parentlist":"-1","deep":"0","childnum":"0","orders":"0","remark":"","icons":null,"delete_flag":"0","delete_time":null,"delete_user_id":null,"pic_path":"http://imgcdn.huixdou.com/uploads/temp/201805/1525498615096.png"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 201
         * type : 17
         * code : alipay
         * name : 支付宝
         * status : 1
         * parentid : -1
         * parentlist : -1
         * deep : 0
         * childnum : 0
         * orders : 0
         * remark :
         * icons : null
         * delete_flag : 0
         * delete_time : null
         * delete_user_id : null
         * pic_path : http://imgcdn.huixdou.com/uploads/temp/201805/1525498605885.png
         */

        private String id;
        private String type;
        private String code;
        private String name;
        private String status;
        private String parentid;
        private String parentlist;
        private String deep;
        private String childnum;
        private String orders;
        private String remark;
        private Object icons;
        private String delete_flag;
        private Object delete_time;
        private Object delete_user_id;
        private String pic_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getParentlist() {
            return parentlist;
        }

        public void setParentlist(String parentlist) {
            this.parentlist = parentlist;
        }

        public String getDeep() {
            return deep;
        }

        public void setDeep(String deep) {
            this.deep = deep;
        }

        public String getChildnum() {
            return childnum;
        }

        public void setChildnum(String childnum) {
            this.childnum = childnum;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getIcons() {
            return icons;
        }

        public void setIcons(Object icons) {
            this.icons = icons;
        }

        public String getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(String delete_flag) {
            this.delete_flag = delete_flag;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public Object getDelete_user_id() {
            return delete_user_id;
        }

        public void setDelete_user_id(Object delete_user_id) {
            this.delete_user_id = delete_user_id;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
    }
}
