package com.example.uts.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.uts.model.Films

@Dao
interface FilmDao {
    @Insert
    suspend fun insertNote(film: Films)

    @Update
    suspend fun updateNote(film: Films)

    @Delete
    suspend fun deleteNote(film: Films)

    @Query("SELECT * FROM films")
    suspend fun getAllNotes():List<Films>

    @Query("SELECT * FROM films WHERE id=:note_id")
    suspend fun getNote(note_id: Int):List<Films>

    @Query("DELETE FROM films")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<Films>)
}