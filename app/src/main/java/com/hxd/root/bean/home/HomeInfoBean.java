package com.hxd.root.bean.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hxd.root.BR;
import com.hxd.root.http.rxhttp.ParamNames;

import java.io.Serializable;
import java.util.List;

/**
 * 作 者： Cazaea
 * 日 期： 2018/8/24
 * 邮 箱： wistorm@sina.com
 */
public class HomeInfoBean extends BaseObservable implements Serializable {

    /**
     * code : 1000
     * msg : 获取成功
     * data : {"box_list":[],"banner":[{"title":"端午节","link_url":"","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1529397450629.jpg-tojpg"},{"title":"政策汇编","link_url":"https://www.haoyu.top/app/inarticle/info?id=249&user_id=-1&token=-1","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1530150232315.jpg-tojpg"},{"title":"首页轮播图01","link_url":"","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1528361716252.jpg-tojpg"},{"title":"公司资产","link_url":"http://lease.nfc-hxd.com/app/home/assets?user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1528520210708.jpg-tojpg"},{"title":"公司荣誉","link_url":"http://lease.nfc-hxd.com/app/home/honor?user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1528519996749.jpg-tojpg"},{"title":"公司名优产品","link_url":"http://lease.nfc-hxd.com/app/home/product?user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1528519557712.jpg-tojpg"}],"headline":[{"title":"七夕节期间签到可领0.66元，邀请奖励双倍！","link_url":"https://www.haoyu.top/app/inarticle/info?id=506&user_id=-1&token=-1","pic_path":""},{"title":"优选双收益：预期年租金收益7.88%+增值分红","link_url":"https://www.haoyu.top/app/Prproduct/detail?share_flag=1&user_id=-1&token=-1&product_id=11","pic_path":""},{"title":"通知存取：预期年租金收益7.58%+平台奖励","link_url":"https://www.haoyu.top/app/Prproduct/detail?share_flag=1&user_id=-1&token=-1&product_id=22","pic_path":""},{"title":"随存随取：预期租金年化率6.99% 200元起投","link_url":"https://www.haoyu.top/app/Prproduct/detail?share_flag=1&user_id=-1&token=-1&product_id=21","pic_path":""},{"title":"【濠寓】关于提现到帐时间公告","link_url":"https://www.haoyu.top/app/inarticle/info?id=479&user_id=-1&token=-1","pic_path":""},{"title":"投资者必看：濠寓平台和P2P的区别","link_url":"https://www.haoyu.top/app/inarticle/info?id=411&user_id=-1&token=-1","pic_path":""},{"title":"濠寓投资排名龙虎榜（每日更新）","link_url":"https://www.haoyu.top/app/inarticle/info?id=500&user_id=-1&token=-1","pic_path":""}],"module":[{"title":"镇江楼盘","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=3&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824072059.png-tojpg"},{"title":"房产资讯","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=4&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824096954.png-tojpg"},{"title":"政策法规","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=5&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824119959.png-tojpg"},{"title":"税务知识","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=6&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824153612.png-tojpg"},{"title":"国土登记","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=7&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824176759.png-tojpg"},{"title":"住建交易","link_url":"http://lease.nfc-hxd.com/app/inarticle/listIndex?column_id=8&user_id=1006&token=abcd","pic_path":"http://imgcdn.huixdou.com/uploads/temp/201806/1527824189710.png-tojpg"}],"article":[{"id":"296","title":"7月31日镇江商品房成交386套","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201808/1533115004626.jpg-tojpg","outline":"据统计，昨日（7月31日）我市商品房（排除安置房）总成交套数386套。其中，镇江市区总成交套数237套","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=296"},{"id":"423","title":"一件焦点访谈专题播出的事件","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201807/1533015990658.jpg-tojpg","outline":"办证难，先转观念后依托科技，茴香豆\u2026\u2026一家总是让小编联想起酒馆的科技公司","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=423"},{"id":"425","title":"江景豪宅VS城郊毛坯房，你选哪个？","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201807/1533018738464.jpg-tojpg","outline":"自去年土拍开始，不少全国大牌开发商纷纷进驻镇江。今年豪宅涌现，定位高端","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=425"},{"id":"426","title":"南徐重磅规划出炉，步入2万时代?","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201807/1533020402871.jpg-tojpg","outline":"千呼万唤始出来！今天，我查询镇江市规划局了解到，南徐体育会展中心以北地块规划终于出炉！","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=426"},{"id":"436","title":"镇江人，你为什么在南徐买房？","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201808/1533110521048.jpg-tojpg","outline":"上海有浦东，南京有河西，镇江有南徐，打自镇江市政府提出\u201c南山北水\u201d","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=436"},{"id":"437","title":"建军节｜濠寓致敬最可爱的人！","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201808/1533111839505.jpg-tojpg","outline":"有一种追求，叫精忠报国。有一种日子，叫与星相守。有一种情愫，叫思家念亲。\u201c八一\u201d建军节。","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=437"},{"id":"440","title":"南徐这楼盘7000元/㎡竟也卖不掉?","pic_url":"http://imgcdn.huixdou.com/uploads/temp/201808/1533114335320.jpg-tojpg","outline":"作为南徐核心区高档别墅盘，君临南山曾屡次获得镇江别墅市场销售冠亚军","link_url":"http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=440"}]}
     */
    @ParamNames("code")
    private int code;
    @ParamNames("msg")
    private String msg;
    @ParamNames("data")
    private DataBean data;

    @Bindable
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        notifyPropertyChanged(BR.msg);
    }

    @Bindable
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    public static class DataBean extends BaseObservable implements Serializable {
        @ParamNames("box_list")
        private List<?> box_list;
        @ParamNames("banner")
        private List<BannerBean> banner;
        @ParamNames("headline")
        private List<HeadlineBean> headline;
        @ParamNames("module")
        private List<ModuleBean> module;
        @ParamNames("article")
        private List<ArticleBean> article;

        @Bindable
        public List<?> getBox_list() {
            return box_list;
        }

        public void setBox_list(List<?> box_list) {
            this.box_list = box_list;
            notifyPropertyChanged(BR.box_list);
        }

        @Bindable
        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
            notifyPropertyChanged(BR.banner);
        }

        @Bindable
        public List<HeadlineBean> getHeadline() {
            return headline;
        }

        public void setHeadline(List<HeadlineBean> headline) {
            this.headline = headline;
            notifyPropertyChanged(BR.headline);
        }

        @Bindable
        public List<ModuleBean> getModule() {
            return module;
        }

        public void setModule(List<ModuleBean> module) {
            this.module = module;
            notifyPropertyChanged(BR.module);
        }

        @Bindable
        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
            notifyPropertyChanged(BR.article);
        }

    }
}
