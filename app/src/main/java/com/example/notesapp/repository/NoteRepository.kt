package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()


    suspend fun insert(note:Note){
        noteDao.insert(note)
    }
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    fun getNoteById(id:Int): LiveData<Note> = noteDao.getNoteById(id)

    suspend fun deleteNoteById(id:Int) = noteDao.deleteNoteId(id)

}