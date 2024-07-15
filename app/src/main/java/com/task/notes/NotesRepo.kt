package com.task.notes

import androidx.lifecycle.LiveData
import com.task.notes.db.Note
import com.task.notes.db.NoteDAO

class NotesRepo(private val noteDao: NoteDAO ,  userId: String) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes(userId)

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}
