package com.qci.fish.RoomDataBase.sample;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qci.fish.pojo.ImageCapturePojo;
import com.qci.fish.pojo.QRCodeCapturePojo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class QRCodeTypeConverator {

    @TypeConverter
    public static ArrayList<QRCodeCapturePojo> fromString(String value) {
        Type listType = new TypeToken<ArrayList<QRCodeCapturePojo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(ArrayList<QRCodeCapturePojo> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
