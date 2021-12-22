package com.thedroidsonroids.cashconverter.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.data.model.TableNbp
import com.thedroidsonroids.cashconverter.data.repository.TableRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingFragmentVM @Inject constructor(
     val repositoryImpl: TableRepositoryImpl
): ViewModel() {

    private val _tableNbp = MutableStateFlow<TableEvent>(TableEvent.Empty)
    val tableNbp: StateFlow<TableEvent> = _tableNbp

    val tables = repositoryImpl.getTableNbp().asLiveData()

    fun getTableC(){
        viewModelScope.launch(Dispatchers.IO) {
            _tableNbp.value = TableEvent.Loading
            when(val response = repositoryImpl.getTableC()){
                is Resource.Error -> {_tableNbp.value = TableEvent.Failure(response.error!!)}
                is Resource.Success ->{
                    val currency = response.data!!
                    _tableNbp.value = TableEvent.Success(currency)
                }
                is Resource.Loading ->{_tableNbp.value = TableEvent.Loading}
            }
        }
    }


    sealed class TableEvent {
        class Success(val data: TableNbp) : TableEvent()
        class Failure(val errorText: String) : TableEvent()
        object Loading : TableEvent()
        object Empty : TableEvent()
    }
}