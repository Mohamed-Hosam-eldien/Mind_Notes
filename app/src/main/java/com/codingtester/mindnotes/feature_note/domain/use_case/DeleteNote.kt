package com.codingtester.mindnotes.feature_note.domain.use_case

import com.codingtester.mindnotes.feature_note.domain.model.Note
import com.codingtester.mindnotes.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}