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

    fun fetchDailyAdvice(language: String) {
        _dailyAdvice.value = ApiResponseState.Loading()
        viewModelScope.launch {
            try {
                val response = geminiRepository.fetchDailyAdvice(language)
                _dailyAdvice.value = ApiResponseState.Success(response.text.orEmpty())
                Log.i("GEMINI", response.text.toString())
            } catch (e: Exception) {
                Log.e("GEMINI", e.message.orEmpty())
                _dailyAdvice.value = ApiResponseState.Error(e.message.orEmpty())
            }
        }
    }
}

sealed class ApiResponseState<T> {
    data class Success<T>(val data: T) : ApiResponseState<T>()
    data class Error<T>(val error: T) : ApiResponseState<T>()
    class Loading<T> : ApiResponseState<T>()
}
