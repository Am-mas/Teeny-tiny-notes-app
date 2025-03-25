package com.example.notesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}