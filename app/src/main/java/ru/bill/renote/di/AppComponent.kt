package ru.bill.renote.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.bill.renote.MainActivity
import ru.bill.renote.data.di.RoomModule
import javax.inject.Singleton


@Singleton
@Component(
  modules = [RoomModule::class, AndroidModule::class]
)
interface AppComponent {

  fun inject(obj: MainActivity)

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Application): AppComponent
  }

  companion object {
    fun init(application: Application): AppComponent {

//      val androidToolsProvider = AndroidComponent.Builder.build(application)

      return DaggerAppComponent.factory()
        .create(application)
    }
  }
}
