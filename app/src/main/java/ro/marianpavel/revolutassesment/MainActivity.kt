package ro.marianpavel.revolutassesment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ro.marianpavel.revolutassesment.adapters.ExchangeCurrencyAdapter
import ro.marianpavel.revolutassesment.databinding.ActivityMainBinding
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener
import ro.marianpavel.revolutassesment.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CurrencyFocusListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var exchangeAdapter: ExchangeCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this)
        exchangeAdapter = ExchangeCurrencyAdapter(this, ::onCurrencyChanged, 1.0f)

        binding.currencyExchangeList.apply {
            layoutManager = viewManager
            adapter = exchangeAdapter
            itemAnimator = null
        }

        mainViewModel.exchangeCurrencyLiveData.observe(this, {
            if (!it.rates.isNullOrEmpty()) {
                exchangeAdapter.submitList(mainViewModel.moveCurrencyToTopIfAny(it.rates.toList().toMutableList()))
            }
        })
    }

    override fun onItemFocused(currency: String) {
        mainViewModel.firstCurrency = currency
        exchangeAdapter.submitList(
            mainViewModel.moveCurrencyToTopIfAny(
                mainViewModel.exchangeCurrencyLiveData.value!!.rates.toList().toMutableList()))
    }

    private fun onCurrencyChanged(currency: String, newValue: Float) {
        exchangeAdapter.multiplyFactor = mainViewModel.calculateMultiplyFactor(currency, newValue)
        exchangeAdapter.notifyItemRangeChanged(1, mainViewModel.exchangeCurrencyLiveData.value!!.rates.size)
    }
}