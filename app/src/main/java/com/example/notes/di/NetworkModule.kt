package com.example.notes.di

import com.example.notes.Api.AuthInterceptors
import com.example.notes.Api.NoteApi
import com.example.notes.Api.UserAPI
import com.example.notes.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    // on approaching for same function o adding clents 1
    //1.returning retrofit builder instead of retrofit and remove builde ()
    //

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            //.build()
    }

    @Singleton
    @Provides
    fun provideOkHTTPClient(authInterceptors: AuthInterceptors) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptors).build()
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder):UserAPI{
       return  retrofitBuilder.build().create(UserAPI::class.java)
    }

@Singleton
@Provides
    fun providesNotesApi(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient):NoteApi{
         return  retrofitBuilder
             .client(okHttpClient)
             .build().create(NoteApi::class.java)
    }
}