package com.thedroidsonroids.cashconverter.ui.currencydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thedroidsonroids.cashconverter.data.model.Rate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.round

class DetailVM : ViewModel() {

    sealed class CurrencyEvent {
        object Success: CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _rate = MutableLiveData<Rate>()
    val rate: LiveData<Rate> get() = _rate

    private val _fromPln = MutableLiveData<String>("0")
    val fromPln get() = _fromPln

    private val _toForeignCurrency = MutableLiveData<Double>(0.0)
    val toForeignCurrency get() = _toForeignCurrency

    private val _toPln = MutableLiveData<Double>(0.0)
    val toPln get() = _toPln

    private val _fromForeignCurrency = MutableLiveData<String>("0")
    val fromForeignCurrency get() = _fromForeignCurrency

    fun loadData(rate: Rate) {
        _rate.postValue(rate)
    }

    fun btnConvertFromPlnClicked() {
        _conversion.value = CurrencyEvent.Loading
        val value = fromPln.value?.toDoubleOrNull()
        val askValue = rate.value!!.ask
        if (value != null){
            val convertedCurrency = round(value * askValue * 10000)/10000
            _toForeignCurrency.postValue(convertedCurrency)
            _conversion.value = CurrencyEvent.Success
        } else{
            _conversion.value = CurrencyEvent.Failure("Invalid input")
        }
    }

    fun btnConvertToPlnClicked() {
        _conversion.value = CurrencyEvent.Loading
        val value = fromForeignCurrency.value?.toDoubleOrNull()
        val bidValue = rate.value!!.bid
        if (value != null){
            val convertedCurrency = round(value * bidValue * 10000)/10000
            _toPln.postValue(convertedCurrency)
            _conversion.value = CurrencyEvent.Success
        } else {
            _conversion.value = CurrencyEvent.Failure("Invalid input")
        }
    }
}