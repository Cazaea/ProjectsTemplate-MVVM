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
 * 当接口请求失败或者返回的数据错误(也就是返回码是错误码时)，按照常理，我们是不会解析data的。
 * 可Gson每次都会对json进行反序列化，不管json里面的数据是否是成功的，里面的data都会被反序列化。
 * <p>
 * 后台接口也很操蛋！
 * 只要请求失败，不管data里面的数据是JSONObject 还是JSONArray，都返回JSONArray。
 * 这就蛋疼了，经常会有json解析的异常抛出，于是我不得不含泪解决。
 */
public class ResultDeserialize<T> implements JsonDeserializer<BaseResponse<T>> {

    @Override
    public BaseResponse<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BaseResponse<T> response = new BaseResponse<T>();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            response.setCode(jsonObject.get("code").getAsInt());
            response.setMsg(jsonObject.get("msg").getAsString());
            if (!response.isSuccess()) {
                return response;
            }
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            response.setData(context.deserialize(jsonObject.get("data"), itemType));
            return response;
        }
        return response;
    }
}