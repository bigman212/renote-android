package ru.bill.renote.di

import android.content.Context
import ru.bill.renote.data.di.DbProvider

interface AppProvider : DbProvider {
  fun context(): Context
}
