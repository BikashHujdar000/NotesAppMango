package com.example.notes.Api

import com.example.notes.Model.NoteRequest
import com.example.notes.Model.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NoteApi {

    @GET("/notes")
    suspend fun getNotes():Response<List<NoteResponse>>
    @POST("/notes")
    suspend fun createNotes(@Body noteRequest: NoteRequest):Response<NoteResponse>
    @PUT("/notes/{noteId}")
    suspend fun updateNotes(@Path("noteId") noteId :String, @Body noteRequest: NoteRequest):Response<NoteRequest>
    @DELETE("/notes/{noteId}")
    suspend fun deleteNotes(@Path("noteId") noteId: String):Response<NoteResponse>




}