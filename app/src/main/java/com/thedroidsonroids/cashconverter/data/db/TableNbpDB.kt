package com.thedroidsonroids.cashconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thedroidsonroids.cashconverter.data.dao.TableNbpDao
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import com.thedroidsonroids.cashconverter.data.model.TableNbpConverter

@TypeConverters(TableNbpConverter::class)
@Database(entities = [TableNbp::class], version = 1)
abstract class TableNbpDB: RoomDatabase() {

    abstract fun getTableNbpDao(): TableNbpDao
}