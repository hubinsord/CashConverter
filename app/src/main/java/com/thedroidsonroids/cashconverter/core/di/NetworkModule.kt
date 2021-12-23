package com.thedroidsonroids.cashconverter.core.di

import android.content.Context
import com.thedroidsonroids.cashconverter.core.resource.NetworkConnectionChecker
import com.thedroidsonroids.cashconverter.data.Constants
import com.thedroidsonroids.cashconverter.data.api.NbpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideNbpApi(retrofit: Retrofit): NbpApi = retrofit.create(NbpApi::class.java)

    @Singleton
    @Provides
    fun provideNetworkConnectionChecker(@ApplicationContext appContext: Context) = NetworkConnectionChecker(appContext)
}