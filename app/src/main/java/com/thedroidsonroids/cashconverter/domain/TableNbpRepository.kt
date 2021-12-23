package com.thedroidsonroids.cashconverter.domain

import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import kotlinx.coroutines.flow.Flow

interface TableNbpRepository {
    fun getTableNbp(): Flow<Resource<List<TableNbp>>>
}