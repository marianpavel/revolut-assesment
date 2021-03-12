package ro.marianpavel.revolutassesment.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ro.marianpavel.revolutassesment.adapters.CurrencyViewModel
import ro.marianpavel.revolutassesment.di.BaseCurrencyConvertor
import ro.marianpavel.revolutassesment.interfaces.ExchangeAPI
import ro.marianpavel.revolutassesment.models.ExchangeCurrency
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: ExchangeAPI,
    private val baseCurrencyConvertor: BaseCurrencyConvertor
): ViewModel() {

    var isRequestPaused = false
    private val firstCurrency = MutableLiveData<String?>(null)
    private val multiplyFactor = MutableLiveData(1f)
    private val exchangeCurrency: LiveData<ExchangeCurrency?> = liveData {
        emit(null)
        while(true) {
            if (!isRequestPaused) {
                emit(client.getExchangeRates())
            }
            delay(timeMillis = 1000)
        }
    }

    val recyclerModels: LiveData<List<CurrencyViewModel>> = combineTuple(exchangeCurrency, firstCurrency, multiplyFactor)
        .map { (ec, firstCurrency, multiplyFactor) ->
            if (ec == null) {
                return@map emptyList()
            }

            val models = ec.rates.mapTo(ArrayList(ec.rates.size)) {
                CurrencyViewModel(it.key, (it.value * multiplyFactor!!).toString())
            }

            firstCurrency?.let { currency ->
                val item = models.find {
                    it.currencyCode == currency
                }
                models.remove(item)
                item?.let { models.add(0, it) }
            }

            return@map models
        }

    fun onItemFocused(currency: String) {
        firstCurrency.value = currency
    }

    fun onCurrencyChanged(currency: String, newValue: Float) {
        multiplyFactor.value = calculateMultiplyFactor(currency, newValue)
    }

    private fun calculateMultiplyFactor(currency: String, newValue: Float): Float {
        return exchangeCurrency.value?.let { baseCurrencyConvertor.convertFromBaseCurrency(currency, newValue, it.rates) }!!
    }
}

/**
 * Copied from https://github.com/Zhuinden/livedata-combinetuple-kt
 */
fun <T1, T2, T3> combineTuple(f1: LiveData<T1>, f2: LiveData<T2>, f3: LiveData<T3>): LiveData<Triple<T1?, T2?, T3?>> = MediatorLiveData<Triple<T1?, T2?, T3?>>().also { mediator ->
    mediator.value = Triple(f1.value, f2.value, f3.value)

    mediator.addSource(f1) { t1: T1? ->
        val (_, t2, t3) = mediator.value!!
        mediator.value = Triple(t1, t2, t3)
    }

    mediator.addSource(f2) { t2: T2? ->
        val (t1, _, t3) = mediator.value!!
        mediator.value = Triple(t1, t2, t3)
    }

    mediator.addSource(f3) { t3: T3? ->
        val (t1, t2, _) = mediator.value!!
        mediator.value = Triple(t1, t2, t3)
    }
}
