package ru.bill.renote

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView

class GradientTextView : AppCompatTextView {
  var gradientEnabled = true

  constructor(context: Context) : super(context, null, -1)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs, -1)

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    if (gradientEnabled && changed) {
      paint.shader = LinearGradient(
        0f, 0f, 0f, height.toFloat(),
        Color.BLACK, Color.WHITE,
        Shader.TileMode.CLAMP
      )
    }
  }
}