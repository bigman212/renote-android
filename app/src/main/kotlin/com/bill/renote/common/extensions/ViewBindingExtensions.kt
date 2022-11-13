package com.bill.renote.common.extensions

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.ViewGroup

val ViewBinding.context: Context
    get() = root.context

val ViewBinding.resources: Resources
    get() = context.resources

fun ViewBinding.getString(@StringRes stringId: Int, vararg formatArgs: Any): String =
    if (formatArgs.isNotEmpty()) {
        context.getString(stringId, *formatArgs)
    } else {
        context.getString(stringId)
    }

@ColorInt
fun ViewBinding.getColor(@ColorRes colorId: Int, theme: Resources.Theme? = context.theme): Int =
    ResourcesCompat.getColor(resources, colorId, theme)

fun ViewBinding.getDrawable(@DrawableRes id: Int, theme: Resources.Theme? = context.theme): Drawable? =
    ResourcesCompat.getDrawable(resources, id, theme)

operator fun ViewGroup.plusAssign(binding: ViewBinding) {
    addView(binding.root)
}