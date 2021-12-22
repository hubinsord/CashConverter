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
class ListingFragmentVM @Inject constructor(private val repositoryImpl: TableRepositoryImpl) : ViewModel() {

    val tables = repositoryImpl.getTableNbp().asLiveData()
}