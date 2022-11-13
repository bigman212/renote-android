package com.bill.renote.common.extensions

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.bill.renote.R

fun Context.vibrate(duration: Long = 500) {
    val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        manager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(
            duration,
            VibrationEffect.DEFAULT_AMPLITUDE
        )
        vibrator?.vibrate(effect)
    }
}

fun Context.copyToClipBoard(content: String) {
    val clipboard = getSystemService<ClipboardManager>()!!
    val clip = ClipData.newPlainText(getString(R.string.app_name), content)
    clipboard.setPrimaryClip(clip)
}

fun Context.getClipboardText(trimmed: Boolean = true): String? {
    val clipboard = getSystemService<ClipboardManager>()!!
    val text = clipboard.primaryClip?.getItemAt(0)
        ?.text
        ?.toString()
    return if (trimmed) text?.trim() else text
}

fun Context.shareText(value: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, value)
    startActivity(Intent.createChooser(shareIntent, "Share Text"))
}

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableId)
}

fun Context.getColorStateListCompat(@ColorRes colorRes: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, colorRes)
}