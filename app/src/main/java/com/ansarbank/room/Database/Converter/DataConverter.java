package com.ansarbank.room.Database.Converter;

import android.arch.persistence.room.TypeConverter;

import com.ansarbank.room.Database.Card.Card;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Card> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Card>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Card> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Card>>() {
        }.getType();
        List<Card> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
