package ro.marianpavel.revolutassesment.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.marianpavel.revolutassesment.interfaces.ExchangeAPI
import ro.marianpavel.revolutassesment.networking.ApiClient

@Module
@InstallIn(ViewModelComponent::class)
class ExternalModules {

    @Provides
    @ViewModelScoped
    fun provideExchangeAPI(): ExchangeAPI {
        return ApiClient.apiClient()
    }
}