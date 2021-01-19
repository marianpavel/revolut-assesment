package ro.marianpavel.revolutassesment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ro.marianpavel.revolutassesment.adapters.ExchangeCurrencyAdapter
import ro.marianpavel.revolutassesment.databinding.ActivityMainBinding
import ro.marianpavel.revolutassesment.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var exchangeAdapter: ExchangeCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exchangeAdapter = ExchangeCurrencyAdapter(mainViewModel::onItemFocused, mainViewModel::onCurrencyChanged)

        binding.currencyExchangeList.apply {
            adapter = exchangeAdapter
            itemAnimator = null
        }

        mainViewModel.recyclerModels.observe(this, exchangeAdapter::submitList)
    }
}