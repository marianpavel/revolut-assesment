package ro.marianpavel.revolutassesment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ro.marianpavel.revolutassesment.adapters.ExchangeCurrencyAdapter
import ro.marianpavel.revolutassesment.databinding.ActivityMainBinding
import ro.marianpavel.revolutassesment.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var exchangeAdapter: ExchangeCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this)
        mainViewModel.exchangeCurrencyLiveData.observe(this, {
            Log.i("TEST", "TEST")

            exchangeAdapter = ExchangeCurrencyAdapter(mainViewModel.exchangeCurrencyLiveData)

            binding.currencyExchangeList.apply {
                layoutManager = viewManager
                adapter = exchangeAdapter
            }
        })
    }
}