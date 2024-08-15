package com.example.myapplication.ui.util

import com.example.myapplication.model.PokerGameSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GameOrderingUtils {

    fun groupGamesByMonth(games: List<PokerGameSession>): List<Pair<String, List<PokerGameSession>>> {
        return games.groupBy { game ->
            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            dateFormat.format(game.date!!) // Assuming `game.date` is of type Date
        }.entries.sortedByDescending { it.key }
            .map { entry -> Pair(entry.key, entry.value) }
    }
}