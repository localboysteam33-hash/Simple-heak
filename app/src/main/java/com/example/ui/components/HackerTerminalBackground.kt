package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class TerminalDrop(
  val x: Float,
  val y: Float,
  val speed: Float,
  val color: Color
)

@Composable
fun HackerTerminalBackground() {
  val infiniteTransition = rememberInfiniteTransition(label = "terminal_bg")

  val animTick by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2000f,
    animationSpec = infiniteRepeatable(
      animation = tween(15000, easing = LinearEasing)
    ),
    label = "terminal_tick"
  )

  val drops = remember {
    List(50) {
      TerminalDrop(
        x = Random.nextFloat(),
        y = Random.nextFloat(),
        speed = Random.nextFloat() * 1.8f + 0.5f,
        color = listOf(
          Color(0xFF00EFBD), // Hacker Green/Cyan
          Color(0xFF168CFF), // Neon Blue
          Color(0xFFFF16B9), // Neon Pink
          Color(0xFFA35BFF)  // Neon Purple
        ).random()
      )
    }
  }

  Canvas(modifier = Modifier.fillMaxSize()) {
    val width = size.width
    val height = size.height

    // Deep Hacker Terminal Dark Background
    drawRect(
      brush = Brush.verticalGradient(
        colors = listOf(
          Color(0xFF070314),
          Color(0xFF030108),
          Color(0xFF000000)
        )
      )
    )

    // Draw Falling Hacker Terminal Code Dots & Lines
    drops.forEach { drop ->
      val currentY = ((drop.y * height + (animTick * drop.speed * 1.5f)) % height)
      drawCircle(
        color = drop.color.copy(alpha = 0.5f),
        radius = 2f,
        center = Offset(drop.x * width, currentY)
      )

      drawLine(
        color = drop.color.copy(alpha = 0.2f),
        start = Offset(drop.x * width, currentY - 25f),
        end = Offset(drop.x * width, currentY),
        strokeWidth = 1.5f
      )
    }

    // Scanline grid effect overlay
    val lineSpacing = 20f
    var yPos = 0f
    while (yPos < height) {
      drawLine(
        color = Color(0xFF00FFCC).copy(alpha = 0.03f),
        start = Offset(0f, yPos),
        end = Offset(width, yPos),
        strokeWidth = 1f
      )
      yPos += lineSpacing
    }
  }
}
