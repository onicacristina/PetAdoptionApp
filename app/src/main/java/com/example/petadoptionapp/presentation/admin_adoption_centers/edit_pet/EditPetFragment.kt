package com.example.petadoptionapp.presentation.admin_adoption_centers.edit_pet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentEditPetBinding
import com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.PICK_IMAGE_REQUEST
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.ImageHelper
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
    override val viewModel: EditPetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.edit_pet)
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }

        viewBinding.etPetName.doAfterTextChanged { text ->
            viewModel.onNameChanges(text.toString())
        }
        viewBinding.etBreed.doAfterTextChanged { text ->
            viewModel.onBreedChanged(text.toString())
        }
        viewBinding.etPetAge.doAfterTextChanged { text ->
            viewModel.onAgeChanged(text.toString())
        }
        viewBinding.etPetStory.doAfterTextChanged { text ->
            viewModel.onStoryChanged(text.toString())
        }

        viewBinding.tvGenderSelected.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetGenderFragment)
        }
        viewBinding.tvSpecieSelected.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetSpecieFragment)
        }

        viewBinding.ivPetImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        viewBinding.btnSwitchVaccinated.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onVaccinatedChanged(isChecked)
        }
        viewBinding.btnSwitchNeutered.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onNeuteredChanged(isChecked)
        }

        viewBinding.btnSave.setOnDebounceClickListener {
            viewModel.editAnimal()
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

    private fun onEvent(event: EditPetViewModel.Event) {
        when (event) {
            EditPetViewModel.Event.SUCCESS -> navController.popBackStack()
        }
    }

    private fun render(state: EditPetViewModel.State) {

        viewBinding.etPetName.setText(state.name, TextView.BufferType.EDITABLE)
        viewBinding.etPetName.setSelection(state.name.length)

        viewBinding.etBreed.setText(state.breed, TextView.BufferType.EDITABLE)
        viewBinding.etBreed.setSelection(state.breed.length)

        viewBinding.etPetAge.setText(state.age, TextView.BufferType.EDITABLE)
        viewBinding.etPetAge.setSelection(state.age.length)

        viewBinding.etPetStory.setText(state.story, TextView.BufferType.EDITABLE)
        viewBinding.etPetStory.setSelection(state.story.length)


        state.image?.let { initPetImage(it) }
        updateSaveButton(state.isEnabledButton)

        viewBinding.tvGenderSelected.text = getString(state.gender.stringResource)
        viewBinding.tvSpecieSelected.text = getString(state.specie.stringResource)

        initNeuteredSwitch(state.neutered)
        initVaccinatedSwitch(state.vaccinated)
    }

    private fun initVaccinatedSwitch(data: Boolean) {
        viewBinding.btnSwitchVaccinated.isChecked = data
    }

    private fun initNeuteredSwitch(data: Boolean) {
        viewBinding.btnSwitchNeutered.isChecked = data
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }

    private fun initPetImage(imageUrl: String) {
        Glide.with(viewBinding.ivPetImage.context).load(imageUrl).into(viewBinding.ivPetImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data ?: return

            // Perform further operations with the selected image URI, such as uploading to a server
            // or displaying it in an ImageView.
//            uploadImage(selectedImageUri)
            viewModel.onImageChanged(ImageHelper().getRealPathFromUri(requireContext(), selectedImageUri))
            viewBinding.ivPetImage.setImageURI(selectedImageUri)
        }
    }

}