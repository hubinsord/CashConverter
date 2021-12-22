package com.thedroidsonroids.cashconverter.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TableNbpConverter {

    @TypeConverter
    fun toJson(value: List<Rate>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Rate>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJson(string: String): List<Rate>{
        val gson = Gson()
        val type = object : TypeToken<List<Rate>>() {}.type
        return gson.fromJson(string, type)
    }
}