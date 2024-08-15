package com.example.myapplication.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myapplication.model.PokerGameSession

class PokerGameRepository(private val gameDao: PokerGameDao) {

    suspend fun insertGameSession(session: PokerGameSession) {
        try {
            gameDao.insert(session)
        } catch (e: Exception) {
            Log.e("PBK - repoInsert", "Error inserting game session", e)
            throw e // Ensure exceptions are re-thrown so they can be caught upstream
        }
    }

    suspend fun deleteGameSession(sessionId: Long) {
        gameDao.deleteById(sessionId)
    }

    suspend fun updateGameSession(session: PokerGameSession) {
        gameDao.update(session)
    }

    fun getAllGameSessions(): LiveData<List<PokerGameSession>> {
        return gameDao.getAllSessions()
    }
}