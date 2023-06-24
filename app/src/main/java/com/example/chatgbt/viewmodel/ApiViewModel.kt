package com.example.chatgbt.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgbt.models.ChatCompletionResponse
import com.example.chatgbt.models.ChatRequest
import com.example.chatgbt.models.Message
import com.example.chatgbt.models.UserMessage
import com.example.chatgbt.repository.OpenAiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val repository: OpenAiRepository , val chatRequest: ChatRequest)
    : ViewModel(){

    private val _chatResponse = MutableLiveData<ChatCompletionResponse>()
    val chatResponse: LiveData<ChatCompletionResponse> get() = _chatResponse

    val messageList = mutableStateListOf<UserMessage>()
    var responseMessages = mutableStateListOf<UserMessage>()

    var isLoading = MutableLiveData(false)


    fun addMessage(userMessage: UserMessage) {
        messageList.add(userMessage)
    }

    fun addResponse(userMessage: UserMessage) {
        responseMessages.add(userMessage)
    }

    fun getChatRequest(userMessageContent: String): ChatRequest {
        return chatRequest.copy(
            messages = listOf(Message(role = "user", content = userMessageContent))
        )
    }

    fun changeLoading(newLoad: Boolean){
        isLoading.value = newLoad
    }

    fun sendApiRequest(chatRequest: ChatRequest){
        viewModelScope.launch {
            try {
                val response = repository.sendRequestApi(chatRequest)
                _chatResponse.value = response
                isLoading.value = true
                addMessage(
                    UserMessage(
                        message = response.choices[0].message.content.replace("," , ""),
                        isUser = false
                    )
                )
                Log.d("chatview" , response.choices[0].message.content)
//                isLoading.value = false
            }
            catch (e: Exception){
                Log.d("ApiError: " , e.toString())
            }
        }
    }
}