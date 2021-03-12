package ro.marianpavel.revolutassesment.di

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface BaseCurrencyConvertor {

    fun convertFromBaseCurrency(currency: String, amount: Float, currencyMap: Map<String, Float>): Float
}

@ViewModelScoped
class BaseCurrencyConvertorImpl @Inject constructor() : BaseCurrencyConvertor {

    override fun convertFromBaseCurrency(
        currency: String,
        amount: Float,
        currencyMap: Map<String, Float>
    ): Float {
        return amount / (currencyMap[currency] ?: error("Value is null"))
    }

}