package com.hxd.root.bean.lease;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * 作 者： Cazaea
 * 日 期： 2018/8/24
 * 邮 箱： wistorm@sina.com
 */
public class LeaseBean extends BaseObservable {

    /**
     * code : 1000
     * msg : 获取成功
     * data : {"top_pic":[{"title":"全景图","link_url":"https://720yun.com/t/874jt0wktf1?scene_id=13802479","pic_path":"uploads/temp/201806/1529547740835.jpg","pic":"http://imgcdn.huixdou.com/uploads/temp/201806/1529547740835.jpg-tojpg"},{"title":"租房子","link_url":"","pic_path":"uploads/temp/201806/1528963432834.jpg","pic":"http://imgcdn.huixdou.com/uploads/temp/201806/1528963432834.jpg-tojpg"},{"title":"租房","link_url":"","pic_path":"uploads/temp/201806/1528960495191.jpg","pic":"http://imgcdn.huixdou.com/uploads/temp/201806/1528960495191.jpg-tojpg"}],"list":[{"id":"12","name":"楚桥雅苑16栋1023室","address":"楚桥路63号","status":"0","house_type":"精装公寓","area":"45平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1529042555070.jpg-tojpg","rent":"99元/天","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=12&user_id=1006&token=abcd","status_str":"招租中"},{"id":"10","name":"楚桥雅苑16栋1610室","address":"楚桥路63号","status":"1","house_type":"精装公寓","area":"51平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1529044640545.jpg-tojpg","rent":"1399元/月","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=10&user_id=1006&token=abcd","status_str":"已出租"},{"id":"11","name":"楚桥雅苑16栋1608室","address":"楚桥路63号","status":"1","house_type":"精装公寓","area":"50平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1529041025666.jpg-tojpg","rent":"1366元/月","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=11&user_id=1006&token=abcd","status_str":"已出租"},{"id":"9","name":"楚桥雅苑16栋1609室","address":"楚桥路63号","status":"1","house_type":"精装公寓","area":"45平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1529042513460.jpg-tojpg","rent":"1300元/月","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=9&user_id=1006&token=abcd","status_str":"已出租"},{"id":"14","name":"优山美地150栋244室","address":"谷阳大道优山美地","status":"1","house_type":"精装公寓","area":"49平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1530149413876.jpg-tojpg","rent":"1300元/月","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=14&user_id=1006&token=abcd","status_str":"已出租"},{"id":"13","name":"优山美地150栋314室","address":"谷阳大道优山美地","status":"1","house_type":"精装公寓","area":"46平方米","decoration_type":"精装公寓","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201806/1530149645523.jpg-tojpg","rent":"1300元/月","link":"http://lease.nfc-hxd.com/App/lehouse/info?id=13&user_id=1006&token=abcd","status_str":"已出租"}]}
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
        private List<TopPicBean> top_pic;
        private List<ListBean> list;

        public List<TopPicBean> getTop_pic() {
            return top_pic;
        }

        public void setTop_pic(List<TopPicBean> top_pic) {
            this.top_pic = top_pic;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class TopPicBean {
            /**
             * title : 全景图
             * link_url : https://720yun.com/t/874jt0wktf1?scene_id=13802479
             * pic_path : uploads/temp/201806/1529547740835.jpg
             * pic : http://imgcdn.huixdou.com/uploads/temp/201806/1529547740835.jpg-tojpg
             */

            private String title;
            private String link_url;
            private String pic_path;
            private String pic;

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

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }

        public static class ListBean {
            /**
             * id : 12
             * name : 楚桥雅苑16栋1023室
             * address : 楚桥路63号
             * status : 0
             * house_type : 精装公寓
             * area : 45平方米
             * decoration_type : 精装公寓
             * pic_url : http://imgcdn.huixdou.com/uploads/temp/201806/1529042555070.jpg-tojpg
             * rent : 99元/天
             * link : http://lease.nfc-hxd.com/App/lehouse/info?id=12&user_id=1006&token=abcd
             * status_str : 招租中
             */

            private String id;
            private String name;
            private String address;
            private String status;
            private String house_type;
            private String area;
            private String decoration_type;
            private String pic_url;
            private String rent;
            private String link;
            private String status_str;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getHouse_type() {
                return house_type;
            }

            public void setHouse_type(String house_type) {
                this.house_type = house_type;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getDecoration_type() {
                return decoration_type;
            }

            public void setDecoration_type(String decoration_type) {
                this.decoration_type = decoration_type;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public String getRent() {
                return rent;
            }

            public void setRent(String rent) {
                this.rent = rent;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getStatus_str() {
                return status_str;
            }

            public void setStatus_str(String status_str) {
                this.status_str = status_str;
            }
        }
    }
}
