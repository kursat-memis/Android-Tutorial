package com.kursatmemis.sharedpreferences.managers

import android.content.Context
import android.util.Log

/**
 * Not: context.getSharedPreferences() methodu ile kaydedilen verinin farklı activity'lerde kullanılmasına
 * izin vermiş oluruz. Eğer kaydedilen verinin sadece ilgili activity'de kullanılmasını istiyorsak
 * context.getPreferences() methodunu kullanmalıyız.
 */

class SharedPrefManager {

    val SHARED_PREF_NAME: String = "MySharedPref"

    // Veriyi kaydeder.
    fun <T>setSharedPreference(context: Context, key: String, value: T){
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()

        when (value) {
            is String -> {
                edit.putString(key, value)
            }

            is Long -> {
                edit.putLong(key, value)
            }

            is Int -> {
                edit.putInt(key, value)
            }

            is Boolean -> {
                edit.putBoolean(key, value)
            }

            is Float -> {
                edit.putFloat(key, value)
            }

            else -> {
                Log.w("mKm - sharedpref", "Tip belirlenemedi.")
            }
        }
        edit.commit()
    }

    // Kaydedilen veriyi alır.
    fun <T>getSharedPreference(context: Context, key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> {
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getString(key, defaultValue) as T
            }

            is Long -> {
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getLong(key, defaultValue) as T
            }

            is Int -> {
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getInt(key, defaultValue) as T
            }

            is Boolean -> {
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getBoolean(key, defaultValue) as T
            }

            is Float -> {
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getFloat(key, defaultValue) as T
            }

            else -> {
                Log.w("mKm - sharedpref", "Tip belirlenemedi.")
                defaultValue
            }
        }
    }

    // Kaydedilen tüm verileri key-value olarak gösterir.
    fun getAll(context: Context) {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val all = sharedPref.all
        for ((key, value) in all) {
            Log.w("mKm - sharedpref", "Key: $key Value: $value")
        }
    }

    // Kaydedilmiş tüm verileri temizler.
    fun clearSharedPreference(context: Context) {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.clear()
        edit.commit()
    }

    fun removeSharedPreference(context: Context, key: String?) {
        val sharedPref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.remove(key)
        edit.commit()
    }
}