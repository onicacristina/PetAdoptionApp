package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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


const val PICK_IMAGE_REQUEST = 1 // Request code for selecting an image
private const val STORAGE_PERMISSION_REQUEST_CODE = 2

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
            viewModel.addPet()
        }

        viewBinding.tvGender.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetGenderFragment)
        }
        viewBinding.tvSpecie.setOnDebounceClickListener {
            navController.navigate(R.id.selectPetSpecieFragment)
        }

        viewBinding.ivPetImage.setOnClickListener {
            checkStoragePermission()
        }
    }

    private fun onImageClicked() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
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
            AddPetViewModel.Event.SUCCESS -> {
                navController.popBackStack()
            }
        }
    }

    private fun render(state: AddPetViewModel.State) {
        updateSaveButton(state.isEnabledButton)
        if (viewModel.isNotEmptyGender()) {
            viewBinding.tvGender.text = getString(state.gender.stringResource)
        }
        if (viewModel.isNotEmptySpecie())
            viewBinding.tvSpecie.text = getString(state.specie.stringResource)
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }

    fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            return columnIndex?.let { cursor?.getString(it) } ?: ""
        } finally {
            cursor?.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
//            val selectedImageUri: Uri? = Uri.parse(data?.data.toString())

//            val selectedImageUri: Uri? = data.

            // Perform further operations with the selected image URI, such as uploading to a server
            // or displaying it in an ImageView.
//            uploadImage(selectedImageUri)
            if (selectedImageUri != null) {
                viewModel.onImageChanged(getRealPathFromUri(requireContext(), selectedImageUri))
//                viewModel.onImageChanged(Uri.parse(selectedImageUri?.toString()))

            }
            viewBinding.ivPetImage.setImageURI(selectedImageUri)
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            onImageClicked()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onImageClicked()
            } else {
                // permission denied
            }
        }
    }

}