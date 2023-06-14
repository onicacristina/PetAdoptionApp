package com.example.petadoptionapp.presentation.ui.appointments.past_appointments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemAppointmentBinding
import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter.AppointmentsDiffUtils
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder


class PastAppointmentsAdapter(
    diffutils: AppointmentsDiffUtils,
) : ListAdapter<Booking, PastAppointmentsAdapter.ViewHolder>(diffutils) {


    class ViewHolder(
        binding: ItemAppointmentBinding,
    ) : ViewBindingViewHolder<ItemAppointmentBinding>(binding) {

        fun bind(data: Booking) {
            bindItem(data)
        }

        private fun bindItem(data: Booking) {
            bindIcon(data)
            bindName(data)
            bindBreed(data)
            bindLocation(data)
            bindTime(data)
            bindViewDetailsClick(data)
            bindCancelClick(data)
        }

        private fun bindIcon(data: Booking) {
//            Glide.with(binding.ivPet.context).load(data.imageUrl).into(binding.ivPet)

        }

        private fun bindName(data: Booking) {
//            binding.tvPetName.text = data.name
        }

        private fun bindBreed(data: Booking) {
//            binding.tvPetBreed.text = data.name
        }

        private fun bindLocation(data: Booking) {
//            binding.tvLocation.text = data.name
        }

        private fun bindTime(data: Booking) {
//            binding.tvTime.text = data.name
        }

        private fun bindViewDetailsClick(data: Booking) {
            binding.btnViewDetails.isVisible = false
        }

        private fun bindCancelClick(data: Booking) {
            binding.btnCancel.isVisible = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemAppointmentBinding.inflate(inflater, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = getItem(position)
        holder.bind(appointment)
    }
}