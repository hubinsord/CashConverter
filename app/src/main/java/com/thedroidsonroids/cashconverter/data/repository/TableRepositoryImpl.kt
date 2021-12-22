package com.thedroidsonroids.cashconverter.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.core.resource.networkBoundResource
import com.thedroidsonroids.cashconverter.data.api.NbpApi
import com.thedroidsonroids.cashconverter.data.db.TableNbpDB
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import javax.inject.Inject

class TableRepositoryImpl @Inject constructor(
    private val nbpApi: NbpApi,
    private val tableNbpDB: TableNbpDB,
) {
    private val tableNbpDao = tableNbpDB.getTableNbpDao()


    fun getTableNbp() = networkBoundResource(
        query = {
            tableNbpDao.getTableNbP()
        },
        fetch = {
            nbpApi.getTables()
        },
        saveFetchResult = {
            tableNbpDB.withTransaction {
                tableNbpDao.deleteTableNbP()
                tableNbpDao.insertTableNbp(it)

            }
        }
    )

    suspend fun getTableC(): Resource<TableNbp> {
        return try {
            val response = nbpApi.getTableC()
            val result = response.body()?.get(0)
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else
                Resource.Error("${response.message()} API ERROR")
        } catch (e: Exception) {
            Resource.Error("${e.message} 12345" ?: "Api response error")
        }
    }

}