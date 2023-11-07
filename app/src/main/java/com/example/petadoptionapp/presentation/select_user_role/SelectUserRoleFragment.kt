package com.example.petadoptionapp.presentation.select_user_role

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentSelectUserRoleBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectUserRoleFragment :
    NoBottomNavigationFragment<FragmentSelectUserRoleBinding>(R.layout.fragment_select_user_role) {
    override val viewBinding: FragmentSelectUserRoleBinding by viewBinding(
        FragmentSelectUserRoleBinding::bind
    )
    override val viewModel: SelectUserRoleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initAdoptionCenterView()
        initNormalUserView()
    }

    private fun initNormalUserView() {
        viewBinding.normalUser.ivImage.setImageResource(R.drawable.ic_normal_user)
        viewBinding.normalUser.tvTitle.text = getString(R.string.user_type_normal)
        viewBinding.normalUser.tvSubtitle.text = getString(R.string.user_type_normal_description)
    }

    private fun initAdoptionCenterView() {
        viewBinding.adoptionCenter.ivImage.setImageResource(R.drawable.ic_no_appointments)
        viewBinding.adoptionCenter.tvTitle.text = getString(R.string.user_type_adoption_center)
        viewBinding.adoptionCenter.tvSubtitle.text =
            getString(R.string.user_type_adoption_center_description)
    }

    private fun initListeners() {
        viewBinding.normalUser.cvContainer.setOnClickListener {
            viewModel.onNormalUserSelected()
        }
        viewBinding.adoptionCenter.cvContainer.setOnClickListener {
            viewModel.onAdoptionCenterSelected()
        }
        viewBinding.btnContinue.setOnDebounceClickListener {
            viewModel.onContinueClicked()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        renderState(value)
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        onEvent(event)
                    }
                }
            }
        }
    }

    private fun onEvent(event: SelectUserRoleViewModel.Event) {
        when (event) {
            SelectUserRoleViewModel.Event.SELECTED -> {
                onSelectedUserType()
            }
            SelectUserRoleViewModel.Event.UNSELECTED -> {
                showErrorPopup(getString(R.string.select_tip))
            }
        }
    }

    private fun onSelectedUserType() {
        val userRole = viewModel.getSelectedUserType()
        ProfilePrefs().saveUserRole(userRole)
        navController.navigate(R.id.loginFragment)
    }

    private fun renderState(state: SelectUserRoleViewModel.State) {

        val colorUnselect = ContextCompat.getColor(requireContext(), R.color.user_type_bg_unselected)
        val colorUnselectStateList = ColorStateList.valueOf(colorUnselect)

        val colorSelected = ContextCompat.getColor(requireContext(), R.color.user_type_bg_selected)
        val colorSelectedStateList = ColorStateList.valueOf(colorSelected)

        if (state.normalUserSelected) {
            viewBinding.normalUser.cvContainer.setCardBackgroundColor(colorSelectedStateList)
            viewBinding.adoptionCenter.cvContainer.setCardBackgroundColor(colorUnselectStateList)
        } else {
            viewBinding.normalUser.cvContainer.setCardBackgroundColor(colorUnselectStateList)
        }

        if (state.adoptionCenterSelected) {
            viewBinding.adoptionCenter.cvContainer.setCardBackgroundColor(colorSelectedStateList)
            viewBinding.normalUser.cvContainer.setCardBackgroundColor(colorUnselectStateList)
        } else {
            viewBinding.adoptionCenter.cvContainer.setCardBackgroundColor(colorUnselectStateList)
        }
    }
}