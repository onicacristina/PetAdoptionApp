package com.example.petadoptionapp.presentation.admin_adoption_centers.add_adoption_center_details

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentAddAdoptionCenterBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAdoptionCenterFragment :
    NoBottomNavigationFragment<FragmentAddAdoptionCenterBinding>(R.layout.fragment_add_adoption_center) {
    override val viewBinding: FragmentAddAdoptionCenterBinding by viewBinding(
        FragmentAddAdoptionCenterBinding::bind
    )
    override val viewModel: AddAdoptionCenterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }


    private fun initViews() {
    }

    private fun initListeners() {
//        viewBinding.etName.doAfterTextChanged { text ->
//            viewModel.onNameChanges(text.toString())
//        }
        viewBinding.etPhone.doAfterTextChanged { text ->
            viewModel.onPhoneNumberChanged(text.toString())
        }
        viewBinding.etAddress.doAfterTextChanged { text ->
            viewModel.onAddressChanged(text.toString())
        }
        viewBinding.etCity.doAfterTextChanged { text ->
            viewModel.onCityChanged(text.toString())
        }

        viewBinding.btnSave.setOnDebounceClickListener {
            viewModel.addAdoptionCenter()
        }
        viewBinding.tvStartTime.setOnClickListener {
            navController.navigate(R.id.selectStartHourFragment)
        }
        viewBinding.tvEndTime.setOnClickListener {
            navController.navigate(R.id.selectEndHourFragment)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        render(value)
                    }
                }
                launch {
                    viewModel.event.collect { value ->
                        onEvent(value)
                    }
                }
            }
        }
    }

    private fun onEvent(event: AddAdoptionCenterViewModel.Event) {
        when (event) {
            AddAdoptionCenterViewModel.Event.SUCCESS_ADD_ADOPTION_CENTER -> {
                viewModel.linkAdminUserToAdoptionCenter()
            }
            AddAdoptionCenterViewModel.Event.SUCCESS_LINK -> {
                openLoginScreen()
            }
        }
    }

    private fun openLoginScreen() {
        navController.navigate(R.id.loginFragment)
    }

    private fun render(state: AddAdoptionCenterViewModel.State) {
        updateSaveButton(state.isEnabledButton)
        if (state.startTime.isNotEmpty())
            viewBinding.tvStartTime.text = state.startTime
        if (state.endTime.isNotEmpty())
            viewBinding.tvEndTime.text = state.endTime
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }
}