package com.bill.renote.di

import com.bill.renote.AppScope
import com.bill.renote.data.InAppFeatureFlagProvider
import com.bill.renote.data.persist.di.RoomModule
import com.bill.renote.data.prefs.PreferencesModule
import com.bill.renote.noteList.di.NoteListModule
import org.koin.dsl.module

object AppModule {

    fun create() = module {
        single { InAppFeatureFlagProvider().provide() }
        single { AppScope() }

        includes(
            RoomModule.create(),
            NoteListModule.create(),
            PreferencesModule.create()
        )
    }
}