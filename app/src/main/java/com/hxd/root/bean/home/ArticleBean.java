package com.hxd.root.bean.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hxd.root.BR;
import com.hxd.root.http.rxhttp.ParamNames;

import java.io.Serializable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/27
 * 邮 箱： wistorm@sina.com
 */
public class ArticleBean extends BaseObservable implements Serializable {
    /**
     * id : 296
     * title : 7月31日镇江商品房成交386套
     * pic_url : http://imgcdn.huixdou.com/uploads/temp/201808/1533115004626.jpg-tojpg
     * outline : 据统计，昨日（7月31日）我市商品房（排除安置房）总成交套数386套。其中，镇江市区总成交套数237套
     * link_url : http://lease.nfc-hxd.com/app/inarticle/info?user_id=1006&token=abcd&id=296
     */
    @ParamNames("id")
    private String id;
    @ParamNames("title")
    private String title;
    @ParamNames("pic_url")
    private String pic_url;
    @ParamNames("outline")
    private String outline;
    @ParamNames("link_url")
    private String link_url;

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
        notifyPropertyChanged(BR.pic_url);
    }

    @Bindable
    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
        notifyPropertyChanged(BR.outline);
    }

    @Bindable
    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
        notifyPropertyChanged(BR.link_url);
    }
}
