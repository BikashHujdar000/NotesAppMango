package com.example.notes.Api

import com.example.notes.Utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptors  @Inject constructor() :Interceptor {
    @Inject
    lateinit var  tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {
       val request = chain.request().newBuilder()
        val token =tokenManager.getToken()
        request.addHeader("Authorization","Bearer $token")

        return  chain.proceed(request.build())


    }
}