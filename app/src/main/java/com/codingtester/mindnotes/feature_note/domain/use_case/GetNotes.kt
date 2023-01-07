package com.codingtester.mindnotes.feature_note.domain.use_case

import com.codingtester.mindnotes.feature_note.domain.model.Note
import com.codingtester.mindnotes.feature_note.domain.repository.NoteRepository
import com.codingtester.mindnotes.feature_note.domain.utils.NoteType
import com.codingtester.mindnotes.feature_note.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteType: NoteType = NoteType.Title(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getAllNotes().map { notes ->
            when(noteType.orderType) {
                is OrderType.Ascending -> {
                    when(noteType) {
                        is NoteType.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteType.Date -> notes.sortedBy { it.timestamp }
                        is NoteType.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when(noteType) {
                        is NoteType.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteType.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteType.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }

}