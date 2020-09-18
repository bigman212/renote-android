package ru.bill.renote.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import ru.bill.renote.persist.di.DbProvider

interface AppProvider : DbProvider {
  fun context(): Context
  fun fragmentFactory(): FragmentFactory
}
