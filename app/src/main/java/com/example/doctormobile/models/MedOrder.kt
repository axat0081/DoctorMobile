package com.example.doctormobile.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedOrder(
    val id: String? = null,
    val email: String? = null,
    val medList: ArrayList<String>? = null
) : Parcelable