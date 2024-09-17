package com.samirharicha.myapplication

data class Message(
    val senderId: String = "",
    val reciverId:String ="",
    val message: String = "",
    val timestamp: Long = 0
)