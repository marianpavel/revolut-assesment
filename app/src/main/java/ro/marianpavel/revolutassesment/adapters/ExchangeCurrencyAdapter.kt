package ro.marianpavel.revolutassesment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener

class ExchangeCurrencyAdapter(
    private val focusListener: CurrencyFocusListener,
    private val onCurrencyChanged: (currency: String, newValue: Float) -> Unit,
    var multiplyFactor: Float
) : ListAdapter<Pair<String, Float>, CurrencyViewHolder>(ExchangeDiffCallback()) {

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
        viewHolder.bind(item, multiplyFactor)
    }
}

private class ExchangeDiffCallback : DiffUtil.ItemCallback<Pair<String, Float>>() {
    override fun areItemsTheSame(
        oldItem: Pair<String, Float>,
        newItem: Pair<String, Float>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<String, Float>,
        newItem: Pair<String, Float>
    ): Boolean {
        return oldItem.second == newItem.second
    }

}
