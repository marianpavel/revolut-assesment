package ro.marianpavel.revolutassesment.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import ro.marianpavel.revolutassesment.interfaces.RevolutAPI
import ro.marianpavel.revolutassesment.models.ExchangeCurrency

class MainViewModel @ViewModelInject constructor(
    private val client: RevolutAPI,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var isRequestPaused = false
    var firstCurrency: String? = null

    val exchangeCurrencyLiveData: LiveData<ExchangeCurrency> = liveData {
        while(true) {
            if (!isRequestPaused) {
                emit(client.getExchangeRates())
            }
            delay(timeMillis = 1000)
        }
    }

    fun calculateMultiplyFactor(currency: String, newValue: Float): Float {
        return newValue / (exchangeCurrencyLiveData.value?.rates?.get(currency) ?: error("Value is null"))
    }

    fun moveCurrencyToTopIfAny(list: MutableList<Pair<String, Float>>): MutableList<Pair<String, Float>> {
        firstCurrency?.let { currency ->
            val item = list.find {
                it.first == currency
            }
            list.remove(item)
            item?.let { list.add(0, it) }
        }
        return list
    }
}