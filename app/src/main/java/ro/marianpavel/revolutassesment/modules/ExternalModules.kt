package ro.marianpavel.revolutassesment.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ro.marianpavel.revolutassesment.interfaces.RevolutAPI
import ro.marianpavel.revolutassesment.networking.ApiClient

@Module
@InstallIn(ApplicationComponent::class)
class ExternalModules {

    @Provides
    fun provideRevolutAPI(): RevolutAPI {
        return ApiClient.apiClient()
    }
}