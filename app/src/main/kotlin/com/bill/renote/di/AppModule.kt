package com.bill.renote.di

import com.bill.renote.data.persist.di.RoomModule
import org.koin.dsl.module

object AppModule {

    fun create() = module {

        includes(RoomModule.create())
    }
}