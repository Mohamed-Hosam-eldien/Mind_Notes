package com.codingtester.mindnotes.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codingtester.mindnotes.feature_note.data.data_source.NoteDatabase
import com.codingtester.mindnotes.feature_note.data.repository.NoteRepositoryImpl
import com.codingtester.mindnotes.feature_note.domain.repository.NoteRepository
import com.codingtester.mindnotes.feature_note.domain.use_case.DeleteNote
import com.codingtester.mindnotes.feature_note.domain.use_case.GetNotes
import com.codingtester.mindnotes.feature_note.domain.use_case.NoteUseCases
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
    fun provideDatabase(app: Application): RoomDatabase {
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
            deleteNote = DeleteNote(noteRepository)
        )
    }

}