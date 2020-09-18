package ru.bill.renote.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.bill.renote.MainActivity
import ru.bill.renote.persist.di.RoomModule
import ru.bill.renote.di.modules.AndroidModule
import ru.bill.renote.di.modules.FragmentsModule
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, AndroidModule::class, FragmentsModule::class])
interface AppComponent : AppProvider {

  fun inject(obj: MainActivity)

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): AppComponent
  }

  companion object {
    fun init(application: Application): AppComponent {
      return DaggerAppComponent
        .factory()
        .create(application)
    }
  }
}
