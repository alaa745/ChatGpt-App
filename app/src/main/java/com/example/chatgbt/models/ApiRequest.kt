package com.example.chatgbt.models

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)

