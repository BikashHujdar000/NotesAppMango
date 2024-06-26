package com.example.notes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Model.NoteRequest
import com.example.notes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) :ViewModel()
{
  val notesLiveData get() = noteRepository.noteLiveData
  val statusLiveData get() = noteRepository.statusLiveData


  fun getnotes()
  {
    viewModelScope.launch {
      noteRepository.getNotes()
    }

  }
  fun createNotes(noteRequest: NoteRequest)
  {
    viewModelScope.launch {
      noteRepository.creteNotes(noteRequest)
    }
  }
 fun deleteNotes(noteId:String)
 {
   viewModelScope.launch {
     noteRepository.deleteNotes(noteId)
    }
 }
  fun updateNotes(noteId: String,noteRequest: NoteRequest)
  {
    viewModelScope.launch  {
      noteRepository.updateNote(noteId,noteRequest)
    }
  }
}