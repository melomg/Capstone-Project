package com.projects.melih.wonderandwander.repository.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.projects.melih.wonderandwander.model.Category;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class Converters {
    private Converters() {
        // no-op
    }

    @TypeConverter
    public static List<Category> fromCategoriesString(String value) {
        Type listType = new TypeToken<List<Category>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromCategoryList(List<Category> list) {
        return new Gson().toJson(list);
    }
}