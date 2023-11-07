package com.example.petadoptionapp.presentation.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingViewHolder<T : ViewBinding>(protected val binding: T) : RecyclerView.ViewHolder(binding.root)