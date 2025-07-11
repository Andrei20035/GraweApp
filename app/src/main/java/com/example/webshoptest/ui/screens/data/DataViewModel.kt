package com.example.webshoptest.ui.screens.data

import androidx.lifecycle.ViewModel
import com.example.webshoptest.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

}