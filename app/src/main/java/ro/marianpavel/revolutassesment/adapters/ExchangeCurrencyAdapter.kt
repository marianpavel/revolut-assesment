package ro.marianpavel.revolutassesment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ro.marianpavel.revolutassesment.R

class ExchangeCurrencyAdapter(
    private val onItemFocused: (String) -> Unit,
    private val onCurrencyChanged: (currency: String, newValue: Float) -> Unit
) : ListAdapter<CurrencyViewModel, CurrencyViewHolder>(ExchangeDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_exchange_currency, viewGroup, false),
            onItemFocused,
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
        // TODO: Floats should not be tested for equality!
        //       Replace all usages of Float by BigDecimal and compare by
        //       decimal1.compareTo(decimal2) == 0 (equal mathematical value)
        //                                    <  0 (decimal1 smaller)
        //                                    >  0 (decimal1 bigger)
        //       Do not use == to test BigDecimals for equality!
        return oldItem.amount == newItem.amount
    }

}
