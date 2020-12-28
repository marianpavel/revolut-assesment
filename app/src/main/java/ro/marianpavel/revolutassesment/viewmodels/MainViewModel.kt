package ro.marianpavel.revolutassesment.viewmodels

import android.util.Log
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

    val exchangeCurrencyLiveData: LiveData<ExchangeCurrency> = liveData {
        while(true) {
            emit(client.getExchangeRates())
            delay(timeMillis = 1000)
        }
    }

}