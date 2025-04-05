package com.example.notesapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val  content: String,
    val timeStamp: Long = System.currentTimeMillis()
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt()?: -1,
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readLong()?: System.currentTimeMillis()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(content)
        dest.writeLong(timeStamp)
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note = Note(parcel)


        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
