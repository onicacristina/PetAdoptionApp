package com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.databinding.ItemAppointmentBinding
import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnViewDetailsItemClickListener = (Booking) -> Unit
typealias OnCancelClickListener = (Booking) -> Unit

class UpcomingAppointmentsAdapter(
    diffutils: AppointmentsDiffUtils,
    private val onViewDetailsItemClickListener: OnViewDetailsItemClickListener,
    private val onCancelClickListener: OnCancelClickListener
) : ListAdapter<Booking, UpcomingAppointmentsAdapter.ViewHolder>(diffutils) {


    class ViewHolder(
        binding: ItemAppointmentBinding,
        private val onViewDetailsItemClickListener: OnViewDetailsItemClickListener,
        private val onCancelClickListener: OnCancelClickListener
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
//            Glide.with(binding.ivPet.context).load(data.animal.imageUrl).into(binding.ivPet)
            val imageUrl = "https://images.unsplash.com/photo-1554693190-383dd5302125?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=871&q=80"
            Glide.with(binding.ivPet.context).load(imageUrl).into(binding.ivPet)
        }

        private fun bindName(data: Booking) {
            binding.tvPetName.text = data.animal.name
        }

        private fun bindBreed(data: Booking) {
            binding.tvPetBreed.text = data.animal.breed
        }

        private fun bindLocation(data: Booking) {
            binding.tvLocation.text = data.adoptionCenter.getFullAddress()
        }

        private fun bindTime(data: Booking) {
            binding.tvTime.text = data.getFormattedDate()
        }

        private fun bindViewDetailsClick(data: Booking) {
            binding.btnViewDetails.setOnClickListener {
                onViewDetailsItemClickListener.invoke(data)
            }
        }

        private fun bindCancelClick(data: Booking) {
            binding.btnCancel.setOnClickListener {
                onCancelClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemAppointmentBinding.inflate(inflater, parent, false),
            onViewDetailsItemClickListener,
            onCancelClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = getItem(position)
        holder.bind(appointment)
    }
}