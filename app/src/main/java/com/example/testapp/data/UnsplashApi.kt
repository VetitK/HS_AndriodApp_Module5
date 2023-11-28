package com.example.testapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "tAPZLbp6Wnlpjp5hMC8eCjr5AOPOo25atN9dftRExQE"

interface UnsplashApi {
    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashPhoto>>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("/photos/{id}")
    fun fetchDetails(@Path("id") id: String) :  Call<UnsplashPhotoDetails>
}