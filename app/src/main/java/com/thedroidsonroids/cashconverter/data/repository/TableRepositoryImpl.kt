package com.thedroidsonroids.cashconverter.data.repository

import androidx.room.withTransaction
import com.thedroidsonroids.cashconverter.core.resource.NetworkConnectionChecker
import com.thedroidsonroids.cashconverter.core.resource.networkBoundResource
import com.thedroidsonroids.cashconverter.data.api.NbpApi
import com.thedroidsonroids.cashconverter.data.db.TableNbpDB
import com.thedroidsonroids.cashconverter.domain.TableNbpRepository
import javax.inject.Inject

class TableRepositoryImpl @Inject constructor(
    private val nbpApi: NbpApi,
    private val tableNbpDB: TableNbpDB,
    private val networkConnectionChecker: NetworkConnectionChecker
): TableNbpRepository {
    private val tableNbpDao = tableNbpDB.getTableNbpDao()


    override fun getTableNbp() = networkBoundResource(
        query = {
            tableNbpDao.getTableNbp()
        },
        fetch = {
            nbpApi.getTables()
        },
        saveFetchResult = {
            tableNbpDB.withTransaction {
                tableNbpDao.deleteTableNbp()
                tableNbpDao.insertTableNbp(it)
            }
        },
        shouldFetch = {
            networkConnectionChecker.isConnected()
        }
    )

}