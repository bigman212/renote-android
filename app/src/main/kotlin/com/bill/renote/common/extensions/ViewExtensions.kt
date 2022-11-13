package com.bill.renote.common.extensions

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.TextView

fun TextView.setTextBold(){
    setTypeface(null, Typeface.BOLD)
}

fun TextView.setTextNormal(){
    setTypeface(null, Typeface.NORMAL)
}

fun TextView.setTextItalic(){
    setTypeface(null, Typeface.ITALIC)
}