package com.bill.renote.base

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment {
    constructor() : super()
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    open val snackbarAttachView: View
        get() = requireActivity().findViewById(android.R.id.content)

    @CallSuper
    protected open fun onEvent(event: Event) {
        when (event) {
            is ShowSnackbarEvent -> {
                val message = event.stringId?.let(::getString) ?: event.message!!
                showSnackbar(
                    message = message,
                    actionButton = event.snackbarButton,
                    duration = event.duration
                )
            }
        }
    }

    private fun showSnackbar(
        message: CharSequence,
        actionButton: SnackbarButton?,
        duration: Int
    ) {
        Snackbar.make(snackbarAttachView, message, duration).apply {
            actionButton?.let { button -> setAction(button.actionButtonResId) { button.onActionClicked(this) } }
        }
            .show()
    }
}
