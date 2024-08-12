package com.example.myapplication.viewmodel

import PokerGameRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.PokerGameSession
import kotlinx.coroutines.launch

class PokerGameViewModel(private val repository: PokerGameRepository): ViewModel() {
    private val _gameSessions = MutableLiveData<List<PokerGameSession>>()
    val gameSessions: LiveData<List<PokerGameSession>> get() = _gameSessions

    fun addGameSession(session: PokerGameSession) = viewModelScope.launch {
        repository.insertGameSession(session)
        fetchGameSessions()  // Refresh list
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
        _gameSessions.value = repository.getAllGameSessions().value
    }

    // Calculate earnings for graph plotting
    fun calculateEarnings(): List<Pair<String?, Double>> {
        // Calculate earnings for plotting
        return _gameSessions.value?.map {
            Pair(it.date, it.cashOutAmount - it.buyInAmount)
        } ?: emptyList()
    }
}