package com.hxd.root.http.rxutils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hxd.root.http.base.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作 者： Cazaea
 * 日 期： 2018/12/4
 * 邮 箱： wistorm@sina.com
 * <p>
 * 只要请求失败，不管data里面的数据是JSONObject 还是JSONArray，都返回JSONArray。
 * 这就蛋疼了，经常会有json解析的异常抛出，于是我不得不含泪解决。
 */
public class ResultJsonTypeAdapter<T> implements JsonDeserializer<BaseResponse<T>> {

    @Override
    public BaseResponse<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BaseResponse<T> response = new BaseResponse<T>();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            response.setCode(code);
            response.setMsg(jsonObject.get("msg").getAsString());
            if (code != 1000) {
                return response;
            }
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            response.setData(context.deserialize(jsonObject.get("data"), itemType));
            return response;
        }
        return response;
    }
}