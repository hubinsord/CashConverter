package com.thedroidsonroids.cashconverter.ui.currencydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thedroidsonroids.cashconverter.data.model.Rate

class DetailVM: ViewModel() {

    private val _viewData = MutableLiveData<Rate>()
    val viewData: LiveData<Rate> get() = _viewData

    fun loadData(rate: Rate){
        _viewData.postValue(rate)
    }

}