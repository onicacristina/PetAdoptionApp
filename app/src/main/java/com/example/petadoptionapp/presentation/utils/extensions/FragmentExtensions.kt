@file:Suppress("UNCHECKED_CAST")

package com.example.petadoptionapp.presentation.utils.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.petadoptionapp.presentation.utils.FragmentViewBindingProperty

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) = FragmentViewBindingProperty(this, viewBindingFactory)

fun <T : Any> Fragment.argument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as T
}

fun <T : Any> Fragment.argument(key: String, default: T): Lazy<T> = lazy {
    arguments?.get(key) as? T ?: default
}

fun <T : Any> Fragment.argumentOrNull(key: String): Lazy<T?> = lazy {
    arguments?.get(key) as? T
}
