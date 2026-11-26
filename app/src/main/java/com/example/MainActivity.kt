package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.screens.HackrifatMainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.HackrifatViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: HackrifatViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        HackrifatMainScreen(viewModel = viewModel)
      }
    }
  }
}
