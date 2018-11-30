package com.hxd.root.bean.product.pay;

import java.util.List;

/**
 * 作 者： Cazaea
 * 日 期： 2018/9/5
 * 邮 箱： wistorm@sina.com
 */
public class PayInfoBean {

    /**
     * code : 1000
     * msg : 成功
     * data : {"type":"channel_pay","redirect":{"success_url":"http://lease.nfc-hxd.com/app/Orpayresult/loadSuccessPage?user_id=6&token=abcd&order_number=APDDC528969818981292","cancel_url":"http://lease.nfc-hxd.com/app/Orpayresult/loadFailPage?user_id=6&token=abcd&order_number=APDDC528969818981292"},"pay_data":{"id":"ch_1aHmHCSuvLu1nTy1ePTKS0O8","object":"charge","created":1527497020,"livemode":false,"paid":false,"refunded":false,"reversed":false,"app":"app_4O08WTnrHOmHqDyT","channel":"wx","order_no":"LC528970205894732","client_ip":"222.186.125.185","amount":100,"amount_settle":100,"currency":"cny","subject":"濠寓支付","body":"濠寓支付","extra":{},"time_paid":null,"time_expire":1527504220,"time_settle":null,"transaction_no":null,"refunds":{"object":"list","url":"/v1/charges/ch_1aHmHCSuvLu1nTy1ePTKS0O8/refunds","has_more":false,"data":[]},"amount_refunded":0,"failure_code":null,"failure_msg":null,"metadata":{"type":2,"pay_channel":"other","order_num":"APDDC528969818981292","token":"abcd"},"credential":{"object":"credential","wx":{"appId":"wxe5ggu9qv1mjh1inn","partnerId":"2702361752","prepayId":"1101000000180528zbbdccoann90rjr1","nonceStr":"4a9e5b6ebb7ed211d319dcb48b9bd25d","timeStamp":1527497020,"packageValue":"Sign=WXPay","sign":"D26FEA439A6212CB60ADA9A6703D25C4"}},"description":null}}
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
         * type : channel_pay
         * redirect : {"success_url":"http://lease.nfc-hxd.com/app/Orpayresult/loadSuccessPage?user_id=6&token=abcd&order_number=APDDC528969818981292","cancel_url":"http://lease.nfc-hxd.com/app/Orpayresult/loadFailPage?user_id=6&token=abcd&order_number=APDDC528969818981292"}
         * pay_data : {"id":"ch_1aHmHCSuvLu1nTy1ePTKS0O8","object":"charge","created":1527497020,"livemode":false,"paid":false,"refunded":false,"reversed":false,"app":"app_4O08WTnrHOmHqDyT","channel":"wx","order_no":"LC528970205894732","client_ip":"222.186.125.185","amount":100,"amount_settle":100,"currency":"cny","subject":"濠寓支付","body":"濠寓支付","extra":{},"time_paid":null,"time_expire":1527504220,"time_settle":null,"transaction_no":null,"refunds":{"object":"list","url":"/v1/charges/ch_1aHmHCSuvLu1nTy1ePTKS0O8/refunds","has_more":false,"data":[]},"amount_refunded":0,"failure_code":null,"failure_msg":null,"metadata":{"type":2,"pay_channel":"other","order_num":"APDDC528969818981292","token":"abcd"},"credential":{"object":"credential","wx":{"appId":"wxe5ggu9qv1mjh1inn","partnerId":"2702361752","prepayId":"1101000000180528zbbdccoann90rjr1","nonceStr":"4a9e5b6ebb7ed211d319dcb48b9bd25d","timeStamp":1527497020,"packageValue":"Sign=WXPay","sign":"D26FEA439A6212CB60ADA9A6703D25C4"}},"description":null}
         */

        private String type;
        private RedirectBean redirect;
        private PayDataBean pay_data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public RedirectBean getRedirect() {
            return redirect;
        }

        public void setRedirect(RedirectBean redirect) {
            this.redirect = redirect;
        }

        public PayDataBean getPay_data() {
            return pay_data;
        }

        public void setPay_data(PayDataBean pay_data) {
            this.pay_data = pay_data;
        }

        public static class RedirectBean {
            /**
             * success_url : http://lease.nfc-hxd.com/app/Orpayresult/loadSuccessPage?user_id=6&token=abcd&order_number=APDDC528969818981292
             * cancel_url : http://lease.nfc-hxd.com/app/Orpayresult/loadFailPage?user_id=6&token=abcd&order_number=APDDC528969818981292
             */

            private String success_url;
            private String cancel_url;

            public String getSuccess_url() {
                return success_url;
            }

            public void setSuccess_url(String success_url) {
                this.success_url = success_url;
            }

            public String getCancel_url() {
                return cancel_url;
            }

            public void setCancel_url(String cancel_url) {
                this.cancel_url = cancel_url;
            }
        }

        public static class PayDataBean {
            /**
             * id : ch_1aHmHCSuvLu1nTy1ePTKS0O8
             * object : charge
             * created : 1527497020
             * livemode : false
             * paid : false
             * refunded : false
             * reversed : false
             * app : app_4O08WTnrHOmHqDyT
             * channel : wx
             * order_no : LC528970205894732
             * client_ip : 222.186.125.185
             * amount : 100
             * amount_settle : 100
             * currency : cny
             * subject : 濠寓支付
             * body : 濠寓支付
             * extra : {}
             * time_paid : null
             * time_expire : 1527504220
             * time_settle : null
             * transaction_no : null
             * refunds : {"object":"list","url":"/v1/charges/ch_1aHmHCSuvLu1nTy1ePTKS0O8/refunds","has_more":false,"data":[]}
             * amount_refunded : 0
             * failure_code : null
             * failure_msg : null
             * metadata : {"type":2,"pay_channel":"other","order_num":"APDDC528969818981292","token":"abcd"}
             * credential : {"object":"credential","wx":{"appId":"wxe5ggu9qv1mjh1inn","partnerId":"2702361752","prepayId":"1101000000180528zbbdccoann90rjr1","nonceStr":"4a9e5b6ebb7ed211d319dcb48b9bd25d","timeStamp":1527497020,"packageValue":"Sign=WXPay","sign":"D26FEA439A6212CB60ADA9A6703D25C4"}}
             * description : null
             */

            private String id;
            private String object;
            private int created;
            private boolean livemode;
            private boolean paid;
            private boolean refunded;
            private boolean reversed;
            private String app;
            private String channel;
            private String order_no;
            private String client_ip;
            private int amount;
            private int amount_settle;
            private String currency;
            private String subject;
            private String body;
            private ExtraBean extra;
            private Object time_paid;
            private int time_expire;
            private Object time_settle;
            private Object transaction_no;
            private RefundsBean refunds;
            private int amount_refunded;
            private Object failure_code;
            private Object failure_msg;
            private MetadataBean metadata;
            private CredentialBean credential;
            private Object description;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public int getCreated() {
                return created;
            }

            public void setCreated(int created) {
                this.created = created;
            }

            public boolean isLivemode() {
                return livemode;
            }

            public void setLivemode(boolean livemode) {
                this.livemode = livemode;
            }

            public boolean isPaid() {
                return paid;
            }

            public void setPaid(boolean paid) {
                this.paid = paid;
            }

            public boolean isRefunded() {
                return refunded;
            }

            public void setRefunded(boolean refunded) {
                this.refunded = refunded;
            }

            public boolean isReversed() {
                return reversed;
            }

            public void setReversed(boolean reversed) {
                this.reversed = reversed;
            }

            public String getApp() {
                return app;
            }

            public void setApp(String app) {
                this.app = app;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getClient_ip() {
                return client_ip;
            }

            public void setClient_ip(String client_ip) {
                this.client_ip = client_ip;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getAmount_settle() {
                return amount_settle;
            }

            public void setAmount_settle(int amount_settle) {
                this.amount_settle = amount_settle;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public Object getTime_paid() {
                return time_paid;
            }

            public void setTime_paid(Object time_paid) {
                this.time_paid = time_paid;
            }

            public int getTime_expire() {
                return time_expire;
            }

            public void setTime_expire(int time_expire) {
                this.time_expire = time_expire;
            }

            public Object getTime_settle() {
                return time_settle;
            }

            public void setTime_settle(Object time_settle) {
                this.time_settle = time_settle;
            }

            public Object getTransaction_no() {
                return transaction_no;
            }

            public void setTransaction_no(Object transaction_no) {
                this.transaction_no = transaction_no;
            }

            public RefundsBean getRefunds() {
                return refunds;
            }

            public void setRefunds(RefundsBean refunds) {
                this.refunds = refunds;
            }

            public int getAmount_refunded() {
                return amount_refunded;
            }

            public void setAmount_refunded(int amount_refunded) {
                this.amount_refunded = amount_refunded;
            }

            public Object getFailure_code() {
                return failure_code;
            }

            public void setFailure_code(Object failure_code) {
                this.failure_code = failure_code;
            }

            public Object getFailure_msg() {
                return failure_msg;
            }

            public void setFailure_msg(Object failure_msg) {
                this.failure_msg = failure_msg;
            }

            public MetadataBean getMetadata() {
                return metadata;
            }

            public void setMetadata(MetadataBean metadata) {
                this.metadata = metadata;
            }

            public CredentialBean getCredential() {
                return credential;
            }

            public void setCredential(CredentialBean credential) {
                this.credential = credential;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public static class ExtraBean {
            }

            public static class RefundsBean {
                /**
                 * object : list
                 * url : /v1/charges/ch_1aHmHCSuvLu1nTy1ePTKS0O8/refunds
                 * has_more : false
                 * data : []
                 */

                private String object;
                private String url;
                private boolean has_more;
                private List<?> data;

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public boolean isHas_more() {
                    return has_more;
                }

                public void setHas_more(boolean has_more) {
                    this.has_more = has_more;
                }

                public List<?> getData() {
                    return data;
                }

                public void setData(List<?> data) {
                    this.data = data;
                }
            }

            public static class MetadataBean {
                /**
                 * type : 2
                 * pay_channel : other
                 * order_num : APDDC528969818981292
                 * token : abcd
                 */

                private int type;
                private String pay_channel;
                private String order_num;
                private String token;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getPay_channel() {
                    return pay_channel;
                }

                public void setPay_channel(String pay_channel) {
                    this.pay_channel = pay_channel;
                }

                public String getOrder_num() {
                    return order_num;
                }

                public void setOrder_num(String order_num) {
                    this.order_num = order_num;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }
            }

            public static class CredentialBean {
                /**
                 * object : credential
                 * wx : {"appId":"wxe5ggu9qv1mjh1inn","partnerId":"2702361752","prepayId":"1101000000180528zbbdccoann90rjr1","nonceStr":"4a9e5b6ebb7ed211d319dcb48b9bd25d","timeStamp":1527497020,"packageValue":"Sign=WXPay","sign":"D26FEA439A6212CB60ADA9A6703D25C4"}
                 */

                private String object;
                private WxBean wx;

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public WxBean getWx() {
                    return wx;
                }

                public void setWx(WxBean wx) {
                    this.wx = wx;
                }

                public static class WxBean {
                    /**
                     * appId : wxe5ggu9qv1mjh1inn
                     * partnerId : 2702361752
                     * prepayId : 1101000000180528zbbdccoann90rjr1
                     * nonceStr : 4a9e5b6ebb7ed211d319dcb48b9bd25d
                     * timeStamp : 1527497020
                     * packageValue : Sign=WXPay
                     * sign : D26FEA439A6212CB60ADA9A6703D25C4
                     */

                    private String appId;
                    private String partnerId;
                    private String prepayId;
                    private String nonceStr;
                    private int timeStamp;
                    private String packageValue;
                    private String sign;

                    public String getAppId() {
                        return appId;
                    }

                    public void setAppId(String appId) {
                        this.appId = appId;
                    }

                    public String getPartnerId() {
                        return partnerId;
                    }

                    public void setPartnerId(String partnerId) {
                        this.partnerId = partnerId;
                    }

                    public String getPrepayId() {
                        return prepayId;
                    }

                    public void setPrepayId(String prepayId) {
                        this.prepayId = prepayId;
                    }

                    public String getNonceStr() {
                        return nonceStr;
                    }

                    public void setNonceStr(String nonceStr) {
                        this.nonceStr = nonceStr;
                    }

                    public int getTimeStamp() {
                        return timeStamp;
                    }

                    public void setTimeStamp(int timeStamp) {
                        this.timeStamp = timeStamp;
                    }

                    public String getPackageValue() {
                        return packageValue;
                    }

                    public void setPackageValue(String packageValue) {
                        this.packageValue = packageValue;
                    }

                    public String getSign() {
                        return sign;
                    }

                    public void setSign(String sign) {
                        this.sign = sign;
                    }
                }
            }
        }
    }
}
