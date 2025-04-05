package com.example.notesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory
import javax.inject.Inject

class NoteFragment: Fragment() {
    @Inject
    lateinit var noteViewModelFactory: ViewModelProvider.Factory

    private val noteViewModel: NoteViewModel by viewModels {noteViewModelFactory }

    private var _binding: FragmentNoteBinding? = null
    private  val binding get() = _binding!!
    private lateinit var noteRecycler: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NotesApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentNoteBinding.inflate(inflater,container,false)
        noteRecycler = binding.recyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notes = ArrayList<Note>()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addNoteFragment)
        }
        noteAdapter = NoteAdapter(parentFragmentManager)
        noteRecycler.layoutManager = LinearLayoutManager(requireContext())
        noteRecycler.adapter= noteAdapter
        LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        noteViewModel.allNotes.observe(viewLifecycleOwner) {notes ->
            noteAdapter.submitList(notes)
        }

    }

}