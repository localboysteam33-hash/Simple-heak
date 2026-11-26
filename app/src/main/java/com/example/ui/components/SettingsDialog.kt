package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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

@Composable
fun SettingsDialog(
  apiEndpoint: String,
  soundEnabled: Boolean,
  vibrationEnabled: Boolean,
  onEndpointChange: (String) -> Unit,
  onSoundChange: (Boolean) -> Unit,
  onVibrationChange: (Boolean) -> Unit,
  onTestConnection: () -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(CardBackground, RoundedCornerShape(20.dp))
        .border(1.dp, NeonPurple, RoundedCornerShape(20.dp))
        .padding(20.dp)
    ) {
      Text(
        text = "⚙ সেটিং / Settings",
        color = NeonPurple,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "API Endpoint",
        color = MutedText,
        fontSize = 14.sp
      )
      Spacer(modifier = Modifier.height(6.dp))
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

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Sound Effects",
          color = LightText,
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
          modifier = Modifier.weight(1f)
        )
        Switch(
          checked = vibrationEnabled,
          onCheckedChange = onVibrationChange,
          colors = SwitchDefaults.colors(checkedThumbColor = NeonPink)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "এটি একটি প্রিমিয়াম এভিয়েটর ডিজাইন ও রিয়েল-টাইম সিগন্যাল ড্যাশবোর্ড। প্রদর্শিত ডেটা ডিজাইন ডেমো ও লাইভ স্ট্রিমিং সোর্স থেকে আসে।",
        color = MutedText,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(20.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
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
