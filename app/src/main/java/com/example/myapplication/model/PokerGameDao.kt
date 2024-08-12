package com.example.myapplication.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokerGameDao {
    @Insert
    suspend fun insert(session: PokerGameSession)

    @Query("DELETE FROM poker_game_sessions WHERE id = :sessionId")
    suspend fun deleteById(sessionId: Long)

    @Update
    suspend fun update(session: PokerGameSession)

    @Query("SELECT * FROM poker_game_sessions")
    fun getAllSessions(): LiveData<List<PokerGameSession>>
}
