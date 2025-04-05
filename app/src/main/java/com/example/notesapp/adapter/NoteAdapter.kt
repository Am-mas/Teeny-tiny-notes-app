package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.ui.NoteDetailFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(
    private val fragmentManager: FragmentManager
): ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    class NoteViewHolder(private val binding: ItemNoteBinding ): RecyclerView.ViewHolder(binding.root)  {
        private val cardLayout = binding.cardNotesLayout
        fun bind(note:Note, fragmentManager: FragmentManager) {
            val timestamp = note.timeStamp
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = Date(timestamp)
            val formattedDate = dateFormat.format(date)
            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content
            binding.noteTime.text = formattedDate
            cardLayout.setOnClickListener{
                val bottomSheetDialog = NoteDetailFragment.newInstance(note)
                bottomSheetDialog.show(fragmentManager, "NoteDetailFragment")
            }

        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem.id ==newItem.id

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), fragmentManager)
    }

}