package ro.marianpavel.revolutassesment.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ro.marianpavel.revolutassesment.interfaces.RevolutAPI

object ApiClient {

    var BASE_URL: String = "https://hiring.revolut.codes/api/"

    fun apiClient(): RevolutAPI {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RevolutAPI::class.java)

    }
}