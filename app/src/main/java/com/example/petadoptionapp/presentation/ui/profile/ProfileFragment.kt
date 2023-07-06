package com.example.petadoptionapp.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentProfileBinding
import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override val viewBinding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initToolbar()
        initSettingsOptions()
    }

    private fun initToolbar() {
        viewBinding.toolbar.ivBack.isVisible = false
        viewBinding.toolbar.tvTitle.text = getString(R.string.profile)
    }

    private fun initSettingsOptions() {
        viewBinding.viewChangePassword.tvOption.text = getString(R.string.change_password)
        viewBinding.viewChangePassword.ivIcon.setImageResource(R.drawable.ic_change_password)
        viewBinding.viewSettings.tvOption.text = getString(R.string.settings)
        viewBinding.viewSettings.ivIcon.setImageResource(R.drawable.ic_settings)
        viewBinding.viewLogOut.tvOption.text = getString(R.string.log_out)
        viewBinding.viewLogOut.ivIcon.setImageResource(R.drawable.ic_log_off)
    }

    private fun initListeners() {
        viewBinding.btnEditProfile.setOnDebounceClickListener {
            navController.navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        viewBinding.viewChangePassword.container.setOnDebounceClickListener {
            navController.navigate(R.id.action_profileFragment_to_changePasswordFragment)
//            navController.navigate(R.id.selectUserRoleFragment)
//            navController.navigate(R.id.addPetFragment)
        }
        viewBinding.viewSettings.container.setOnDebounceClickListener {
            navController.navigate(R.id.action_profileFragment_to_settingsFragment)
        }
        viewBinding.viewLogOut.container.setOnDebounceClickListener {
            viewModel.logOut()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.userProfile.collect { user ->
                        if (user != null) {
                            initProfile(user)
                        }
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

    private fun onEvent(event: ProfileViewModel.Event) {
        when (event) {
            ProfileViewModel.Event.SIGNED_OUT -> signedOut()
        }
    }

    private fun signedOut() {
        navController.popBackStack()
        navController.navigate(R.id.selectUserRoleFragment)
    }

    private fun initProfile(user: User) {
        val userRole = ProfilePrefs().getProfile()?.role
        viewBinding.tvAvatar.text = user.getInitials()
        viewBinding.tvFullName.text = if (userRole == 0) user.getFullName() else user.lastName
        viewBinding.tvEmail.text = user.email
        Timber.e("joined ${user.getFormattedCreationDate(LocaleHelper.getLocale().locale)}")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

}