package com.thedroidsonroids.cashconverter.data.api

import com.thedroidsonroids.cashconverter.data.model.TableNbp
import retrofit2.Response
import retrofit2.http.GET

interface NbpApi {

    @GET("exchangerates/tables/c")
    suspend fun getTables(): List<TableNbp>
}