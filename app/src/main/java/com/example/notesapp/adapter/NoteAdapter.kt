package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteBinding
import com.example.notesapp.model.Note

class NoteAdapter(private val onNoteClick: (Note)-> Unit): ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {
    class NoteViewHolder(private val binding: ItemNoteBinding ): RecyclerView.ViewHolder(binding.root)  {
        fun bind(note:Note, onNoteClick: (Note) -> Unit) {
            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content
            binding.root.setOnClickListener { onNoteClick(note) }
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
        holder.bind(getItem(position), onNoteClick)
    }

}