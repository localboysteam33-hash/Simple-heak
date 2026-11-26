package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CardBackground
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun PremiumPasswordDialog(
  onVerify: (String) -> Boolean,
  onDismiss: () -> Unit
) {
  var passwordInput by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  Dialog(onDismissRequest = onDismiss) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(CardBackground, RoundedCornerShape(22.dp))
        .border(1.5.dp, NeonCyan, RoundedCornerShape(22.dp))
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "🔒 MUNIN_BHAI VIP PREMIUM",
        color = NeonCyan,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "This app is 1000 Years Free! For VIP Premium features & advanced signals, enter your pass (MUMIN3231).",
        color = LightText,
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = passwordInput,
        onValueChange = {
          passwordInput = it
          errorMessage = null
        },
        label = { Text("Enter Pass (MUMIN3231)") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = NeonCyan,
          unfocusedBorderColor = NeonPurple,
          focusedTextColor = LightText,
          unfocusedTextColor = LightText
        ),
        singleLine = true
      )

      if (errorMessage != null) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = errorMessage!!, color = NeonPink, fontSize = 12.sp)
      }

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = {
          val success = onVerify(passwordInput)
          if (!success) {
            errorMessage = "Invalid Pass! Correct password is MUMIN3231"
          }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
        shape = RoundedCornerShape(30.dp)
      ) {
        Text(
          text = "Unlock VIP Premium",
          color = DarkBackground,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        onClick = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
        shape = RoundedCornerShape(30.dp)
      ) {
        Text("Cancel", color = LightText, fontWeight = FontWeight.Bold)
      }
    }
  }
}
