package ru.bill.renote.di

import android.content.Context
import ru.bill.renote.data.di.AppDatabaseProvider

interface AppProvider : AppDatabaseProvider {
  fun context(): Context
}