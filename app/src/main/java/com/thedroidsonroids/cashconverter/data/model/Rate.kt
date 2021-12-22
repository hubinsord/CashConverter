package com.thedroidsonroids.cashconverter.data.model

data class Rate(
    val currency: String,
    val code: String,
    val bid: Double,
    val ask: Double
) {
}