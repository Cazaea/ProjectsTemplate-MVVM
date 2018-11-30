package com.hxd.root.bean.product.pay;

/**
 * 作 者： Cazaea
 * 日 期： 2018/9/5
 * 邮 箱： wistorm@sina.com
 */
public class OrderInfoBean {

    /**
     * code : 1000
     * msg : 获取成功
     * data : {"id":"71","member_id":"6","order_number":"APDDC528969818981292","type_code":"1","type_name":"充值订单","create_time":"1527496981","status":"0","order_money":"1.00","money":"1.00","remark":null,"pay_status":"0","pay_time":null,"pay_channel":null,"pay_channel_name":null,"charge_id":null,"refund_id":null,"refund_user_id":null,"refund_reason":null,"refund_time":null,"invoice_status":"0","product_id":"6","product_name":"充值15050855986","product_price":"1.00","product_quantity":"1","is_buy":"0","show_flag":"1","delete_flag":"0","delete_time":null,"delete_user_id":null}
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
         * id : 71
         * member_id : 6
         * order_number : APDDC528969818981292
         * type_code : 1
         * type_name : 充值订单
         * create_time : 1527496981
         * status : 0
         * order_money : 1.00
         * money : 1.00
         * remark : null
         * pay_status : 0
         * pay_time : null
         * pay_channel : null
         * pay_channel_name : null
         * charge_id : null
         * refund_id : null
         * refund_user_id : null
         * refund_reason : null
         * refund_time : null
         * invoice_status : 0
         * product_id : 6
         * product_name : 充值15050855986
         * product_price : 1.00
         * product_quantity : 1
         * is_buy : 0
         * show_flag : 1
         * delete_flag : 0
         * delete_time : null
         * delete_user_id : null
         */

        private String id;
        private String member_id;
        private String order_number;
        private String type_code;
        private String type_name;
        private String create_time;
        private String status;
        private String order_money;
        private String money;
        private Object remark;
        private String pay_status;
        private Object pay_time;
        private Object pay_channel;
        private Object pay_channel_name;
        private Object charge_id;
        private Object refund_id;
        private Object refund_user_id;
        private Object refund_reason;
        private Object refund_time;
        private String invoice_status;
        private String product_id;
        private String product_name;
        private String product_price;
        private String product_quantity;
        private String is_buy;
        private String show_flag;
        private String delete_flag;
        private Object delete_time;
        private Object delete_user_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
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

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public Object getPay_time() {
            return pay_time;
        }

        public void setPay_time(Object pay_time) {
            this.pay_time = pay_time;
        }

        public Object getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(Object pay_channel) {
            this.pay_channel = pay_channel;
        }

        public Object getPay_channel_name() {
            return pay_channel_name;
        }

        public void setPay_channel_name(Object pay_channel_name) {
            this.pay_channel_name = pay_channel_name;
        }

        public Object getCharge_id() {
            return charge_id;
        }

        public void setCharge_id(Object charge_id) {
            this.charge_id = charge_id;
        }

        public Object getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(Object refund_id) {
            this.refund_id = refund_id;
        }

        public Object getRefund_user_id() {
            return refund_user_id;
        }

        public void setRefund_user_id(Object refund_user_id) {
            this.refund_user_id = refund_user_id;
        }

        public Object getRefund_reason() {
            return refund_reason;
        }

        public void setRefund_reason(Object refund_reason) {
            this.refund_reason = refund_reason;
        }

        public Object getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(Object refund_time) {
            this.refund_time = refund_time;
        }

        public String getInvoice_status() {
            return invoice_status;
        }

        public void setInvoice_status(String invoice_status) {
            this.invoice_status = invoice_status;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getProduct_quantity() {
            return product_quantity;
        }

        public void setProduct_quantity(String product_quantity) {
            this.product_quantity = product_quantity;
        }

        public String getIs_buy() {
            return is_buy;
        }

        public void setIs_buy(String is_buy) {
            this.is_buy = is_buy;
        }

        public String getShow_flag() {
            return show_flag;
        }

        public void setShow_flag(String show_flag) {
            this.show_flag = show_flag;
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
    }
}
