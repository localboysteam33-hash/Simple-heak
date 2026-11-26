package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChatMessage
import com.example.data.RoundItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

enum class ConnectionStatus {
  LIVE,
  CONNECTING,
  OFFLINE
}

class HackrifatViewModel : ViewModel() {

  private val _currentMultiplier = MutableStateFlow("2.14x")
  val currentMultiplier: StateFlow<String> = _currentMultiplier.asStateFlow()

  private val _connectionStatus = MutableStateFlow(ConnectionStatus.LIVE)
  val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

  private val _roundHistory = MutableStateFlow<List<RoundItem>>(
    listOf(
      RoundItem("R-2001", 1.85, "19:48:12", true),
      RoundItem("R-2000", 1.22, "19:47:40", false),
      RoundItem("R-1999", 3.40, "19:47:05", true),
      RoundItem("R-1998", 1.15, "19:46:30", false),
      RoundItem("R-1997", 2.10, "19:45:55", true)
    )
  )
  val roundHistory: StateFlow<List<RoundItem>> = _roundHistory.asStateFlow()

  private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage("1", "MUNIN_BHAI BOT", "Welcome to MUNIN_BHAI VIP V2. 1000 Years Free Access Active!", "19:45", true),
      ChatMessage("2", "@szxpolash", "Enter password MUMIN3231 in VIP pass for premium signals.", "19:46", true),
      ChatMessage("3", "System", "Connected to live Aviator server stream.", "19:47", false)
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  private val _isSettingsOpen = MutableStateFlow(false)
  val isSettingsOpen: StateFlow<Boolean> = _isSettingsOpen.asStateFlow()

  private val _isContactOpen = MutableStateFlow(false)
  val isContactOpen: StateFlow<Boolean> = _isContactOpen.asStateFlow()

  private val _isPremiumModalOpen = MutableStateFlow(false)
  val isPremiumModalOpen: StateFlow<Boolean> = _isPremiumModalOpen.asStateFlow()

  private val _isPremiumUnlocked = MutableStateFlow(false)
  val isPremiumUnlocked: StateFlow<Boolean> = _isPremiumUnlocked.asStateFlow()

  private val _apiEndpoint = MutableStateFlow("https://api.muninbhai.live/v2/stream")
  val apiEndpoint: StateFlow<String> = _apiEndpoint.asStateFlow()

  private val _soundEnabled = MutableStateFlow(true)
  val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

  private val _vibrationEnabled = MutableStateFlow(true)
  val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

  private val _lastUpdateTime = MutableStateFlow("--:--:--")
  val lastUpdateTime: StateFlow<String> = _lastUpdateTime.asStateFlow()

  private var updateJob: Job? = null
  private var roundCounter = 2002

  init {
    startLiveUpdates()
  }

  fun toggleSettings(open: Boolean) {
    _isSettingsOpen.value = open
  }

  fun toggleContact(open: Boolean) {
    _isContactOpen.value = open
  }

  fun togglePremiumModal(open: Boolean) {
    _isPremiumModalOpen.value = open
  }

  fun verifyPremiumPassword(pass: String): Boolean {
    if (pass == "MUMIN3231") {
      _isPremiumUnlocked.value = true
      _isPremiumModalOpen.value = false
      addChatMessage("MUNIN_BHAI VIP", "Password MUMIN3231 accepted! VIP Premium Unlocked.", true)
      return true
    }
    return false
  }

  fun updateApiEndpoint(url: String) {
    _apiEndpoint.value = url
  }

  fun toggleSound(enabled: Boolean) {
    _soundEnabled.value = enabled
  }

  fun toggleVibration(enabled: Boolean) {
    _vibrationEnabled.value = enabled
  }

  fun testConnection() {
    viewModelScope.launch {
      _connectionStatus.value = ConnectionStatus.CONNECTING
      delay(1000)
      _connectionStatus.value = ConnectionStatus.LIVE
    }
  }

  fun addChatMessage(sender: String, text: String, isVip: Boolean = false) {
    val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val newMsg = ChatMessage(
      id = "MSG-${System.currentTimeMillis()}",
      sender = sender,
      text = text,
      time = timeStr,
      isVip = isVip
    )
    _chatMessages.update { list ->
      val updated = mutableListOf(newMsg)
      updated.addAll(list)
      if (updated.size > 20) updated.take(20) else updated
    }
  }

  private fun startLiveUpdates() {
    updateJob?.cancel()
    updateJob = viewModelScope.launch {
      while (true) {
        // Exactly 7 seconds per round as requested
        delay(7000L)
        if (_connectionStatus.value == ConnectionStatus.LIVE) {
          val rawValue = Random.nextDouble(1.10, 5.80)
          val formatted = String.format(Locale.US, "%.2fx", rawValue)
          _currentMultiplier.value = formatted

          val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
          _lastUpdateTime.value = timeStr

          val newRound = RoundItem(
            id = "R-$roundCounter",
            multiplier = Math.round(rawValue * 100.0) / 100.0,
            timestamp = timeStr,
            isGreen = rawValue >= 1.50
          )
          roundCounter++

          _roundHistory.update { currentList ->
            val updated = mutableListOf(newRound)
            updated.addAll(currentList)
            if (updated.size > 10) updated.take(10) else updated
          }

          // Add automated chat signal message every round update
          val statusText = if (rawValue >= 2.0) "High Multiplier Alert! 🚀" else "Safe Round Completed."
          addChatMessage(
            sender = "MUNIN_BHAI SIGNAL",
            text = "Round R-${roundCounter - 1} finished at ${formatted}. $statusText",
            isVip = true
          )
        }
      }
    }
  }
}
