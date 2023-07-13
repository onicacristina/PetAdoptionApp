package com.example.petadoptionapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetSpecieBinding
import com.example.petadoptionapp.presentation.ui.home.PetCategoryModel
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

class PetCategoryAdapterNew(
    diffutils: PetCategoryDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<PetCategoryModel, PetCategoryAdapterNew.PetCategoryViewHolder>(diffutils) {


    class PetCategoryViewHolder(
        binding: ItemPetSpecieBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemPetSpecieBinding>(binding) {

        fun bind(data: PetCategoryModel) {
            bindItem(data)
        }

        private fun bindItem(data: PetCategoryModel) {
            bindIcon(data)
            bindName(data)
            bindSelectedItem(data)
            bindClick(data)
        }

        private fun bindIcon(data: PetCategoryModel) {
            binding.ivPetImage.setImageResource(data.iconDrawable)
        }

        private fun bindName(data: PetCategoryModel) {
            val context = binding.tvPetSpecie.context
            binding.tvPetSpecie.text = context.getString(data.name)
        }

        private fun bindSelectedItem(data: PetCategoryModel) {
            val context = binding.viewPet.context
            val textBlack = ContextCompat.getColor(context, R.color.text_black)
            val textNormal = ContextCompat.getColor(context, R.color.text_pet_specie)

            if (data.isSelected) {
                binding.viewPet.setBackgroundResource(R.drawable.bg_item_pet_category_yellow)
                binding.tvPetSpecie.setTextColor(textBlack)
            } else {
                binding.viewPet.setBackgroundResource(R.drawable.bg_item_pet_category_gray)
                binding.tvPetSpecie.setTextColor(textNormal)
            }
        }

        private fun bindClick(data: PetCategoryModel) {
            binding.itemContainer.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PetCategoryViewHolder(
            ItemPetSpecieBinding.inflate(inflater, parent, false),
            onItemClickListener,
        )
    }

    override fun onBindViewHolder(holder: PetCategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}