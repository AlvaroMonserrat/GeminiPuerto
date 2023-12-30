package com.namkuzo.geminipuerto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GeminiViewModel : ViewModel() {

    private val geminiRepository: GeminiRepository = GeminiRepository()

    private val _dailyAdvice = MutableStateFlow<ApiResponseState<String>?>(null)
    val dailyAdvice: StateFlow<ApiResponseState<String>?> = _dailyAdvice

    fun fetchDailyAdvice() {
        _dailyAdvice.value = ApiResponseState.Loading()
        viewModelScope.launch {
            Log.i("GEMINI", "CALL")
            try {
                val response = geminiRepository.fetchDailyAdvice()
                _dailyAdvice.value = ApiResponseState.Success(response.text.orEmpty())
                Log.i("GEMINI", response.text.toString())
            } catch (e: Exception) {
                _dailyAdvice.value = ApiResponseState.Success("")
            }
        }
    }
}

sealed class ApiResponseState<T> {
    data class Success<T>(val data: T) : ApiResponseState<T>()
    class Loading<T> : ApiResponseState<T>()
}
