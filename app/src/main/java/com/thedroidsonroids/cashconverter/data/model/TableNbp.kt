package com.thedroidsonroids.cashconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.thedroidsonroids.cashconverter.data.Constants

@Entity(tableName = Constants.DB_NAME)
data class TableNbp(
    val table: String,
    val no: String,
    val tradingDate: String,
    @PrimaryKey val effectiveDate: String,
    val rates: List<Rate>,
)