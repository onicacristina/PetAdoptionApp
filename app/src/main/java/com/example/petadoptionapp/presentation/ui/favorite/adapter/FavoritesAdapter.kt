package com.example.petadoptionapp.presentation.ui.favorite.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetFavoriteBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener

typealias OnItemPetClickListener = (AnimalResponse) -> Unit
typealias OnFavoriteButtonClickListener = (AnimalResponse) -> Unit

class FavoritesAdapter(
    diffutils: FavoritesDiffUtils,
    private val onItemPetClickListener: OnItemPetClickListener,
    private val onFavoriteButtonClickListener: OnFavoriteButtonClickListener
) : ListAdapter<AnimalResponse, FavoritesAdapter.FavoritesViewHolder>(diffutils) {

    class FavoritesViewHolder(
        binding: ItemPetFavoriteBinding,
        private val onItemPetClickListener: OnItemPetClickListener,
        private val onFavoriteButtonClickListener: OnFavoriteButtonClickListener
    ) : ViewBindingViewHolder<ItemPetFavoriteBinding>(binding) {

        fun bind(data: AnimalResponse) {
            bindItem(data)
        }

        private fun bindItem(data: AnimalResponse) {
            bindImage(data)
            bindPetName(data)
            bindPetBreedAndAge(data)
            bindPetGender(data)
//            bindFavorite(data)
            bindItemClick(data)
            bindFavoriteButtonClick(data)
        }

        private fun bindImage(data: AnimalResponse) {
//            Glide.with(binding.ivPetImage.context).load(data.imageUrl).into(binding.ivPetImage)
        }

        private fun bindPetName(data: AnimalResponse) {
            binding.tvPetName.text = data.name
        }

        private fun bindPetGender(data: AnimalResponse) {
            if (data.gender == EPetGender.FEMALE) {
                binding.ivPetGender.setImageResource(EPetGender.FEMALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_female_gender)
            } else {
                binding.ivPetGender.setImageResource(EPetGender.MALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_male_gender)
            }
        }

        @SuppressLint("StringFormatMatches")
        private fun bindPetBreedAndAge(data: AnimalResponse) {
            val context = binding.ivPetGender.context
            val ageCategory = data.getAgeCategory(data)
            val displayName = ageCategory.getDisplayName(data.specie)
            val textToDisplay = context.getString(displayName)
            binding.tvPetBreed.text =
                context.getString(R.string.breed_age, data.breed, textToDisplay)
        }

        private fun bindFavorite(data: AnimalResponse) {
            binding.ivFavorite.setImageResource(if (data.isSaved) R.drawable.ic_favorite_selected else R.drawable.ic_favorite)
        }

        private fun bindItemClick(data: AnimalResponse) {
            binding.cvContainer.setOnDebounceClickListener {
                onItemPetClickListener.invoke(data)
            }
        }

        private fun bindFavoriteButtonClick(data: AnimalResponse) {
            binding.ivFavorite.setOnClickListener {
                onFavoriteButtonClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(
            ItemPetFavoriteBinding.inflate(inflater, parent, false),
            onItemPetClickListener,
            onFavoriteButtonClickListener
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val pet = getItem(position)
        holder.bind(pet)
    }
}