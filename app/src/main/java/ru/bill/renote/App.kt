package ru.bill.renote

import android.app.Application
import ru.bill.renote.data.AppDatabase

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
