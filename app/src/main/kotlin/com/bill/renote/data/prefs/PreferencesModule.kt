package com.bill.renote.data.prefs

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object PreferencesModule {
    fun create() = module {
        singleOf(::CommonPrefs)
    }
}