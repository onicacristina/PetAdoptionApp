package com.example.petadoptionapp.presentation.ui.favorite.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetFavoriteBinding
import com.example.petadoptionapp.network.models.Animal
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener

typealias OnItemPetClickListener = (Animal) -> Unit
typealias OnFavoriteButtonClickListener = (Animal) -> Unit

class FavoritesAdapter(
    diffutils: FavoritesDiffUtils,
    private val onItemPetClickListener: OnItemPetClickListener,
    private val onFavoriteButtonClickListener: OnFavoriteButtonClickListener
) : ListAdapter<Animal, FavoritesAdapter.FavoritesViewHolder>(diffutils) {

    class FavoritesViewHolder(
        binding: ItemPetFavoriteBinding,
        private val onItemPetClickListener: OnItemPetClickListener,
        private val onFavoriteButtonClickListener: OnFavoriteButtonClickListener
    ) : ViewBindingViewHolder<ItemPetFavoriteBinding>(binding) {

        fun bind(data: Animal) {
            bindItem(data)
        }

        private fun bindItem(data: Animal) {
            bindImage(data)
            bindPetName(data)
            bindPetBreedAndAge(data)
            bindPetGender(data)
//            bindFavorite(data)
            bindItemClick(data)
            bindFavoriteButtonClick(data)
        }

        private fun bindImage(data: Animal) {
            Glide.with(binding.ivPetImage.context).load(data.uploadedAssets)
                .into(binding.ivPetImage)
        }

        private fun bindPetName(data: Animal) {
            binding.tvPetName.text = data.name
        }

        private fun bindPetGender(data: Animal) {
            if (data.gender == EPetGender.FEMALE) {
                binding.ivPetGender.setImageResource(EPetGender.FEMALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_female_gender)
            } else {
                binding.ivPetGender.setImageResource(EPetGender.MALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_male_gender)
            }
        }

        @SuppressLint("StringFormatMatches")
        private fun bindPetBreedAndAge(data: Animal) {
            val context = binding.ivPetGender.context
            val ageCategory = data.getAgeCategory(data)
            val displayName = ageCategory.getDisplayName(data.specie)
            val textToDisplay = context.getString(displayName)
            binding.tvPetBreed.text =
                context.getString(R.string.breed_age, data.breed, textToDisplay)
        }

        private fun bindItemClick(data: Animal) {
            binding.cvContainer.setOnDebounceClickListener {
                onItemPetClickListener.invoke(data)
            }
        }

        private fun bindFavoriteButtonClick(data: Animal) {
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