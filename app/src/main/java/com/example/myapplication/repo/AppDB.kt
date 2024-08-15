package com.example.myapplication.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.PokerGameSession

@Database(entities = [PokerGameSession::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun pokerGameDao(): PokerGameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "poker_game_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
