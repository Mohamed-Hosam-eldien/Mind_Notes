package com.codingtester.mindnotes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingtester.mindnotes.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedPink, BabyBlue, RedOrange, Violet, LightGreen)
    }
}

class InvalidNoteException : Exception()
