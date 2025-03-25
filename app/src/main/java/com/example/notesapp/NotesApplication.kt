package com.example.notesapp
import android.app.Application
import com.example.notesapp.di.AppComponent
import com.example.notesapp.di.DaggerAppComponent

class NotesApplication: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}