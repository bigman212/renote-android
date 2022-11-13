package ru.bill.renote.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import ru.bill.renote.App
import ru.bill.renote.di.AppComponent

open class BaseActivity : AppCompatActivity {
  constructor() : super()
  constructor(@LayoutRes layoutId: Int) : super(layoutId)

  companion object {
    val TAG = this::class.java.simpleName
  }

  protected val appComponent: AppComponent by lazy {
    (applicationContext as App).getApplicationProvider()
  }
}
