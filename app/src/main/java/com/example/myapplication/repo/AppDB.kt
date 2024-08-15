package com.example.myapplication.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.model.PokerGameSession

@Database(entities = [PokerGameSession::class], version = 3)
@TypeConverters(Converters::class)
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
                )
                    .fallbackToDestructiveMigration() // This allows for destructive migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
