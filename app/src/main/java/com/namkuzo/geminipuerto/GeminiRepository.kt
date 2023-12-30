package com.namkuzo.geminipuerto

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse

class GeminiRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "HERE_YOUR_KEY"
    )

    suspend fun fetchDailyAdvice(): GenerateContentResponse {
        val prompt = "dame un solo consejo del dia"
        return generativeModel.generateContent(prompt)
    }
}
