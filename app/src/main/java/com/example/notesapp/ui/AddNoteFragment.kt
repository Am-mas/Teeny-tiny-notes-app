package com.example.notesapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import javax.inject.Inject


class AddNoteFragment: Fragment() {
    @Inject
    lateinit var noteViewModelFactory: ViewModelProvider.Factory

    private val noteViewModel: NoteViewModel by viewModels {noteViewModelFactory }
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private var alignmentIndex = 0
    private val alignments = listOf(
        Gravity.START,
        Gravity.CENTER,
        Gravity.END
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NotesApplication).appComponent.inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.toolBar
        toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
        val todoButton: ImageButton = binding.toDoListButton
        val todoContainer: LinearLayout = binding.todoContainer
        binding.saveButton.setOnClickListener {
            saveNote()
        }
        todoButton.setOnClickListener { v: View? ->
            todoButton.setColorFilter(androidx.appcompat.R.attr.colorPrimary)
            todoContainer.removeAllViews()
            val content: String = binding.contentEditText.getText().toString().trim()
            if (content.isEmpty()) return@setOnClickListener

            val lines =
                content.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            for (line in lines) {
                val checkBox = CheckBox(requireContext())
                checkBox.text = line
               checkBox.setTextColor( com.google.android.material.R.attr.colorOnBackground)
                checkBox.setPadding(8, 8, 8, 8)
                todoContainer.addView(checkBox)
            }
            binding.contentEditText.setVisibility(View.GONE)
            todoContainer.visibility = View.VISIBLE
            binding.saveButton.setOnClickListener {
                saveNote()
            }
        }
        val paragraphFormatter = binding.paragraphFormatter

        paragraphFormatter.setOnClickListener {
            paragraphFormatter.setColorFilter(androidx.appcompat.R.attr.colorPrimary)

            val content = binding.contentEditText.text.toString().trim()
            if (content.isEmpty()) return@setOnClickListener

            alignmentIndex = (alignmentIndex + 1) % alignments.size
            binding.contentEditText.gravity = alignments[alignmentIndex]  // Set new alignment

            // Change icon based on alignment
            val newDrawable = when (alignmentIndex) {
                0 -> R.drawable.segment_para
                1 -> R.drawable.align_center
                else ->  R.drawable.segment1_para
            }
            paragraphFormatter.setImageResource(newDrawable)  // Correct way to update an ImageView
        }

        binding.numberingButton.setOnClickListener {  }
    }
    private fun saveNote() {
        val title = binding.titleEditText.text.toString().trim()
        binding.time.text = System.currentTimeMillis().toString()

        val noteText = binding.contentEditText.text.toString().trim()

        if (title.isEmpty() || noteText.isEmpty()) {
            Toast.makeText(requireContext(),"Title and Content cannot be empty!",Toast.LENGTH_SHORT).show()
            return
        }
        val newNote = Note(title = title, content = noteText, timeStamp = System.currentTimeMillis())
        Log.d(TAG,"$newNote")
        noteViewModel.insert(newNote)
        Toast.makeText(requireContext(),"Note saved",Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_addNoteFragment_to_noteFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}