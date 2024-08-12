package com.example.myapplication.viewmodel

import PokerGameRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokerGameViewModelFactory(
    private val repository: PokerGameRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokerGameViewModel::class.java)) {
            return PokerGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
