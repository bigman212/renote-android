package com.bill.renote.data.prefs

import android.content.Context

private const val PREFS_NAME = "renote_deletable.prefs"
private const val KEY_IS_FIRST_LAUNCH = "KEY_IS_FIRST_LAUNCH"

class CommonPrefs(context: Context) : AppSharedPreferences(
    prefsName = PREFS_NAME,
    prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
) {
    var isFirstLaunch: Boolean by BooleanPreference(KEY_IS_FIRST_LAUNCH, false)
}