package com.example.petadoptionapp.presentation.utils.extensions

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat

fun CheckBox.addClickableLink(
    fullText: String,
    linkText: SpannableString,
    textColor: Int,
    context: Context,
    callback: () -> Unit
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents() // Prevent CheckBox state from being toggled when link is clicked
            callback.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, textColor)
            ds.isUnderlineText = true
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)
    val cs = TextUtils.expandTemplate(fullTextWithTemplate, linkText)

    text = cs
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}