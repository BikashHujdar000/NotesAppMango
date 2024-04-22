package com.example.notes.ViewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Model.UserRequest
import com.example.notes.Model.UserResponse
import com.example.notes.Utils.NetworkResults
import com.example.notes.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(private  val userRepository: UserRepository): ViewModel() {

    // writting function which will be called from the fragments


     val userResponseLiveData :LiveData<NetworkResults<UserResponse>>
     get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){

        viewModelScope.launch {

          userRepository.registerUser(userRequest)

        }


    }
    fun loginUser(userRequest: UserRequest){

        viewModelScope.launch {

            userRepository.loginUser(userRequest)

        }


    }

    fun ValidateCredentials(username:String,emailAddress:String,password :String,isLogin:Boolean):Pair<Boolean,String>
    {
//    var result =Pair(true," ")
//        if (! isLogin && username==null)
//        {
//            result = Pair(false,"Please Enter Username ")
//
//        }
//        else if (password==null)
//        {
//            result= Pair(false,"Please Enter Password")
//        }
//        else if (emailAddress==null)
//        {
//            result = Pair(false ,"Please Enter EmailAddress")
//        }
//// removed matcher fot the email address for now to test
//
//        else if (password != null && password.length<=5) {
//            result = Pair(false, "Password Must be greater than 5 characters")
//        }
//
//
//      return  result

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || (!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

}