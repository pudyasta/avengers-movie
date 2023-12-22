package com.example.uts.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uts.model.Films

@Database(
    entities = [Films::class],
    version = 1
)
abstract class FilmDB : RoomDatabase() {
    abstract  fun filmDao():FilmDao
    companion object {
        @Volatile
        private var instance: FilmDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context.applicationContext).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FilmDB::class.java,
                "filmdb"
            ).build()
    }
}