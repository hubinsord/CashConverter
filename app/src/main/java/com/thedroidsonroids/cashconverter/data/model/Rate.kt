package com.thedroidsonroids.cashconverter.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rate(
    val currency: String,
    val code: String,
    val bid: Double,
    val ask: Double
) : Parcelable