package ru.bill.renote.base

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

data class MessageEvent(val message: CharSequence? = null, @StringRes val stringId: Int? = null) : Event
data class ErrorEvent(val errorMessage: CharSequence) : Event

interface NavigationEvent : Event

data class NavigateToEvent(val direction: NavDirections) : NavigationEvent
object OnBackPressedEvent : NavigationEvent
object NavigateUpEvent : NavigationEvent
