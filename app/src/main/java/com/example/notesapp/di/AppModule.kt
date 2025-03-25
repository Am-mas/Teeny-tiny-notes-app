package com.example.notesapp.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase{
       return Room.databaseBuilder(application,NoteDatabase::class.java,"notes_db").build()
    }
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepository(noteDao)

    @Provides
    @Singleton
    fun provideNoteViewModel(repository: NoteRepository): NoteViewModel {
        return NoteViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideNoteViewModelFactory(viewModel: NoteViewModel):ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                    return viewModel as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}