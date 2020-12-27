package ro.marianpavel.revolutassesment.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ro.marianpavel.revolutassesment.interfaces.RevolutAPI

class MainViewModel @ViewModelInject constructor(
    private val client: RevolutAPI,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    
}