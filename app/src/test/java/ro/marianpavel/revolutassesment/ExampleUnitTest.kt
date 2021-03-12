package ro.marianpavel.revolutassesment

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import ro.marianpavel.revolutassesment.di.BaseCurrencyConvertor
import ro.marianpavel.revolutassesment.di.BaseCurrencyConvertorImpl
import ro.marianpavel.revolutassesment.interfaces.ExchangeAPI
import ro.marianpavel.revolutassesment.models.ExchangeCurrency
import ro.marianpavel.revolutassesment.viewmodels.MainViewModel

@RunWith(AndroidJUnit4::class)
class CurrencyUnitTest: TestCase() {

    private lateinit var baseCurrencyConvertor: BaseCurrencyConvertor
    private lateinit var mainViewModel: MainViewModel

    @Before
    public override fun setUp() {
        val client = mockk<ExchangeAPI>()
        baseCurrencyConvertor = mockk<BaseCurrencyConvertor>()
        mainViewModel = MainViewModel(client, baseCurrencyConvertor)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `check exchange currency model`() {
        val exchangeCurrency = ExchangeCurrency("EUR", mapOf(Pair("RON", 5.4f)))
        assertEquals("EUR", exchangeCurrency.baseCurrency)
        assertEquals(1, exchangeCurrency.rates.size)
    }

    @Test
    fun `convert 1 euro to ron test`() {
        val currencyMap = mapOf(Pair("RON", 4.8f))
        val converted = baseCurrencyConvertor.convertFromBaseCurrency("RON", 1f, currencyMap)
        assertEquals(4.8f, converted)
    }
}