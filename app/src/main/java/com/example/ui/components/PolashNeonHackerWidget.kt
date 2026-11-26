package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import kotlin.random.Random

data class RainDrop(
  val x: Float,
  val y: Float,
  val speed: Float,
  val color: Color
)

@Composable
fun PolashNeonHackerWidget() {
  val infiniteTransition = rememberInfiniteTransition(label = "hacker_anim")

  val pulseAnim by infiniteTransition.animateFloat(
    initialValue = 0.92f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  val rainTick by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1000f,
    animationSpec = infiniteRepeatable(
      animation = tween(10000, easing = LinearEasing)
    ),
    label = "rain"
  )

  val rainDrops = remember {
    List(35) {
      RainDrop(
        x = Random.nextFloat(),
        y = Random.nextFloat(),
        speed = Random.nextFloat() * 1.5f + 0.8f,
        color = listOf(NeonPink, NeonCyan, NeonBlue, NeonPurple).random()
      )
    }
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(130.dp)
      .background(DarkBackground, RoundedCornerShape(16.dp))
      .border(1.5.dp, NeonPink, RoundedCornerShape(16.dp)),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.matchParentSize()) {
      val width = size.width
      val height = size.height
      val centerX = width / 2f
      val centerY = height / 2f

      // Cyberpunk Circuit Background
      drawRect(
        brush = Brush.radialGradient(
          colors = listOf(Color(0xFF180630), Color(0xFF05000D)),
          center = Offset(centerX, centerY),
          radius = width * 0.8f
        )
      )

      // Hacker Code Rain
      rainDrops.forEach { drop ->
        val currentY = ((drop.y * height + (rainTick * drop.speed)) % height)
        drawCircle(
          color = drop.color.copy(alpha = 0.6f),
          radius = 2.5f,
          center = Offset(drop.x * width, currentY)
        )
      }

      // Rotating Neon Pink Circular Energy Rings
      val ringRadius = 55.dp.toPx() * pulseAnim
      drawCircle(
        color = NeonPink.copy(alpha = 0.4f),
        radius = ringRadius,
        center = Offset(centerX, centerY),
        style = Stroke(width = 3.dp.toPx())
      )

      drawCircle(
        color = NeonCyan.copy(alpha = 0.3f),
        radius = ringRadius * 0.8f,
        center = Offset(centerX, centerY),
        style = Stroke(width = 2.dp.toPx())
      )
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(horizontal = 8.dp)
    ) {
      Text(
        text = "POWERED BY",
        color = NeonBlue,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "POLASH",
        color = NeonCyan,
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 4.sp,
        textAlign = TextAlign.Center
      )
    }
  }
}
