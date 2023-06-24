package com.example.chatgbt.api

import com.example.chatgbt.models.ChatCompletionResponse
import com.example.chatgbt.models.ChatRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {

    @POST("completions")
    suspend fun sendRequestApi(@Body request: ChatRequest): ChatCompletionResponse
}