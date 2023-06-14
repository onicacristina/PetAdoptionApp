package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemLanguageBinding
import com.example.petadoptionapp.presentation.ui.profile.settings.language_settings.Language

typealias OnItemClickListener = (Language) -> Unit

class LanguageAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<Language, LanguageAdapter.ViewHolder>(LanguageDiffUtils) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLanguageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemLanguageBinding,
        private val onItemClickListener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Language) {
            val context = binding.tvText.context
            binding.tvText.text = context.getString(item.language)

            binding.clLanguage.setOnClickListener {
                onItemClickListener.invoke(item)
            }

            bindSelected(item)
        }

        private fun bindSelected(data: Language) {
            if (data.isSelected)
                binding.ivArrow.setImageResource(R.drawable.ic_language_checked)
            else
                binding.ivArrow.setImageResource(R.drawable.ic_language_unchecked)
        }
    }
}