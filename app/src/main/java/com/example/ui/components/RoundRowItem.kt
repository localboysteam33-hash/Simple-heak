package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RoundItem
import com.example.ui.theme.CardBackground
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink

@Composable
fun RoundRowItem(round: RoundItem) {
  val accentColor = if (round.isGreen) NeonCyan else NeonPink

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(CardBackground, RoundedCornerShape(16.dp))
      .border(1.5.dp, accentColor.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Box(
        modifier = Modifier
          .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = round.id,
          color = accentColor,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Text(
        text = "Time: ${round.timestamp}",
        color = MutedText,
        fontSize = 12.sp
      )
    }

    Box(
      modifier = Modifier
        .background(accentColor.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
        .border(1.dp, accentColor, RoundedCornerShape(20.dp))
        .padding(horizontal = 14.dp, vertical = 6.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "${round.multiplier}x",
        color = accentColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.Black
      )
    }
  }
}
