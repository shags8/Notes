package com.task.notes.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.task.notes.NotesAdapter
import com.task.notes.NotesViewModel
import com.task.notes.R
import com.task.notes.db.Note

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        adapter = NotesAdapter { note -> showNoteOptions(note) }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        notesViewModel.notes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let { adapter.submitList(it) }
        })

        view.findViewById<FloatingActionButton>(R.id.fab_add_note).setOnClickListener {
            showAddNoteDialog()
        }

        return view
    }

    private fun showNoteOptions(note: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle("Note Options")
            .setItems(arrayOf("Edit", "Delete")) { _, which ->
                when (which) {
                    0 -> showEditNoteDialog(note)
                    1 -> deleteNote(note)
                }
            }
            .show()
    }

    private fun showAddNoteDialog() {
        val inputView = EditText(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("Add New Note")
            .setView(inputView)
            .setPositiveButton("Add") { _, _ ->
                val content = inputView.text.toString()
                if (content.isNotEmpty()) {
                    val newNote = Note(0, content)
                    notesViewModel.insert(newNote)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditNoteDialog(note: Note) {
        val inputView = EditText(requireContext())
        inputView.setText(note.content)
        AlertDialog.Builder(requireContext())
            .setTitle("Edit Note")
            .setView(inputView)
            .setPositiveButton("Save") { _, _ ->
                val content = inputView.text.toString()
                if (content.isNotEmpty()) {
                    val updatedNote = note.copy(content = content)
                    notesViewModel.update(updatedNote)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteNote(note: Note) {
        notesViewModel.delete(note)
    }

}