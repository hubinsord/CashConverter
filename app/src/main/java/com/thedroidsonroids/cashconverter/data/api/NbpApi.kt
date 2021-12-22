package com.thedroidsonroids.cashconverter.data.api

import com.thedroidsonroids.cashconverter.data.model.TableNbp
import retrofit2.Response
import retrofit2.http.GET

interface NbpApi {

    @GET("exchangerates/tables/c")
    suspend fun getTableC(): Response<List<TableNbp>>

    @GET("exchangerates/tables/c")
    suspend fun getTables(): List<TableNbp>

    companion object {
        const val BASE_URL = "https://api.nbp.pl/api/"
    }
}