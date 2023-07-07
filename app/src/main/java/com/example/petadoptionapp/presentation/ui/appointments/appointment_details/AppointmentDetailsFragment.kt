package com.example.petadoptionapp.presentation.ui.appointments.appointment_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentAppointmentDetailsBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentDetailsFragment :
    NoBottomNavigationFragment<FragmentAppointmentDetailsBinding>(R.layout.fragment_appointment_details) {
    override val viewBinding: FragmentAppointmentDetailsBinding by viewBinding(
        FragmentAppointmentDetailsBinding::bind
    )
    override val viewModel: AppointmentDetailsViewModel by viewModels()
    private val args: AppointmentDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.appointment_details)
        initPetDetails()
        initAppointmentTime()
        initDetailsUserTypeToDisplay()
    }


    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.clPhone.setOnDebounceClickListener {
            args.appointmentDetails?.adoptionCenter?.phone?.let { phoneNumber ->
                showDialer(
                    phoneNumber
                )
            }
        }
        viewBinding.clPersonPhone.setOnDebounceClickListener {
            args.appointmentDetails?.user?.phone?.let { phoneNumber ->
                showDialer(
                    phoneNumber
                )
            }
        }
        viewBinding.clEmail.setOnDebounceClickListener {
            args.appointmentDetails?.adoptionCenter?.email?.let { email -> openEmail(email) }
        }
        viewBinding.clPersonEmail.setOnDebounceClickListener {
            args.appointmentDetails?.user?.email?.let { email -> openEmail(email) }
        }
        viewBinding.clLocation.setOnDebounceClickListener {
            args.appointmentDetails?.adoptionCenter?.getFullAddress()
                ?.let { address -> openGoogleMaps(address) }
        }
        viewBinding.btnViewPetDetails.setOnDebounceClickListener {
            val bundle = Bundle()
            bundle.putString(
                Constants.ADOPTION_CENTER_ID,
                args.appointmentDetails?.adoptionCenter?.id
            )
            bundle.putString(Constants.PET_ID, args.appointmentDetails?.animal?.id)
            navController.navigate(R.id.petDetailsFragment, bundle)
        }
    }


    private fun initObservers() {

    }

    private fun initAppointmentTime() {
        viewBinding.tvTime.text = args.appointmentDetails?.getFormattedDate()
    }

    private fun initPetDetails() {
        viewBinding.tvPetName.text = args.appointmentDetails?.animal?.name
        viewBinding.tvPetBreed.text = args.appointmentDetails?.animal?.breed
        args.appointmentDetails?.animal?.let { initPetImage(it) }
    }

    private fun initPetImage(data: AnimalResponse) {
        if (data.uploadedAssets.isNotEmpty()) {
            val imageUrl = data.uploadedAssets.first().path ?: ""
            Glide.with(viewBinding.ivPet.context).load(imageUrl).into(viewBinding.ivPet)
        } else {
            Glide.with(viewBinding.ivPet.context).load(Constants.PLACEHOLDER_PET_IMAGE)
                .into(viewBinding.ivPet)
        }
    }

    private fun initDetailsUserTypeToDisplay() {
        val userRole = ProfilePrefs().getProfile()?.role
        if (userRole == 1) {
            viewBinding.llPersonDetails.isVisible = true
            viewBinding.llAdoptionCenter.isVisible = false
            initPersonDetails()
        } else {
            viewBinding.llPersonDetails.isVisible = false
            viewBinding.llAdoptionCenter.isVisible = true
            initAdoptionCenterDetails()
        }
    }

    private fun initAdoptionCenterDetails() {
        viewBinding.tvLocation.text = args.appointmentDetails?.adoptionCenter?.getFullAddress()
        viewBinding.tvPhoneNumber.text = args.appointmentDetails?.adoptionCenter?.phone
        viewBinding.tvEmail.text = args.appointmentDetails?.adoptionCenter?.email
        viewBinding.tvAdoptionCenterName.text =
            getString(R.string.adoption_center_name, args.appointmentDetails?.adoptionCenter?.name)

    }

    private fun initPersonDetails() {
        viewBinding.tvPersonName.text =
            getString(R.string.adoption_center_name, args.appointmentDetails?.user?.getFullName())
        if (args.appointmentDetails?.user?.phone?.isNotEmpty() == true) {
            viewBinding.clPersonPhone.isVisible = true
            viewBinding.tvPersonPhoneNumber.text = args.appointmentDetails?.user?.phone
        } else {
            viewBinding.clPersonPhone.isVisible = false
        }
        viewBinding.tvPersonEmail.text = args.appointmentDetails?.user?.email
    }
}