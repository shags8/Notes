package com.task.notes

import android.app.Application
import android.os.Build.VERSION_CODES.S
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.task.notes.db.Note
import com.task.notes.db.NoteDatabase
import kotlinx.coroutines.launch

class NotesViewModel(application: Application, userId: String) : ViewModel() {
    private val repository: NotesRepo
    val notes: LiveData<List<Note>>

    init {
        val notesDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NotesRepo(notesDao , userId)
        notes = repository.allNotes
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }
        fun update(note: Note) {
            viewModelScope.launch {
                repository.update(note)
            }
        }

    fun delete(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}

class NotesViewModel_Factory(private val application: Application ,private val userId: String):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(application,userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
