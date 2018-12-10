package com.hxd.root.http.rxutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/12
 * 邮 箱： wistorm@sina.com
 * <p>
 * 构建Gson空值转换器, 避免转换异常
 */
public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);

        return (Converter<ResponseBody, Object>) value -> {
            if (value.contentLength() == 0)
                return null;
            return delegate.convert(value);
        };
    }
}