package ru.bill.renote.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class])
interface AndroidComponent {

  fun context(): Context

  @Component.Builder
  interface ComponentBuilder {

    @BindsInstance
    fun application(daggerApplication: Application): ComponentBuilder

    fun build(): AndroidComponent
  }

  class Builder private constructor() {
    companion object {
      fun build(daggerApplication: Application): AndroidComponent {
        return DaggerAndroidComponent.builder()
          .application(daggerApplication)
          .build()
      }
    }
  }
}
