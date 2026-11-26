package com.example.data

data class ChatMessage(
  val id: String,
  val sender: String,
  val text: String,
  val time: String,
  val isVip: Boolean = false
)
