package com.hxd.root.bean.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hxd.root.BR;
import com.hxd.root.http.rxutils.ParamNames;

import java.io.Serializable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/27
 * 邮 箱： wistorm@sina.com
 */
public class BannerBean extends BaseObservable implements Serializable {
    /**
     * title : 端午节
     * link_url :
     * pic_path : http://imgcdn.huixdou.com/uploads/temp/201806/1529397450629.jpg-tojpg
     */
    @ParamNames("title")
    private String title;
    @ParamNames("link_url")
    private String link_url;
    @ParamNames("pic_path")
    private String pic_path;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
        notifyPropertyChanged(BR.link_url);
    }

    @Bindable
    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
        notifyPropertyChanged(BR.pic_path);
    }
}
