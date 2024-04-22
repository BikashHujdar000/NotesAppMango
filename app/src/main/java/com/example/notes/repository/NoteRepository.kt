package com.example.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.Api.NoteApi
import com.example.notes.Model.NoteRequest
import com.example.notes.Model.NoteResponse
import com.example.notes.Utils.NetworkResults
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor (private val noteApi: NoteApi) {

    private val _statusLiveData = MutableLiveData<NetworkResults<String>>()
    val statusLiveData: LiveData<NetworkResults<String>>
        get() = _statusLiveData

    private val _NoteLiveData = MutableLiveData<NetworkResults<List<NoteResponse>>>()
    val noteLiveData: LiveData<NetworkResults<List<NoteResponse>>>
        get() = _NoteLiveData


    suspend fun getNotes() {
        _NoteLiveData.postValue(NetworkResults.Loading())
        val response = noteApi.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _NoteLiveData.postValue(NetworkResults.Success(response.body()!!))

        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _NoteLiveData.postValue(NetworkResults.Error(errorObj.getString("message")))

        } else {
            _NoteLiveData.postValue(NetworkResults.Error("Something Went Wrong"))

        }
    }

    suspend fun creteNotes(noteRequest: NoteRequest) {
        _statusLiveData.postValue(NetworkResults.Loading())
        val response = noteApi.createNotes(noteRequest)

        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResults.Success("Note Created"))
        } else {
            _statusLiveData.postValue(NetworkResults.Error("Something went Wrong"))
        }
    }


    suspend fun deleteNotes(noteId: String) {
        _statusLiveData.postValue(NetworkResults.Loading())
        val response = noteApi.deleteNotes(noteId)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResults.Success("Note Deleted"))
        } else {
            _statusLiveData.postValue(NetworkResults.Error("Something went Wrong"))
        }
    }

    suspend fun  updateNote(noteId: String,noteRequest: NoteRequest)
    {
        _statusLiveData.postValue(NetworkResults.Loading())
        val response = noteApi.updateNotes(noteId,noteRequest)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResults.Success("Note Is updated"))
        } else {
            _statusLiveData.postValue(NetworkResults.Error("Something went Wrong"))
        }
    }

}











