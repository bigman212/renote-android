package com.bill.renote.data.prefs

import androidx.core.content.edit
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class AppSharedPreferences(
    private val prefsName: String,
    private val prefs: SharedPreferences
) {
    fun getAllRaw(): Map<String, *> = prefs.all.orEmpty()

    inner class BooleanPreference(
        private val key: String,
        private val defaultValue: Boolean
    ) : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean = prefs.getBoolean(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            prefs.edit { putBoolean(key, value) }
        }
    }

    inner class StringPreference(
        private val key: String,
        private val defaultValue: String?
    ) : ReadWriteProperty<Any, String?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String? = prefs.getString(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            if (value == null) {
                prefs.edit { remove(key) }
            } else {
                prefs.edit { putString(key, value) }
            }
        }
    }

    override fun toString(): String = "[AppSharedPreferences($prefsName, ${getAllRaw().size})]"
}

