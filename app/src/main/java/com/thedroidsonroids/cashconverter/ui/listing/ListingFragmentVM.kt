package com.thedroidsonroids.cashconverter.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.data.model.Table
import com.thedroidsonroids.cashconverter.data.repository.TableRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingFragmentVM @Inject constructor(
    private val repositoryImpl: TableRepositoryImpl
): ViewModel() {

    private val _table = MutableStateFlow<TableEvent>(TableEvent.Empty)
    val table: StateFlow<TableEvent> = _table

    fun getTableC(){
        viewModelScope.launch(Dispatchers.IO) {
            _table.value = TableEvent.Loading
            when(val response = repositoryImpl.getTableC()){
                is Resource.Error -> {_table.value = TableEvent.Failure(response.error!!)}
                is Resource.Success ->{
                    val currency = response.data!!
                    _table.value = TableEvent.Success(currency)
                }
                is Resource.Loading ->{_table.value = TableEvent.Loading}
            }
        }
    }


    sealed class TableEvent {
        class Success(val data: Table) : TableEvent()
        class Failure(val errorText: String) : TableEvent()
        object Loading : TableEvent()
        object Empty : TableEvent()
    }
}