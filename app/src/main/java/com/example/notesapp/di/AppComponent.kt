package com.example.notesapp.di

import android.app.Application
import com.example.notesapp.MainActivity
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.ui.AddNoteFragment
import com.example.notesapp.ui.NoteDetailFragment
import com.example.notesapp.ui.NoteFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getRepository(): NoteRepository
    fun inject(mainActivity: MainActivity)
    fun inject(noteFragment: NoteFragment)
    fun inject(noteDetailFragment: NoteDetailFragment)
    fun inject(addNoteFragment: AddNoteFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }
}