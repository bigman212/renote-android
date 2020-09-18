package ru.bill.renote.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AndroidModule {
  @Singleton
  @Provides
  fun provideContext(application: Application): Context = application.applicationContext
}
