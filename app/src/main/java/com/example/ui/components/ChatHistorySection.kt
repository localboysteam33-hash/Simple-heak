package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.ui.theme.CardBackground
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun ChatHistorySection(messages: List<ChatMessage>) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .height(130.dp)
      .background(CardBackground, RoundedCornerShape(16.dp))
      .border(1.dp, NeonPurple.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
      .padding(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "💬 MUNIN_BHAI VIP Live Chat & Signal Log",
        color = NeonCyan,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Real-time",
        color = MutedText,
        fontSize = 10.sp
      )
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(top = 6.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(messages) { msg: ChatMessage ->
        val senderColor = if (msg.isVip) NeonCyan else NeonPink
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF10072E), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = msg.sender,
                color = senderColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = msg.time,
                color = MutedText,
                fontSize = 9.sp
              )
            }
            Text(
              text = msg.text,
              color = LightText,
              fontSize = 11.sp
            )
          }
        }
      }
    }
  }
}
