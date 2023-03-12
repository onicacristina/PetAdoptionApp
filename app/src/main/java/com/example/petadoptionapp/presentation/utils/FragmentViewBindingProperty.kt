package com.example.petadoptionapp.presentation.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Function to create the binding class
 */
typealias ViewBindingFactory<T> = (View) -> T

/**
 * Kotlin custom property that handles the lifecycle of the binding class through a [DefaultLifecycleObserver], when the view lifecycle
 * reaches the destroyed state it is cleared automatically
 */
class FragmentViewBindingProperty<T : ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingFactory: ViewBindingFactory<T>
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    /**
     * Returns the value of the current binding class
     * If it does not exists then calls [viewBindingFactory] to generate a new binding instance
     *
     * @throws: [IllegalStateException] if the view is accessed when the fragment is not visible
     */
    @Throws(IllegalStateException::class)
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("View Binding property is accessed outside the view lifecycle.")
        }
        return viewBindingFactory(thisRef.requireView()).also {
            this.binding = it
        }
    }

}