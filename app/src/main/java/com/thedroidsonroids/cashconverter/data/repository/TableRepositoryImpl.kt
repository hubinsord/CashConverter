package com.thedroidsonroids.cashconverter.data.repository

import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.data.api.NbpApi
import com.thedroidsonroids.cashconverter.data.model.Table
import javax.inject.Inject

class TableRepositoryImpl @Inject constructor(private val nbpApi: NbpApi) {

    suspend fun getTableC(): Resource<Table> {
        return try {
            val response = nbpApi.getTableC()
            val result = response.body()?.get(0)
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else
                Resource. Error("${response.message()} API ERROR" )
        } catch (e: Exception) {
            Resource.Error("${e.message} 12345" ?: "Api response error")
        }
    }

}