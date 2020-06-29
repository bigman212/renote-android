package ru.bill.renote

import android.app.Application
import ru.bill.renote.di.AppComponent

class App : Application() {
  override fun onCreate() {
    super.onCreate()
  }

  private val appComponent: AppComponent by lazy {
    AppComponent.init(this)
  }

  fun getApplicationProvider(): AppComponent = appComponent
}
