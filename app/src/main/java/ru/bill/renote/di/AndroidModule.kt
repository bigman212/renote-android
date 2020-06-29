package ru.bill.renote.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AndroidModule {
  @Singleton
  @Provides
  fun provideContext(application: Application): Context {
    return application.applicationContext
  }
}
