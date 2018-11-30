package com.hxd.root.http.testbackup;

import com.hxd.root.http.HttpUtils;

import static com.hxd.root.http.HttpUtils.API_BETA;
import static com.hxd.root.http.HttpUtils.API_FORMAL;
import static com.hxd.root.http.HttpUtils.API_UPDATE;

/**
 * @author Cazaea
 * @data 2018/2/9
 * @Description
 */

public class BuildFactory {

    private static BuildFactory instance;

    // 默认服务器地址
    private Object baseHttps;

    // 测试服务器地址
    private Object betaHttps;
    // 正式服务器地址
    private Object formalHttps;

    // 项目更新服务器地址
    private Object updateHttps;

    public static BuildFactory getInstance() {
        if (instance == null) {
            synchronized (BuildFactory.class) {
                if (instance == null) {
                    instance = new BuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> a, String type) {

        switch (type) {
            case API_BETA:
                if (betaHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (betaHttps == null) {
                            betaHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) betaHttps;
            case API_FORMAL:
                if (formalHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (formalHttps == null) {
                            formalHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) formalHttps;
            case API_UPDATE:
                if (updateHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (updateHttps == null) {
                            updateHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) updateHttps;
            default:
                if (baseHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (baseHttps == null) {
                            baseHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) baseHttps;
        }

    }

}
