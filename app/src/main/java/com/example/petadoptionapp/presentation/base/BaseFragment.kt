package com.example.petadoptionapp.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected open val viewModel: BaseViewModel? = null

    protected val navController: NavController
        get() = NavHostFragment.findNavController(this)
}