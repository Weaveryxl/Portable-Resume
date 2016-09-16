package com.example.weaver.minilinkedin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Xiaolong on 2016/9/2.
 */
public class ModelUtils {
    private static Gson gson = new Gson();
    public static void saveModel(@NonNull Context context,String key, Object model){
        SharedPreferences sp = context.getSharedPreferences("model", context.MODE_PRIVATE);
        sp.edit().putString(key, gson.toJson(model)).apply();
    }

    public static <T> T readModel(@NonNull Context context, String Key, TypeToken<T> typeToken){
        SharedPreferences sp = context.getSharedPreferences("model",Context.MODE_PRIVATE);
        String jsonString = sp.getString(Key,"");
        return gson.fromJson(jsonString, typeToken.getType());
    }
}
