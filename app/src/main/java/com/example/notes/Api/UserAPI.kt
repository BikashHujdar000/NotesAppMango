package com.example.notes.Api

import com.example.notes.Model.UserRequest
import com.example.notes.Model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/users/signup")
    suspend fun signUp( @Body userRequest: UserRequest): Response<UserResponse>


    @POST("/users/signin")

    suspend fun signInp( @Body userRequest: UserRequest): Response<UserResponse>
}