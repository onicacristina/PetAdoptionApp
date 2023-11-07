package com.example.petadoptionapp.presentation.base

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<T : ViewBinding>(@LayoutRes contentLayoutId: Int) : BaseFragment(contentLayoutId) {

    abstract val viewBinding: T

}