package com.qci.fish.RoomDataBase.sample;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeConverator {

    @TypeConverter
    public static ArrayList<SampleFishTypeList> fromString(String value) {
        Type listType = new TypeToken<ArrayList<SampleFishTypeList>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(ArrayList<SampleFishTypeList> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
