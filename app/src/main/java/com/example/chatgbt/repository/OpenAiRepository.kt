package com.example.chatgbt.repository

import com.example.chatgbt.api.OpenAiApi
import com.example.chatgbt.models.ChatCompletionResponse
import com.example.chatgbt.models.ChatRequest
import javax.inject.Inject

class OpenAiRepository @Inject constructor(private val openAiApi: OpenAiApi){
    suspend fun sendRequestApi(chatRequest: ChatRequest): ChatCompletionResponse{
        return openAiApi.sendRequestApi(chatRequest)
    }
}