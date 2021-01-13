package ro.marianpavel.revolutassesment.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import ro.marianpavel.revolutassesment.adapters.CurrencyViewModel
import ro.marianpavel.revolutassesment.interfaces.RevolutAPI
import ro.marianpavel.revolutassesment.models.ExchangeCurrency

class MainViewModel @ViewModelInject constructor(
    private val client: RevolutAPI,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var isRequestPaused = false
    var firstCurrency: String? = null
    private var multiplyFactor: Float = 1f

    val _recyclerModels = MutableLiveData<List<CurrencyViewModel>>(emptyList())
    val recyclerModels: LiveData<List<CurrencyViewModel>> get() = _recyclerModels

    private val exchangeCurrency: LiveData<ExchangeCurrency> = liveData {
        while(true) {
            if (!isRequestPaused) {
                emit(client.getExchangeRates())
            }
            delay(timeMillis = 1000)
        }
    }

    init {
        exchangeCurrency.observeForever { ec ->
            _recyclerModels.value = moveCurrencyToTopIfAny(ec.rates.toList())
        }
    }

    private fun calculateMultiplyFactor(currency: String, newValue: Float): Float {
        return newValue / (exchangeCurrency.value?.rates?.get(currency) ?: error("Value is null"))
    }

    private fun moveCurrencyToTopIfAny(list: List<Pair<String, Float>>): List<CurrencyViewModel> {
        val models = list.mapTo(ArrayList(list.size)) { CurrencyViewModel(it.first, it.second, multiplyFactor) }
        firstCurrency?.let { currency ->
            val item = models.find {
                it.currencyCode == currency
            }
            models.remove(item)
            item?.let { models.add(0, it) }
        }
        return models
    }

    fun onItemFocused(currency: String) {
        firstCurrency = currency
        _recyclerModels.value = moveCurrencyToTopIfAny(exchangeCurrency.value!!.rates.toList())
    }

    fun onCurrencyChanged(currency: String, newValue: Float) {
        multiplyFactor = calculateMultiplyFactor(currency, newValue)
        _recyclerModels.value = moveCurrencyToTopIfAny(exchangeCurrency.value!!.rates.toList())
    }
}