package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemSelectHourBinding
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (EPetGender) -> Unit

class SelectPetGenderAdapter(
    diffUtils: SelectPetGenderDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<EPetGender, SelectPetGenderAdapter.ViewHolder>(diffUtils) {

    class ViewHolder(
        binding: ItemSelectHourBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemSelectHourBinding>(binding) {

        fun bind(data: EPetGender) {
            bindItem(data)
        }

        private fun bindItem(data: EPetGender) {
            bindHour(data)
            bindClick(data)
        }

        private fun bindHour(data: EPetGender) {
            val context = binding.tvTime.context
            binding.tvTime.text = context.getString(data.stringResource)
        }

        private fun bindClick(data: EPetGender) {
            binding.container.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemSelectHourBinding.inflate(inflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }
}