package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentAddPetBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPetFragment :
    NoBottomNavigationFragment<FragmentAddPetBinding>(R.layout.fragment_add_pet) {
    override val viewBinding: FragmentAddPetBinding by viewBinding(
        FragmentAddPetBinding::bind
    )
    override val viewModel: AddPetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        viewBinding.etName.doAfterTextChanged { text ->
            viewModel.onNameChanges(text.toString())
        }
        viewBinding.etBreed.doAfterTextChanged { text ->
            viewModel.onBreedChanged(text.toString())
        }
        viewBinding.etAge.doAfterTextChanged { text ->
            viewModel.onAgeChanged(text.toString())
        }
        viewBinding.eStory.doAfterTextChanged { text ->
            viewModel.onStoryChanged(text.toString())
        }

        viewBinding.btnSwitchVaccinated.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onVaccinatedChanged(isChecked)
        }
        viewBinding.btnSwitchNeutered.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onNeuteredChanged(isChecked)
        }

        viewBinding.btnSave.setOnDebounceClickListener {
            //todo
        }

        viewBinding.tvGender.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetGenderFragment)
        }
        viewBinding.tvSpecie.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetSpecieFragment)
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

    private fun onEvent(event: AddPetViewModel.Event) {
        when (event) {
            AddPetViewModel.Event.SUCCESS -> TODO()
        }
    }

    private fun render(state: AddPetViewModel.State) {
        updateSaveButton(state.isEnabledButton)
        if (viewModel.isNotEmptyGender())
        {
            viewBinding.tvGender.text = getString(state.gender.stringResource)
        }
        if (viewModel.isNotEmptySpecie())
            viewBinding.tvSpecie.text = getString(state.specie.stringResource)
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }
}