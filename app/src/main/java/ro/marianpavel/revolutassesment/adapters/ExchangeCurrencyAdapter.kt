package ro.marianpavel.revolutassesment.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.enums.CurrencyNameFlag
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener

class ExchangeCurrencyAdapter(private val listener: CurrencyFocusListener, var multiplyFactor: Float) :
    ListAdapter<Pair<String, Float>, ExchangeCurrencyAdapter.ViewHolder>(ExchangeDiffCallback()) {

    class ViewHolder(view: View, listener: CurrencyFocusListener) : RecyclerView.ViewHolder(view) {

        val container: ConstraintLayout = view.findViewById(R.id.container)
        val flag: ImageView = view.findViewById(R.id.currency_flag)
        val code: TextView = view.findViewById(R.id.currency_code)
        val name: TextView = view.findViewById(R.id.currency_name)
        val currencyValue: EditText = view.findViewById(R.id.currency_value)

        init {
            currencyValue.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    listener.onItemFocused(code.text.toString())
                }
            }

            currencyValue.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (currencyValue.isFocused) {
                        if (s.toString().isNotEmpty()) {
                            listener.onCurrencyChanged(code.text.toString(), s.toString().toFloat())
                        } else {
                            currencyValue.setText("0")
                        }
                    }
                }

            })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_exchange_currency, viewGroup, false), listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = getItem(position)
        val rate = item.second
        val currencyNameFlag = CurrencyNameFlag.valueOf(item.first)

        viewHolder.code.text = currencyNameFlag.name
        viewHolder.name.text = currencyNameFlag.currency

        if (!viewHolder.currencyValue.isFocused) {
            viewHolder.currencyValue.setText((rate * multiplyFactor).toString())
        }

        viewHolder.flag.load(currencyNameFlag.icon) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
            transformations(CircleCropTransformation())
        }

        viewHolder.container.setOnClickListener {
            val newList = mutableListOf<Pair<String, Float>>()
            newList.addAll(currentList)
            newList.removeAt(position)
            newList.add(0, item)

            onCurrentListChanged(currentList, newList)
            notifyItemMoved(position, 0)
            notifyItemRangeChanged(0, itemCount)
        }
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
