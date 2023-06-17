package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemSelectSpecieBinding
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (EPetCategory) -> Unit

class SelectPetSpecieAdapter(
    diffUtils: SelectPetSpecieDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<EPetCategory, SelectPetSpecieAdapter.ViewHolder>(diffUtils) {

    class ViewHolder(
        binding: ItemSelectSpecieBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemSelectSpecieBinding>(binding) {

        fun bind(data: EPetCategory) {
            bindItem(data)
        }

        private fun bindItem(data: EPetCategory) {
            bindName(data)
            bindIcon(data)
            bindClick(data)
        }

        private fun bindName(data: EPetCategory) {
            val context = binding.tvPetSpecie.context
            binding.tvPetSpecie.text = context.getString(data.stringResource)
        }

        private fun bindIcon(data: EPetCategory) {
            binding.ivPetImage.setImageResource(data.iconResource)
        }

        private fun bindClick(data: EPetCategory) {
            binding.itemContainer.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemSelectSpecieBinding.inflate(inflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }
}