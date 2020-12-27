package ro.marianpavel.revolutassesment.interfaces

import retrofit2.Call
import retrofit2.http.GET
import ro.marianpavel.revolutassesment.models.ExchangeCurrency

interface RevolutAPI {

    @GET("android/latest?base=EUR")
    suspend fun getExchangeRates(): ExchangeCurrency
}