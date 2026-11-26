package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CardBackground
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsDialog(
  apiEndpoint: String,
  currentInterval: Long,
  soundEnabled: Boolean,
  vibrationEnabled: Boolean,
  onEndpointChange: (String) -> Unit,
  onIntervalChange: (Long) -> Unit,
  onSoundChange: (Boolean) -> Unit,
  onVibrationChange: (Boolean) -> Unit,
  onTestConnection: () -> Unit,
  onDismiss: () -> Unit
) {
  val intervalOptions = listOf(5L, 7L, 10L, 15L, 20L, 25L, 30L, 35L, 40L, 45L, 50L)

  Dialog(onDismissRequest = onDismiss) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(CardBackground, RoundedCornerShape(20.dp))
        .border(1.dp, NeonPurple, RoundedCornerShape(20.dp))
        .padding(18.dp)
    ) {
      Text(
        text = "⚙ সেটিং / Round Interval",
        color = NeonPurple,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Select Result Interval (Seconds):",
        color = MutedText,
        fontSize = 13.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        intervalOptions.forEach { secs ->
          val isSelected = currentInterval == secs
          Box(
            modifier = Modifier
              .background(
                if (isSelected) NeonCyan else Color(0xFF150B2E),
                RoundedCornerShape(12.dp)
              )
              .border(
                1.dp,
                if (isSelected) NeonCyan else NeonPurple,
                RoundedCornerShape(12.dp)
              )
              .clickable { onIntervalChange(secs) }
              .padding(horizontal = 10.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${secs}s",
              color = if (isSelected) Color(0xFF05000D) else LightText,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "API Endpoint",
        color = MutedText,
        fontSize = 13.sp
      )
      Spacer(modifier = Modifier.height(4.dp))
      OutlinedTextField(
        value = apiEndpoint,
        onValueChange = onEndpointChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = NeonBlue,
          unfocusedBorderColor = NeonPurple,
          focusedTextColor = LightText,
          unfocusedTextColor = LightText
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Sound Effects",
          color = LightText,
          fontSize = 13.sp,
          modifier = Modifier.weight(1f)
        )
        Switch(
          checked = soundEnabled,
          onCheckedChange = onSoundChange,
          colors = SwitchDefaults.colors(checkedThumbColor = NeonCyan)
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Vibration Feedback",
          color = LightText,
          fontSize = 13.sp,
          modifier = Modifier.weight(1f)
        )
        Switch(
          checked = vibrationEnabled,
          onCheckedChange = onVibrationChange,
          colors = SwitchDefaults.colors(checkedThumbColor = NeonPink)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onTestConnection,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(containerColor = NeonBlue)
        ) {
          Text("Test Ping", color = LightText, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = onDismiss,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
        ) {
          Text("বন্ধ করুন", color = LightText, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
