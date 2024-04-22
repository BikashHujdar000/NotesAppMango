package com.example.notes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.notes.Api.UserAPI

import com.example.notes.Model.UserRequest
import com.example.notes.Model.UserResponse

import com.example.notes.Utils.Constants.TAG
import com.example.notes.Utils.NetworkResults
import org.json.JSONObject
import retrofit2.Response

import javax.inject.Inject
import kotlin.math.log

class UserRepository @Inject constructor ( private  val userAPI: UserAPI) {

    private val _userResponseLiveData= MutableLiveData<NetworkResults<UserResponse>>()
    val userResponseLiveData:LiveData<NetworkResults<UserResponse>>
    get()= _userResponseLiveData



    suspend fun  registerUser(userRequest: UserRequest)
    {

         // setting up for progress bar
        _userResponseLiveData.postValue(NetworkResults.Loading())
        val response = userAPI.signUp(userRequest)
        handleResponse(response)
    }

    suspend fun  loginUser(userRequest: UserRequest)
     {
         _userResponseLiveData.postValue(NetworkResults.Loading())
        val  response = userAPI.signInp(userRequest)
         Log.d("Data ", response.toString());
     handleResponse(response)
     }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResults.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

            _userResponseLiveData.postValue(NetworkResults.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResults.Error("Something went wrong"))
        }
    }



}