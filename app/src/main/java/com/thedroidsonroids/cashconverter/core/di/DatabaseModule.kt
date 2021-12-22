package com.thedroidsonroids.cashconverter.core.di

import android.content.Context
import androidx.room.Room
import com.thedroidsonroids.cashconverter.data.Constants
import com.thedroidsonroids.cashconverter.data.dao.TableNbpDao
import com.thedroidsonroids.cashconverter.data.db.TableNbpDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): TableNbpDB =
        Room.databaseBuilder(context, TableNbpDB::class.java, Constants.DB_NAME)
            .build()
}