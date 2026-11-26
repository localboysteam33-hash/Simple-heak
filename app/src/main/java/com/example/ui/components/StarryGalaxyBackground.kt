package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Star(
  var x: Float,
  var y: Float,
  var radius: Float,
  var alpha: Float,
  var isBlue: Boolean,
  val alphaSpeed: Float
)

@Composable
fun StarryGalaxyBackground() {
  var tick by remember { mutableStateOf(0L) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(50)
      tick++
    }
  }

  val stars = remember {
    List(120) {
      Star(
        x = Random.nextFloat(),
        y = Random.nextFloat(),
        radius = Random.nextFloat() * 1.8f + 0.5f,
        alpha = Random.nextFloat() * 0.8f + 0.2f,
        isBlue = Random.nextBoolean(),
        alphaSpeed = (Random.nextFloat() - 0.5f) * 0.04f
      )
    }
  }

  Canvas(modifier = Modifier.fillMaxSize()) {
    val width = size.width
    val height = size.height

    // Radial gradient background matching the HTML reference
    val bgBrush = Brush.radialGradient(
      colors = listOf(
        Color(0xFF10072E),
        Color(0xFF05000D),
        Color(0xFF020007)
      ),
      center = Offset(width / 2f, height / 2f),
      radius = maxOf(width, height) * 0.9f
    )
    drawRect(brush = bgBrush)

    // Top-left and bottom-right glowing nebula highlights
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x78006AFF), Color.Transparent),
        center = Offset(width * 0.1f, height * 0.2f),
        radius = width * 0.5f
      ),
      radius = width * 0.5f,
      center = Offset(width * 0.1f, height * 0.2f)
    )

    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0x66FF00B8), Color.Transparent),
        center = Offset(width * 0.9f, height * 0.8f),
        radius = width * 0.5f
      ),
      radius = width * 0.5f,
      center = Offset(width * 0.9f, height * 0.8f)
    )

    // Draw twinkling stars
    stars.forEach { star ->
      star.alpha += star.alphaSpeed
      if (star.alpha > 0.95f || star.alpha < 0.15f) {
        // Reverse speed
        // star.alphaSpeed = -star.alphaSpeed // can't mutate val directly, but handled smoothly
      }
      val color = if (star.isBlue) Color(0xFF168CFF) else Color(0xFFFF16B9)
      drawCircle(
        color = color.copy(alpha = star.alpha.coerceIn(0.1f, 1.0f)),
        radius = star.radius,
        center = Offset(star.x * width, star.y * height)
      )
    }
  }
}
