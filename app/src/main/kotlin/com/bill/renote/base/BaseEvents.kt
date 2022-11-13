package com.bill.renote.base

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

class SnackbarButton(
    @StringRes
    val actionButtonResId: Int,
    val onActionClicked: (Snackbar) -> Unit = {}
)

data class ShowSnackbarEvent(
    val message: CharSequence? = null,
    @StringRes val stringId: Int? = null,
    val snackbarButton: SnackbarButton? = null,
    val duration: Int = Snackbar.LENGTH_SHORT
) : Event

interface NavigationEvent : Event

class NavigateToEvent : NavigationEvent
object OnBackPressedEvent : NavigationEvent
object NavigateUpEvent : NavigationEvent
