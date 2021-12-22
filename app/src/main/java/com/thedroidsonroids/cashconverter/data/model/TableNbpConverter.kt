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

//    private val tablesType = Types.newParameterizedType(List::class.java, Rate::class.java)
//    private val tablesAdapter = Moshi.Builder().build().adapter<List<Rate>>(tablesType)
//
//    @TypeConverter
//    fun toJson(value: List<Rate>): String {
//
//        return tablesAdapter.toJson(value)
//    }
//
//    @TypeConverter
//    fun fromJson(string: String): List<Rate>{
//        return tablesAdapter.fromJson(string).orEmpty()
//    }
}