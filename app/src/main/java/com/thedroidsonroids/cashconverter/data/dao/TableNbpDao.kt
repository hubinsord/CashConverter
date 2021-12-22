package com.thedroidsonroids.cashconverter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thedroidsonroids.cashconverter.data.Constants.DB_NAME
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import kotlinx.coroutines.flow.Flow

@Dao
interface TableNbpDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTableNbp(tablesNbp: List<TableNbp>)

    @Query("DELETE FROM $DB_NAME")
    suspend fun deleteTableNbp()

    @Query("SELECT * FROM $DB_NAME ")
    fun getTableNbp(): Flow<List<TableNbp>>

    @Query("SELECT * FROM $DB_NAME ")
    fun getTestTableNbP(): List<TableNbp>
}
