package com.jones.test_timer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _counterValue = MutableStateFlow(0)
    val counterValue: StateFlow<Int> = _counterValue

    fun startTimer(seconds: Int) {
        _counterValue.value = seconds
    }
}