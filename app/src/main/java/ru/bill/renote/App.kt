package ru.bill.renote

import android.app.Application
import ru.bill.renote.model.AppDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = AppDatabase.instance(this)
    }
}