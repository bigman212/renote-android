package ru.bill.renote

import android.app.Application
import ru.bill.renote.di.AppComponent
import timber.log.Timber

class App : Application() {
  override fun onCreate() {
    super.onCreate()

    Timber.plant(Timber.DebugTree())
  }

  private val appComponent: AppComponent by lazy {
    AppComponent.init(this)
  }

  fun getApplicationProvider(): AppComponent = appComponent
}
