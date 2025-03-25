package com.example.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel(){
    val allNotes: LiveData<List<Note>> = repository.allNotes


    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
    fun getNoteById(id:Int) : LiveData<Note> = repository.getNoteById(id)

    fun deleteNoteById(id: Int) = viewModelScope.launch {
        repository.deleteNoteById(id)
    }
}