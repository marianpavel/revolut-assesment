package ro.marianpavel.revolutassesment.interfaces

interface CurrencyFocusListener {
    fun onItemFocused(currency: String)
    fun onCurrencyChanged(currency: String, newValue: Float)
}