package ro.marianpavel.revolutassesment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener

class ExchangeCurrencyAdapter(
    private val focusListener: CurrencyFocusListener,
    private val onCurrencyChanged: (currency: String, newValue: Float) -> Unit
) : ListAdapter<CurrencyViewModel, CurrencyViewHolder>(ExchangeDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_exchange_currency, viewGroup, false),
            focusListener,
            onCurrencyChanged
        )
    }

    override fun onBindViewHolder(viewHolder: CurrencyViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item)
    }
}

private class ExchangeDiffCallback : DiffUtil.ItemCallback<CurrencyViewModel>() {
    override fun areItemsTheSame(
        oldItem: CurrencyViewModel,
        newItem: CurrencyViewModel
    ): Boolean {
        return oldItem.currencyCode == newItem.currencyCode
    }

    override fun areContentsTheSame(
        oldItem: CurrencyViewModel,
        newItem: CurrencyViewModel
    ): Boolean {
        return oldItem.rate == newItem.rate
    }

}
