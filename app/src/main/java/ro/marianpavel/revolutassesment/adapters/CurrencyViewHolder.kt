package ro.marianpavel.revolutassesment.adapters

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.enums.CurrencyNameFlag

class CurrencyViewHolder(
    view: View,
    onItemFocused: (String) -> Unit,
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
                onItemFocused(code.text.toString())
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

    fun bind(model: CurrencyViewModel) {
        val currencyNameFlag = CurrencyNameFlag.valueOf(model.currencyCode)

        code.text = currencyNameFlag.name
        name.text = currencyNameFlag.currency

        if (!currencyValue.isFocused) {
            currencyValue.setText(model.amount)
        }

        flag.load(currencyNameFlag.icon) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
            transformations(CircleCropTransformation())
        }
    }
}
