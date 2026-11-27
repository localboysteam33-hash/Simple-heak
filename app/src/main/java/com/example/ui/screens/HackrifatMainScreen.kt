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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.RoundItem
import com.example.ui.components.ChatHistorySection
import com.example.ui.components.ContactDialog
import com.example.ui.components.PolashNeonHackerWidget
import com.example.ui.components.PremiumPasswordDialog
import com.example.ui.components.RoundRowItem
import com.example.ui.components.SettingsDialog
import com.example.ui.components.TerminalLogOverlay
import com.example.ui.components.WebViewComposable
import com.example.viewmodel.HackrifatViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HackrifatMainScreen(viewModel: HackrifatViewModel) {
  val currentMultiplier by viewModel.currentMultiplier.collectAsStateWithLifecycle()
  val roundHistory by viewModel.roundHistory.collectAsStateWithLifecycle()
  val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
  val isResultsModalOpen by viewModel.isResultsModalOpen.collectAsStateWithLifecycle()
  val isSettingsOpen by viewModel.isSettingsOpen.collectAsStateWithLifecycle()
  val isContactOpen by viewModel.isContactOpen.collectAsStateWithLifecycle()
  val isPremiumModalOpen by viewModel.isPremiumModalOpen.collectAsStateWithLifecycle()
  val isPremiumUnlocked by viewModel.isPremiumUnlocked.collectAsStateWithLifecycle()
  val apiEndpoint by viewModel.apiEndpoint.collectAsStateWithLifecycle()
  val roundIntervalSeconds by viewModel.roundIntervalSeconds.collectAsStateWithLifecycle()
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
      .background(Color(0xFF000000))
  ) {
    // 1. Fullscreen WebView loading amarclub17.com behind everything
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 55.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      WebViewComposable(
        url = "https://amarclub17.com",
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      )
    }

    // 2. Sticky Header at the Top with HEAK Logo
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xE605000D))
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // HEAK Logo (Top-Left) -> Clicking opens Results & Signals Modal
        Box(
          modifier = Modifier
            .background(Color(0xFF180630), RoundedCornerShape(30.dp))
            .border(1.5.dp, Color(0xFFFF16B9), RoundedCornerShape(30.dp))
            .clickable { viewModel.toggleResultsModal(true) }
            .padding(horizontal = 12.dp, vertical = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "⚡ HEAK 📊",
            color = Color(0xFFFF16B9),
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
        }

        // Clock Pill
        Box(
          modifier = Modifier
            .weight(1f)
            .background(Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, Color(0xFFBD91FF), RoundedCornerShape(40.dp))
            .padding(vertical = 6.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "◷ $clockTime",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // VIP / Premium Status Button (Pass MUMIN3231)
        Box(
          modifier = Modifier
            .weight(1f)
            .background(if (isPremiumUnlocked) Color(0xFF00EFBD).copy(alpha = 0.2f) else Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, if (isPremiumUnlocked) Color(0xFF00EFBD) else Color(0xFFFF16B9), RoundedCornerShape(40.dp))
            .clickable { viewModel.togglePremiumModal(true) }
            .padding(vertical = 6.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = if (isPremiumUnlocked) "👑 1000Y VIP" else "🔒 VIP PASS",
            color = if (isPremiumUnlocked) Color(0xFF00EFBD) else Color(0xFFFF16B9),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        // Settings Pill Button
        Box(
          modifier = Modifier
            .weight(0.7f)
            .background(Color(0xCC09051D), RoundedCornerShape(40.dp))
            .border(1.dp, Color(0xFFBD91FF), RoundedCornerShape(40.dp))
            .clickable { viewModel.toggleSettings(true) }
            .padding(vertical = 6.dp, horizontal = 4.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "⚙ সেটিং",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // 3. Floating Neon Trigger Button at Bottom-Right for Results & Signals
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      contentAlignment = Alignment.BottomEnd
    ) {
      Button(
        onClick = { viewModel.toggleResultsModal(true) },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF16B9)),
        shape = RoundedCornerShape(30.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
      ) {
        Text(
          text = "⚡ HEAK RESULTS ($currentMultiplier)",
          color = Color.White,
          fontWeight = FontWeight.Black,
          fontSize = 14.sp
        )
      }
    }

    // 4. HEAK Results & Signal Dashboard Dialog Overlay (triggered by HEAK logo or floating button)
    if (isResultsModalOpen) {
      Dialog(onDismissRequest = { viewModel.toggleResultsModal(false) }) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .height(580.dp)
            .background(Color(0xFF080314), RoundedCornerShape(22.dp))
            .border(2.dp, Color(0xFF00EFBD), RoundedCornerShape(22.dp))
            .padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "⚡ HEAK RESULTS & SIGNALS",
              color = Color(0xFF00EFBD),
              fontSize = 15.sp,
              fontWeight = FontWeight.Black
            )
            Button(
              onClick = { viewModel.toggleResultsModal(false) },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF16B9)),
              shape = RoundedCornerShape(16.dp)
            ) {
              Text("Close", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          PolashNeonHackerWidget()

          Spacer(modifier = Modifier.height(4.dp))

          TerminalLogOverlay()

          Spacer(modifier = Modifier.height(4.dp))

          // Stage Display
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(90.dp),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(75.dp)
                .rotate(rotationAngle)
                .border(2.dp, Color(0xFFA75AFF), RoundedCornerShape(10.dp))
            )

            Box(
              modifier = Modifier
                .size(60.dp)
                .background(Color(0xFF09051B), CircleShape)
                .border(2.dp, Color(0xFFA76AFF), CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = if (isPremiumUnlocked) "👑 VIP" else "${roundIntervalSeconds}s",
                  color = Color(0xFFC8B5DF),
                  fontSize = 7.sp
                )
                Text(
                  text = currentMultiplier,
                  color = Color(0xFFFF064B),
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "amarclub17.com History",
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

          LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
          ) {
            items(roundHistory) { round: RoundItem ->
              RoundRowItem(round = round)
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          ChatHistorySection(messages = chatMessages)
        }
      }
    }

    // Settings Dialog with Interval Selector
    if (isSettingsOpen) {
      SettingsDialog(
        apiEndpoint = apiEndpoint,
        currentInterval = roundIntervalSeconds,
        soundEnabled = soundEnabled,
        vibrationEnabled = vibrationEnabled,
        onEndpointChange = { viewModel.updateApiEndpoint(it) },
        onIntervalChange = { viewModel.updateRoundInterval(it) },
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
