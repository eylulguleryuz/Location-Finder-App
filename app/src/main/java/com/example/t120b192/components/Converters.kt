package com.example.t120b192.components

import android.text.TextUtils
import androidx.room.TypeConverter
import java.util.Arrays


object Converters {
    @TypeConverter
    fun stringListFromString(value: String): List<String> {
        return Arrays.asList(*value.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
    }

    @TypeConverter
    fun stringListToString(list: List<String?>?): String {
        return TextUtils.join(",", list!!)
    }

    @TypeConverter
    fun integerListFromString(value: String): List<Int> {
        val resultList: MutableList<Int> = ArrayList()
        val parts = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        for (part in parts) {
            resultList.add(part.toInt())
        }
        return resultList
    }

    @TypeConverter
    fun integerListToString(list: List<Int>): String {
        val stringList: MutableList<String?> = ArrayList()
        for (item in list) {
            stringList.add(item.toString())
        }
        return TextUtils.join(",", stringList)
    }
}



