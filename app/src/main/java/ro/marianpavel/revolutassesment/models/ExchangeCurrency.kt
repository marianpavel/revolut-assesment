package ro.marianpavel.revolutassesment.models

import com.squareup.moshi.Json

class ExchangeCurrency {

    @Json(name = "baseCurrency")
    lateinit var baseCurrency: String

    @Json(name = "rates")
    lateinit var rates: Map<String, Float>
}