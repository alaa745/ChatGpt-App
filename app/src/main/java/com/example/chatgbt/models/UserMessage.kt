package com.example.chatgbt.models

data class UserMessage(
    var message: String,
    var isUser: Boolean = true
)
