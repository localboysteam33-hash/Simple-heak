package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TerminalLogOverlay() {
  val logPool = listOf(
    "[SYS_INIT] Loading amaeclub.com core modules...",
    "[BYPASS] Intercepting SSL handshake on Aviator socket...",
    "[DECRYPT] Key MUMIN3231 verified. VIP neural link established.",
    "[SIGNAL] High multiplier probability detected: 4.85x",
    "[HACK] Injecting packet #889 into server websocket...",
    "[STATUS] HEAK security firewall bypassed successfully.",
    "[AI_CORE] Neural prediction model synchronizing...",
    "[SUCCESS] Secure tunnel active on amaeclub.com.",
    "[SCAN] Monitoring round frequency and RNG sequence..."
  )

  var logs by remember {
    mutableStateOf(
      listOf(
        "[INIT] HEAK Terminal v2.4 booted",
        "[SYS] Connected to amaeclub.com"
      )
    )
  }

  LaunchedEffect(Unit) {
    while (true) {
      delay(3000L)
      val nextLog = logPool.random()
      logs = buildList {
        add(nextLog)
        addAll(logs.take(3))
      }
    }
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(65.dp)
      .background(Color(0xFF020904).copy(alpha = 0.85f), RoundedCornerShape(10.dp))
      .border(1.dp, Color(0xFF00EFBD).copy(alpha = 0.5f), RoundedCornerShape(10.dp))
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Column {
      Text(
        text = "TERMINAL LOGS [AMAEC CLUB]",
        color = Color(0xFF00EFBD),
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )
      logs.forEach { logLine ->
        Text(
          text = "> $logLine",
          color = Color(0xFF00FF88).copy(alpha = 0.8f),
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          maxLines = 1
        )
      }
    }
  }
}
