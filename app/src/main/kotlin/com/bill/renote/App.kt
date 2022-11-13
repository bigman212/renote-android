package com.bill.renote

import android.app.Application
import com.bill.renote.data.InAppFeatureFlags
import com.bill.renote.di.AppModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    private val inAppFeatureFlags: InAppFeatureFlags by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(AppModule.create())
        }

        if (inAppFeatureFlags.isDebugBuild) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
