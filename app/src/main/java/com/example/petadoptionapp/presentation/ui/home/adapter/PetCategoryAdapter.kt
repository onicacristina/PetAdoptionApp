package com.example.petadoptionapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetCategoryBinding
import com.example.petadoptionapp.presentation.ui.home.PetCategoryModel
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (PetCategoryModel) -> Unit

class PetCategoryAdapter(
    diffutils: PetCategoryDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<PetCategoryModel, PetCategoryAdapter.PetCategoryViewHolder>(diffutils) {


    class PetCategoryViewHolder(
        binding: ItemPetCategoryBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemPetCategoryBinding>(binding) {

        fun bind(data: PetCategoryModel) {
            bindItem(data)
        }

        private fun bindItem(data: PetCategoryModel) {
            bindIcon(data)
            bindName(data)
            bindSelectedItem(data)
            bindClick(data)
        }

        private fun bindIcon(data: PetCategoryModel){
            binding.ivCategoryIcon.setImageResource(data.iconDrawable)
        }

        private fun bindName(data: PetCategoryModel){
            val context = binding.tvCategoryName.context
            binding.tvCategoryName.text = context.getString(data.name)
        }

        private fun bindSelectedItem(data: PetCategoryModel){
            val context = binding.clItem.context
            if (data.isSelected){
                binding.ivCategoryIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            }
            else
                binding.ivCategoryIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        private fun bindClick(data: PetCategoryModel){
            binding.clItem.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PetCategoryViewHolder(
            ItemPetCategoryBinding.inflate(inflater, parent, false),
            onItemClickListener,
        )
    }

    override fun onBindViewHolder(holder: PetCategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}