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
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNoteDetailBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class NoteDetailFragment: BottomSheetDialogFragment() {
    @Inject
    lateinit var noteViewModelFactory: ViewModelProvider.Factory

    private val noteViewModel: NoteViewModel by viewModels {noteViewModelFactory }
    private var _binding: FragmentNoteDetailBinding?= null
    private val binding get() = _binding!!
    private var noteId: Int = -1
    private var notes: Note? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NotesApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            notes = it.getParcelable("notes")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        binding.noteTitle.text = notes?.title
        binding.noteContent.text = notes?.content
        val deleteIcon: ImageButton = binding.deleteButton
        deleteIcon.setOnClickListener{
            noteViewModel.deleteNoteById(noteId)
            Toast.makeText(requireContext(),"Deleted",Toast.LENGTH_SHORT).show()
            dismiss()
        }


//        noteViewModel.getNoteById(noteId).observe(viewLifecycleOwner) { note ->
//            if (note != null) {
//                binding.noteTitle.text = note.title
//                binding.noteContent.text = note.content
//            }
//        }
    }

    companion object {
        fun newInstance(notes: Note): NoteDetailFragment{
            return NoteDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("notes", notes)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}