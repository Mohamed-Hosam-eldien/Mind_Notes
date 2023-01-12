package com.codingtester.mindnotes.feature_note.domain.use_case

import com.codingtester.mindnotes.feature_note.domain.model.InvalidNoteException
import com.codingtester.mindnotes.feature_note.domain.model.Note
import com.codingtester.mindnotes.feature_note.domain.repository.NoteRepository

class AddNote(
    private val noteRepository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException()
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException()
        }
        noteRepository.insertNote(note)
    }
}