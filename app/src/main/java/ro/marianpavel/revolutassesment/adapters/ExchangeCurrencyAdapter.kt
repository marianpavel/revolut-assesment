package ro.marianpavel.revolutassesment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ro.marianpavel.revolutassesment.R
import ro.marianpavel.revolutassesment.models.ExchangeCurrency

class ExchangeCurrencyAdapter(private val dataSet: LiveData<ExchangeCurrency>) :
    RecyclerView.Adapter<ExchangeCurrencyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val flag: ImageView = view.findViewById(R.id.currency_flag)
        val code: TextView = view.findViewById(R.id.currency_code)
        val name: TextView = view.findViewById(R.id.currency_name)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_exchange_currency, viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.code.text = "RON"
        viewHolder.name.text = "Romanian Leu"
        viewHolder.flag.load(R.drawable.ic_romania) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount() = dataSet.value!!.rates.size
}