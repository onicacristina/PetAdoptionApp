package com.example.petadoptionapp.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.petadoptionapp.R
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener

fun showOkDialog(
    context: Context,
    title: String,
    description: String = "",
    positiveActionText: String,
    negativeActionText: String = "",
    positiveAction: (() -> Unit)? = null,
    negativeAction: (() -> Unit)? = null,
    dismissAction: (() -> Unit)? = null,
    isCancellable: Boolean = true
) {
    val dialog = Dialog(context, R.style.CustomDialog)
    dialog.setContentView(R.layout.layout_popup)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    dialog.findViewById<TextView>(R.id.tvPopUpTitle).text = title
    dialog.findViewById<TextView>(R.id.tvPopUpDescription).text = description
    dialog.findViewById<Button>(R.id.btnPositive)
        .setBackgroundResource(R.drawable.selector_yellow_button)
    dialog.findViewById<Button>(R.id.btnPositive).text = positiveActionText
    dialog.findViewById<Button>(R.id.btnPositive).setOnDebounceClickListener {
        if (positiveAction != null) {
            positiveAction()
        }
        dismissAction?.invoke()
        dialog.dismiss()
    }

    dialog.findViewById<Button>(R.id.btnNegative).isVisible = negativeActionText.isNotEmpty()
    dialog.findViewById<Button>(R.id.btnNegative).text = negativeActionText
    dialog.findViewById<Button>(R.id.btnNegative).setOnDebounceClickListener {
        if (negativeAction != null) {
            negativeAction()
        }
        dismissAction?.invoke()
        dialog.dismiss()
    }
    dialog.setCancelable(isCancellable)
    dialog.show()
}

fun showDialog(
    context: Context,
    title: String,
    description: String,
    positiveActionButtonColor: Int,
    positiveActionText: String,
    positiveAction: (() -> Unit)?,
    negativeActionText: String,
    negativeAction: (() -> Unit)?,
    isCancellable: Boolean = true
) {
    val dialog = Dialog(context, R.style.CustomDialog)
    dialog.setContentView(R.layout.layout_popup)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    dialog.findViewById<TextView>(R.id.tvPopUpTitle).text = title
    dialog.findViewById<TextView>(R.id.tvPopUpDescription).text = description
    dialog.findViewById<Button>(R.id.btnPositive).setBackgroundResource(positiveActionButtonColor)
    dialog.findViewById<Button>(R.id.btnPositive).text = positiveActionText
    dialog.findViewById<Button>(R.id.btnPositive).setOnDebounceClickListener {
        if (positiveAction != null) {
            positiveAction()
        }
        dialog.dismiss()
    }
    dialog.findViewById<Button>(R.id.btnNegative).text = negativeActionText
    dialog.findViewById<Button>(R.id.btnNegative).setOnDebounceClickListener {
        if (negativeAction != null) {
            negativeAction()
        }
        dialog.dismiss()
    }
    dialog.setCancelable(isCancellable)
    dialog.show()
}
