package com.example.notesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNoteDetailBinding
import com.example.notesapp.viewmodel.NoteViewModel
import javax.inject.Inject

class NoteDetailFragment: Fragment() {
    @Inject
    lateinit var noteViewModelFactory: ViewModelProvider.Factory

    private val noteViewModel: NoteViewModel by viewModels {noteViewModelFactory }
    private var _binding: FragmentNoteDetailBinding?= null
    private val binding get() = _binding!!
    private var noteId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NotesApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.navigationIcon?.setTint(com.google.android.material.R.attr.colorOnBackground)
        binding.toolBar.menu.hasVisibleItems()
        val deleteIcon = binding.deleteButton
        deleteIcon.setOnClickListener{
            noteViewModel.deleteNoteById(noteId)
            findNavController().navigate(R.id.action_noteDetailFragment_to_noteFragment)
        }
        noteViewModel.getNoteById(noteId).observe(viewLifecycleOwner) { note ->
            if (note != null) {
                binding.noteTitle.text = note.title
                binding.noteContent.text = note.content
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.deleteButton ->{
                noteViewModel.deleteNoteById(noteId)
                findNavController().navigate(R.id.action_noteDetailFragment_to_noteFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }
}