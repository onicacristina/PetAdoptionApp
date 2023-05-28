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
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
            }
        }
    }

    private fun initProfile(user: User) {
        viewBinding.tvAvatar.text = user.getInitials()
        viewBinding.tvFullName.text = user.getFullName()
        viewBinding.tvEmail.text = user.email
    }

}