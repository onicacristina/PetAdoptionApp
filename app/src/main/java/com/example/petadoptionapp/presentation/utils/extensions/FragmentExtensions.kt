@file:Suppress("UNCHECKED_CAST")

package com.example.petadoptionapp.presentation.utils.extensions

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.petadoptionapp.presentation.utils.FragmentViewBindingProperty

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingProperty(this, viewBindingFactory)

fun <T : Any> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T
}

fun <T : Any> Fragment.argument(key: String, default: T): Lazy<T> = lazy {
    arguments?.get(key) as? T ?: default
}

fun <T : Any> Fragment.argumentOrNull(key: String): Lazy<T?> = lazy {
    arguments?.get(key) as? T
}

fun Fragment.showDialer(tel: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$tel")
        startActivity(intent)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Fragment.openEmail(email: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        startActivity(Intent.createChooser(intent, ""))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Fragment.openGoogleMaps(address: String) {
    try {
        val encodedAddress = Uri.encode(address)
        val uri = "geo:0,0?q=$encodedAddress"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}