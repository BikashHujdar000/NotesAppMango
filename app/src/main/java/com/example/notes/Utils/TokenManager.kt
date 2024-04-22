package com.example.notes.Utils

import android.content.Context
import com.example.notes.Utils.Constants.PREf_TOKEN_FILE
import com.example.notes.Utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


// we use this class just for storing our token used in Authorization

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs = context.getSharedPreferences(PREf_TOKEN_FILE,Context.MODE_PRIVATE)
    fun saveToken (token :String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun getToken (): String? {
        return  prefs.getString(USER_TOKEN,null)
    }

}