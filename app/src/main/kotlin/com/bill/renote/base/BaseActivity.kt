package com.bill.renote.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity {
    constructor() : super()
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    companion object {
        val TAG = this::class.java.simpleName
    }
}
