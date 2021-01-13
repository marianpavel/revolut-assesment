package ro.marianpavel.revolutassesment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.enums.CurrencyNameFlag
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener

class ExchangeCurrencyAdapter(
    private val focusListener: CurrencyFocusListener,
    private val onCurrencyChanged: (currency: String, newValue: Float) -> Unit,
    var multiplyFactor: Float
) : ListAdapter<Pair<String, Float>, ExchangeCurrencyAdapter.ViewHolder>(ExchangeDiffCallback()) {

    class ViewHolder(
        view: View,
        focusListener: CurrencyFocusListener,
        onCurrencyChanged: (String, Float) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        val container: ConstraintLayout = view.findViewById(R.id.container)
        private val flag: ImageView = view.findViewById(R.id.currency_flag)
        private val code: TextView = view.findViewById(R.id.currency_code)
        private val name: TextView = view.findViewById(R.id.currency_name)
        private val currencyValue: EditText = view.findViewById(R.id.currency_value)

        init {
            currencyValue.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    focusListener.onItemFocused(code.text.toString())
                }
            }

            currencyValue.doAfterTextChanged { input ->
                if (currencyValue.isFocused) {
                    if (input?.isNotEmpty() == true) {
                        onCurrencyChanged(code.text.toString(), input.toString().toFloat())
                    } else {
                        currencyValue.setText("0")
                    }
                }
            }
        }

        fun bind(model: Pair<String, Float>, multiplyFactor: Float) {
            val rate = model.second
            val currencyNameFlag = CurrencyNameFlag.valueOf(model.first)

            code.text = currencyNameFlag.name
            name.text = currencyNameFlag.currency

            if (!currencyValue.isFocused) {
                currencyValue.setText((rate * multiplyFactor).toString())
            }

            flag.load(currencyNameFlag.icon) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_exchange_currency, viewGroup, false),
            focusListener,
            onCurrencyChanged
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
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
