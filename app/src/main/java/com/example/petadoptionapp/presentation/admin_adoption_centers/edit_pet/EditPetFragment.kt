package com.example.petadoptionapp.presentation.admin_adoption_centers.edit_pet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentEditPetBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.profile.edit_profile.EditProfileViewModel
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditPetFragment :
    NoBottomNavigationFragment<FragmentEditPetBinding>(R.layout.fragment_edit_pet) {
    override val viewBinding: FragmentEditPetBinding by viewBinding(
        FragmentEditPetBinding::bind
    )
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

    private fun onEvent(event: EditProfileViewModel.Event) {

    }

    private fun render(state: EditProfileViewModel.State) {

    }

}