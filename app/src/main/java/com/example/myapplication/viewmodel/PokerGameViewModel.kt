package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.PokerGameSession
import com.example.myapplication.repo.PokerGameRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PokerGameViewModel(private val repository: PokerGameRepository): ViewModel() {
    private val _gameSessions = MutableLiveData<List<PokerGameSession>>()
    val gameSessions: LiveData<List<PokerGameSession>> get() = _gameSessions

    init {
        fetchGameSessions()
    }

    fun addGameSession(session: PokerGameSession) = viewModelScope.launch {
        try {
            repository.insertGameSession(session)
            fetchGameSessions()  // Refresh list
            Log.d("PBK - VM add game", _gameSessions.value?.size.toString())
        } catch (e: Exception) {
            Log.e("PBK - VM add game", "Error adding game session", e)
        }
    }

    fun removeGameSession(sessionId: Long) = viewModelScope.launch {
        repository.deleteGameSession(sessionId)
        fetchGameSessions()  // Refresh list
    }

    fun updateGameSession(session: PokerGameSession) = viewModelScope.launch {
        repository.updateGameSession(session)
        fetchGameSessions()  // Refresh list
    }

    private fun fetchGameSessions() = viewModelScope.launch {
        repository.getAllGameSessions().observeForever { sessions ->
            _gameSessions.value = sessions
            Log.d("PBK - repoGetAll", "Fetched sessions: ${sessions?.size}")
        }
    }

    // Calculate earnings for graph plotting
    fun calculateEarnings(): List<Pair<String?, Double>> {
        // Calculate earnings for plotting
        return _gameSessions.value?.map {
            Pair(it.date, it.cashOutAmount - it.buyInAmount)
        } ?: emptyList()
    }

    fun groupGamesByMonth(games: List<PokerGameSession>): List<Pair<String, List<PokerGameSession>>> {
        val groupedGames = games.groupBy {
            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            dateFormat.format(it.date ?: Date()) // Adjust based on your date field
        }
        return groupedGames.entries
            .sortedByDescending { SimpleDateFormat("MMMM yyyy", Locale.getDefault()).parse(it.key)?.time ?: 0L }
            .map { it.key to it.value }
    }
}