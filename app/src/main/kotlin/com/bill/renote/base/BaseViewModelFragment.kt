package com.bill.renote.base

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar

abstract class BaseViewModelFragment<VM: AppViewModel> : Fragment() {

    open val snackbarAttachView: View
        get() = requireActivity().findViewById(android.R.id.content)

    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents(viewModel.events, ::onEvent)
    }

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
