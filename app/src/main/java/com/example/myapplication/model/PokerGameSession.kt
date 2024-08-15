package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "poker_game_sessions")
data class PokerGameSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val smallBlind: Double,
    val bigBlind: Double,
    val ante: Double,
    val date: Date?,  // Use ISO format (e.g., "2024-08-11")
    val startTime: String?,  // Use ISO format (e.g., "14:00:00")
    val endTime: String?,  // Use ISO format (e.g., "18:00:00")
    val buyInAmount: Double,
    val cashOutAmount: Double,
    val location: String?
)