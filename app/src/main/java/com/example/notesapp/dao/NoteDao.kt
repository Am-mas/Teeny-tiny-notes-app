package com.example.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notesapp.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteId(id: Int)

}