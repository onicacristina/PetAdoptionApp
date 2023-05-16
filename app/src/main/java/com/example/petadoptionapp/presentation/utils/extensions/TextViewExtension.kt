package com.example.petadoptionapp.presentation.utils.extensions

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.addClickableLink(
    fullText: String,
    linkText: SpannableString,
    textColor: Int,
    context: Context,
    isBolded: Boolean? = null,
    isUnderlined: Boolean? = null,
    callback: (() -> Unit)? = null
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            callback?.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, textColor)
            if (isUnderlined == true) {
                ds.isUnderlineText = true // Show links with underlines
            }
            if (isBolded == true) {
                ds.typeface = Typeface.DEFAULT_BOLD // show the text bolded
            }
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)
    val cs = TextUtils.expandTemplate(fullTextWithTemplate, linkText)

    text = cs
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}