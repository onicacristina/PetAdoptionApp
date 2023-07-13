package com.example.petadoptionapp.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UploadedAssets(
    val id: String,
    val originalName: String,
    val fileName: String,
    val path: String,
    val size: Long
): Parcelable
