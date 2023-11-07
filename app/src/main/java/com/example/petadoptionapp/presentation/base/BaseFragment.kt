package com.example.petadoptionapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.petadoptionapp.R
import com.example.petadoptionapp.presentation.ui.main.MainActivity
import com.example.petadoptionapp.presentation.utils.showOkDialog
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment {

    open var navigationVisibility = View.VISIBLE

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected open val viewModel: BaseViewModel? = null

    protected val navController: NavController
        get() = NavHostFragment.findNavController(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        bottomNavigationVisibility()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel?.error?.collect {
                        showErrorPopup(it)
                    }
                }
            }
        }
    }

    private fun bottomNavigationVisibility() {
        getMainActivity()?.bottomNavigationVisibility(navigationVisibility)
    }

    protected fun getMainActivity(): MainActivity? {
        return activity?.let { mainActivity ->
            (mainActivity as MainActivity)
        }
    }

    protected fun showErrorPopup(errorMessage: String?) {
        errorMessage?.let { message ->
            context?.let {
                showOkDialog(
                    it,
                    getString(R.string.something_went_wrong),
                    message,
                    getString(R.string.btn_ok)
                )
            }
        }
    }

}