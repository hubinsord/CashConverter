package com.thedroidsonroids.cashconverter.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.thedroidsonroids.cashconverter.data.db.TableNbpDB
import com.thedroidsonroids.cashconverter.data.model.Rate
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import com.thedroidsonroids.cashconverter.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TableNbpDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TableNbpDB
    private lateinit var dao: TableNbpDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TableNbpDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.getTableNbpDao()
    }

    @After
    fun teardown(){
         database.close()
    }

    @Test
    fun insertTable() = runBlockingTest {
        val rate = Rate("euro", "EUR", 4.00, 5.00  )
        val tableNbp = TableNbp("table", "tb/11", "2021-12-12", "2012-12-14", listOf(rate))
        dao.insertTableNbp(listOf(tableNbp))
        val allTables = dao.getTableNbp().asLiveData().getOrAwaitValue()
        assertThat(allTables).contains(tableNbp)
    }

    @Test
    fun deleteTable() = runBlockingTest {
        val rate = Rate("euro", "EUR", 4.00, 5.00  )
        val tableNbp = TableNbp("table", "tb/11", "2021-12-12", "2012-12-14", listOf(rate))
        dao.insertTableNbp(listOf(tableNbp))
        dao.deleteTableNbp()
        val allTables = dao.getTableNbp().asLiveData().getOrAwaitValue()
        assertThat(allTables).isEmpty()
    }
}