package com.codingtester.mindnotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtester.mindnotes.feature_note.domain.model.Note
import com.codingtester.mindnotes.feature_note.domain.use_case.NoteUseCases
import com.codingtester.mindnotes.feature_note.domain.utils.NoteOrder
import com.codingtester.mindnotes.feature_note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentDeletedNote: Note? = null

    private var getNoteJob: Job? = null

    init {
        getAllNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(notesEvent: NotesEvent) {
        when(notesEvent) {
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class == notesEvent.noteOrder::class &&
                        state.value.noteOrder.orderType == notesEvent.noteOrder.orderType) {
                    return
                }
                getAllNotes(notesEvent.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(notesEvent.note)
                    recentDeletedNote = notesEvent.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentDeletedNote ?: return@launch)
                    recentDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAllNotes(noteOrder: NoteOrder) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCases.getNotes.invoke(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}