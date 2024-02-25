package com.namkuzo.geminipuerto

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse

class GeminiRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "HERE_YOUR_KEY"
    )

    suspend fun fetchDailyAdvice(language: String): GenerateContentResponse {
        return generativeModel.generateContent(getPrompt(language))
    }

    private fun getPrompt(language: String): String {
        return when (language) {
            "es" -> "dame un solo consejo del dia"
            else -> "give me just one piece of advice for the day"
        }
    }
}
