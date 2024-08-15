package com.example.myapplication.model

sealed class GameListItem {
    data class Game(val game: PokerGameSession) : GameListItem()
    data class MonthHeader(val month: String) : GameListItem()
}