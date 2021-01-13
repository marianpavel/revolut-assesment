package ro.marianpavel.revolutassesment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ro.marianpavel.revolutassesment.adapters.ExchangeCurrencyAdapter
import ro.marianpavel.revolutassesment.databinding.ActivityMainBinding
import ro.marianpavel.revolutassesment.interfaces.CurrencyFocusListener
import ro.marianpavel.revolutassesment.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CurrencyFocusListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var exchangeAdapter: ExchangeCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exchangeAdapter = ExchangeCurrencyAdapter(this, ::onCurrencyChanged, 1.0f)

        binding.currencyExchangeList.apply {
            adapter = exchangeAdapter
            itemAnimator = null
        }

        mainViewModel.exchangeCurrency.observe(this, {
            if (!it.rates.isNullOrEmpty()) {
                exchangeAdapter.submitList(mainViewModel.moveCurrencyToTopIfAny(it.rates.toList().toMutableList()))
            }
        })
    }

    override fun onItemFocused(currency: String) {
        mainViewModel.firstCurrency = currency
        exchangeAdapter.submitList(
            mainViewModel.moveCurrencyToTopIfAny(
                mainViewModel.exchangeCurrency.value!!.rates.toList().toMutableList()))
    }

    private fun onCurrencyChanged(currency: String, newValue: Float) {
        exchangeAdapter.multiplyFactor = mainViewModel.calculateMultiplyFactor(currency, newValue)
        exchangeAdapter.notifyItemRangeChanged(1, mainViewModel.exchangeCurrency.value!!.rates.size)
    }
}