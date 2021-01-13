package ro.marianpavel.revolutassesment.models

import com.squareup.moshi.Json

data class ExchangeCurrency(
    @Json(name = "baseCurrency")
    val baseCurrency: String,
    @Json(name = "rates")
    var rates: Map<String, Float> = emptyMap()
)
