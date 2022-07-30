package com.ansarbank.room.Database.Converter;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;

import com.ansarbank.room.Database.Card.Card;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class BitmapConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromValuesList(Bitmap optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Bitmap>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Bitmap toValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Bitmap>() {
        }.getType();
        Bitmap productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
