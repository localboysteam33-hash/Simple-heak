package com.example.ui.screens

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.ChatMessage
import com.example.data.RoundItem
import com.example.ui.components.ChatHistorySection
import com.example.ui.components.ContactDialog
import com.example.ui.components.PolashNeonHackerWidget
import com.example.ui.components.PremiumPasswordDialog
import com.example.ui.components.RoundRowItem
import com.example.ui.components.SettingsDialog
import com.example.ui.components.StarryGalaxyBackground
import com.example.viewmodel.ConnectionStatus
import com.example.viewmodel.HackrifatViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HackrifatMainScreen(viewModel: HackrifatViewModel) {
  val currentMultiplier by viewModel.currentMultiplier.collectAsStateWithLifecycle()
  val connectionStatus by viewModel.connectionStatus.collectAsStateWithLifecycle()
  val roundHistory by viewModel.roundHistory.collectAsStateWithLifecycle()
  val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
  val isSettingsOpen by viewModel.isSettingsOpen.collectAsStateWithLifecycle()
  val isContactOpen by viewModel.isContactOpen.collectAsStateWithLifecycle()
  val isPremiumModalOpen by viewModel.isPremiumModalOpen.collectAsStateWithLifecycle()
  val isPremiumUnlocked by viewModel.isPremiumUnlocked.collectAsStateWithLifecycle()
  val apiEndpoint by viewModel.apiEndpoint.collectAsStateWithLifecycle()
  val soundEnabled by viewModel.soundEnabled.collectAsStateWithLifecycle()
  val vibrationEnabled by viewModel.vibrationEnabled.collectAsStateWithLifecycle()
  val lastUpdateTime by viewModel.lastUpdateTime.collectAsStateWithLifecycle()

  var clockTime by remember { mutableStateOf("00:00:00") }

  LaunchedEffect(Unit) {
    while (true) {
      clockTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
      kotlinx.coroutines.delay(1000)
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "spin_anim")
  val rotationAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(12000, easing = androidx.compose.animation.core.LinearEasing)
    ),
    label = "rotation"
  )

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF05000D))
  ) {
    StarryGalaxyBackground()

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Clock Pill
        Box(
          modifier = Modifier
            .weight(1.2f)
            .background(Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, Color(0xFFBD91FF), RoundedCornerShape(40.dp))
            .padding(vertical = 8.dp, horizontal = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "◷ $clockTime",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // VIP / Premium Status Button (1000 Years Free / Pass MUMIN3231)
        Box(
          modifier = Modifier
            .weight(1.1f)
            .background(if (isPremiumUnlocked) Color(0xFF00EFBD).copy(alpha = 0.2f) else Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, if (isPremiumUnlocked) Color(0xFF00EFBD) else Color(0xFFFF16B9), RoundedCornerShape(40.dp))
            .clickable { viewModel.togglePremiumModal(true) }
            .padding(vertical = 8.dp, horizontal = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = if (isPremiumUnlocked) "👑 VIP 1000Y" else "🔒 VIP PASS",
            color = if (isPremiumUnlocked) Color(0xFF00EFBD) else Color(0xFFFF16B9),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // Settings Pill Button
        Box(
          modifier = Modifier
            .weight(0.9f)
            .background(Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, Color(0xFFBD91FF), RoundedCornerShape(40.dp))
            .clickable { viewModel.toggleSettings(true) }
            .padding(vertical = 8.dp, horizontal = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "⚙ সেটিং",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Brand Header Area with User Avatar (Left) and MUNIN_BHAI Title
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(52.dp)
              .background(Color(0xFF050008), CircleShape)
              .border(2.5.dp, Color(0xFF00EFBD), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = R.drawable.munin_avatar),
              contentDescription = "Munin Bhai Avatar",
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
              contentScale = ContentScale.Crop
            )
          }

          Column {
            Text(
              text = "MUNIN_BHAI ¡!",
              fontSize = 16.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF00EFBD)
            )
            Text(
              text = "1000Y Free / VIP",
              fontSize = 10.sp,
              color = Color(0xFFC8B5DF),
              fontWeight = FontWeight.Bold
            )
          }
        }

        Box(
          modifier = Modifier
            .background(Color(0xFF10072E), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF00EFBD), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = "ACTIVE",
            color = Color(0xFF00EFBD),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // POWERED BY POLASH Neon Hacker GIF / Animated Header Widget (Replacing Aviator logo)
      PolashNeonHackerWidget()

      Spacer(modifier = Modifier.height(4.dp))

      // Stage Display (Compact Aviator Orb & Rotating Frame)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(130.dp),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(110.dp)
            .rotate(rotationAngle)
            .border(2.dp, Color(0xFFA75AFF), RoundedCornerShape(12.dp))
        )

        Box(
          modifier = Modifier
            .size(85.dp)
            .background(Color(0xFF09051B), CircleShape)
            .border(2.5.dp, Color(0xFFA76AFF), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
          ) {
            Text(
              text = if (isPremiumUnlocked) "👑 VIP SIGNAL" else "7s INTERVAL",
              color = Color(0xFFC8B5DF),
              fontSize = 8.sp,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = currentMultiplier,
              color = Color(0xFFFF064B),
              fontSize = 20.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "AVIATOR",
              color = Color.White,
              fontSize = 8.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Section Header for History
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "সাম্প্রতিক রাউন্ড হিস্ট্রি (7s Update)",
          color = Color(0xFF00EFBD),
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Sync: $lastUpdateTime",
          color = Color(0xFFC8B5DF),
          fontSize = 9.sp
        )
      }

      Spacer(modifier = Modifier.height(2.dp))

      // LazyColumn for Latest 10 Aviator Results
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 2.dp)
      ) {
        items(roundHistory) { round: RoundItem ->
          RoundRowItem(round = round)
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Chat & Signal History Section
      ChatHistorySection(messages = chatMessages)

      Spacer(modifier = Modifier.height(6.dp))

      // Contact US Button
      Button(
        onClick = { viewModel.toggleContact(true) },
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp),
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFFE5CCFF)
        )
      ) {
        Text(
          text = "➤ CONTACT (@szxpolash)",
          color = Color(0xFF080018),
          fontSize = 16.sp,
          fontWeight = FontWeight.Black
        )
      }
    }

    // Settings Dialog
    if (isSettingsOpen) {
      SettingsDialog(
        apiEndpoint = apiEndpoint,
        soundEnabled = soundEnabled,
        vibrationEnabled = vibrationEnabled,
        onEndpointChange = { viewModel.updateApiEndpoint(it) },
        onSoundChange = { viewModel.toggleSound(it) },
        onVibrationChange = { viewModel.toggleVibration(it) },
        onTestConnection = { viewModel.testConnection() },
        onDismiss = { viewModel.toggleSettings(false) }
      )
    }

    // Contact Dialog
    if (isContactOpen) {
      ContactDialog(
        onDismiss = { viewModel.toggleContact(false) }
      )
    }

    // Premium Password Dialog (Pass: MUMIN3231)
    if (isPremiumModalOpen) {
      PremiumPasswordDialog(
        onVerify = { pass -> viewModel.verifyPremiumPassword(pass) },
        onDismiss = { viewModel.togglePremiumModal(false) }
      )
    }
  }
}
