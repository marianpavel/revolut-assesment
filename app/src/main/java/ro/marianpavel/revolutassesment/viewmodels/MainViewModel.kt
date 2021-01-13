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

    val exchangeCurrency: LiveData<ExchangeCurrency> = liveData {
        while(true) {
            if (!isRequestPaused) {
                emit(client.getExchangeRates())
            }
            delay(timeMillis = 1000)
        }
    }

    fun calculateMultiplyFactor(currency: String, newValue: Float): Float {
        return newValue / (exchangeCurrency.value?.rates?.get(currency) ?: error("Value is null"))
    }

    fun moveCurrencyToTopIfAny(list: List<Pair<String, Float>>): List<CurrencyViewModel> {
        val models = list.mapTo(ArrayList(list.size)) { CurrencyViewModel(it.first, it.second) }
        firstCurrency?.let { currency ->
            val item = models.find {
                it.currencyCode == currency
            }
            models.remove(item)
            item?.let { models.add(0, it) }
        }
        return models
    }
}