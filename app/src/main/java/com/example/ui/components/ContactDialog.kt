package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CardBackground
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun ContactDialog(
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(onDismissRequest = onDismiss) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(CardBackground, RoundedCornerShape(22.dp))
        .border(1.5.dp, NeonPink, RoundedCornerShape(22.dp))
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "➤ CONTACT & SUPPORT",
        color = NeonPink,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "MUNIN_BHAI VIP official Telegram channels & admin support.",
        color = LightText,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/szxpolash"))
          context.startActivity(intent)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
        shape = RoundedCornerShape(30.dp)
      ) {
        Text(
          text = "Telegram: @szxpolash",
          color = DarkBackground,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/szx_polash_admin"))
          context.startActivity(intent)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NeonBlue),
        shape = RoundedCornerShape(30.dp)
      ) {
        Text(
          text = "Admin: szx_polash_admin",
          color = LightText,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
        shape = RoundedCornerShape(30.dp)
      ) {
        Text("Close", color = LightText, fontWeight = FontWeight.Bold)
      }
    }
  }
}
