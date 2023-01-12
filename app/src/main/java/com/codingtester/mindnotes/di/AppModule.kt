package com.codingtester.mindnotes.di

import android.app.Application
import androidx.room.Room
import com.codingtester.mindnotes.feature_note.data.data_source.NoteDatabase
import com.codingtester.mindnotes.feature_note.data.repository.NoteRepositoryImpl
import com.codingtester.mindnotes.feature_note.domain.repository.NoteRepository
import com.codingtester.mindnotes.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDatabase: NoteDatabase
    ): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        noteRepository: NoteRepository
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }

}