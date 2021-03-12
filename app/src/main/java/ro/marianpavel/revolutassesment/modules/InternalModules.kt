package ro.marianpavel.revolutassesment.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.marianpavel.revolutassesment.di.BaseCurrencyConvertor
import ro.marianpavel.revolutassesment.di.BaseCurrencyConvertorImpl

@Module
@InstallIn(ViewModelComponent::class)
interface InternalModules {

    @Binds
    @ViewModelScoped
    fun bindBaseCurrencyConvertorFactory(factory: BaseCurrencyConvertorImpl): BaseCurrencyConvertor
}