package i.am.lucky.http.utils;

import i.am.lucky.http.HttpUtils;

import static i.am.lucky.http.HttpUtils.API_BETA;
import static i.am.lucky.http.HttpUtils.API_FORMAL;

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
                        baseHttps = betaHttps;
                    }
                }
            case API_FORMAL:
                if (formalHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (formalHttps == null) {
                            formalHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                        baseHttps = formalHttps;
                    }
                }
        }
        return (T) baseHttps;

    }

}
