package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemSelectHourBinding
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (EPetCategory) -> Unit

class SelectPetSpecieAdapter(
    diffUtils: SelectPetSpecieDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<EPetCategory, SelectPetSpecieAdapter.ViewHolder>(diffUtils) {

    class ViewHolder(
        binding: ItemSelectHourBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemSelectHourBinding>(binding) {

        fun bind(data: EPetCategory) {
            bindItem(data)
        }

        private fun bindItem(data: EPetCategory) {
            bindHour(data)
            bindClick(data)
        }

        private fun bindHour(data: EPetCategory) {
            val context = binding.tvTime.context
            binding.tvTime.text = context.getString(data.stringResource)
        }

        private fun bindClick(data: EPetCategory) {
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