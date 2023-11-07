package com.example.petadoptionapp.presentation.ui.profile.edit_profile

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentEditProfileBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment :
    NoBottomNavigationFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {
    override val viewBinding: FragmentEditProfileBinding by viewBinding(FragmentEditProfileBinding::bind)
    override val viewModel: EditProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.edit_profile)
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }

        viewBinding.etFirstName.doAfterTextChanged { text ->
            viewModel.onFirstNameChanged(text.toString())
        }
        viewBinding.etLastName.doAfterTextChanged { text ->
            viewModel.onLastNameChanged(text.toString())
        }
        viewBinding.etPhoneNumber.doAfterTextChanged { text ->
            viewModel.onPhoneNumberChanged(text.toString())
        }

        viewBinding.btnSaveChanges.setOnClickListener {
            viewModel.editUser()
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

    private fun render(state: EditProfileViewModel.State) {
        viewBinding.etFirstName.setText(state.firstName, TextView.BufferType.EDITABLE)
        viewBinding.etFirstName.setSelection(state.firstName.length)

        viewBinding.etLastName.setText(state.lastName, TextView.BufferType.EDITABLE)
        viewBinding.etLastName.setSelection(state.lastName.length)

        viewBinding.etPhoneNumber.setText(state.phone, TextView.BufferType.EDITABLE)
        viewBinding.etPhoneNumber.setSelection(state.phone.length)

        viewBinding.tvAvatar.text = state.user?.getInitials()
        viewBinding.tvJoined.text =
            state.user?.getFormattedCreationDate(LocaleHelper.getLocale().locale)

    }

    private fun onEvent(event: EditProfileViewModel.Event) {
        when (event) {
            EditProfileViewModel.Event.SUCCESS -> navController.popBackStack()
        }
    }

}
