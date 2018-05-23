package com.and.wodness.api;
/**
 * Created by Ivan on 8/24/2017.
 */

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class APIResponseDeserializer<T> implements JsonDeserializer<T> {

    private static final String KEY_STATUS = "response_code";
    private static final String KEY_DATA = "data";
    private static final String KEY_MESSAGE = "message";
    Class<T> returnType;

    public APIResponseDeserializer(Class<T> clazz) {
        returnType = clazz;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        // Read simple String values.
        final int statusCode = jsonObject.get(KEY_STATUS).getAsInt();
        JsonElement dataElement = jsonObject.get(KEY_DATA);
        JsonElement messageElement = jsonObject.get(KEY_MESSAGE);

        // Read the dynamic parameters object.
        //final Map<String, String> parameters = readParametersMap(jsonObject);
        if (LoginResponse.class.isAssignableFrom(returnType)) {
            LoginResponse result = new LoginResponse();
            result.setStatusCode(statusCode);

            if (statusCode != 200) {
                result.setData(null);

            }
            //Type type = new TypeToken<LoginResponse>() {}.getType();
            //context.deserialize(json, type);
            Gson gson = new Gson();
            LoginResponse m = gson.fromJson(json, LoginResponse.class);
            return (T) m;
        }

        else if(RegisterResponse.class.isAssignableFrom(returnType)) {
            RegisterResponse result = new RegisterResponse();
            result.setStatusCode(statusCode);

            if (statusCode != 200) {
                result.setData(null);
            }
            Gson gson = new Gson();
            RegisterResponse m = gson.fromJson(json, RegisterResponse.class);
            return  (T)m;
        }
        else if(ResetResponse.class.isAssignableFrom(returnType)) {
            ResetResponse result = new ResetResponse();
            result.setStatusCode(statusCode);

            if (statusCode != 200) {
                result.setData(null);
            }
            Gson gson = new Gson();
            ResetResponse m = gson.fromJson(json, ResetResponse.class);
            return  (T)m;
        }
        else if(ModifyResponse.class.isAssignableFrom(returnType)) {
            ModifyResponse result = new ModifyResponse();
            result.setStatusCode(statusCode);

            if (statusCode != 200) {
                result.setData(null);
            }
            Gson gson = new Gson();
            ModifyResponse m = gson.fromJson(json, ModifyResponse.class);
            return  (T)m;
        }
        else if (UpdateResponse.class.isAssignableFrom(returnType)) {
            UpdateResponse result = new UpdateResponse();
            result.setStatusCode(statusCode);

            if (statusCode != 200) {
                result.setData(null);
            }
            //Type type = new TypeToken<LoginResponse>() {}.getType();
            //context.deserialize(json, type);
            Gson gson = new Gson();
            UpdateResponse m = gson.fromJson(json, UpdateResponse.class);
            return (T) m;
        }
        return null;
    }

}
