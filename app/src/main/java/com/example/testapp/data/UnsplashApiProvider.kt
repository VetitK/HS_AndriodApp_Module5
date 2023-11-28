package com.example.testapp.data

import android.util.Log
import com.example.testapp.data.cb.UnsplashDetailResult
import com.example.testapp.data.cb.UnsplashResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


private const val BASE_URL = "https://api.unsplash.com/"


class UnsplashApiProvider {
    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<UnsplashApi>()
    }
    fun fetchImages(cb: UnsplashResult) {
        retrofit.fetchPhotos().enqueue(object : Callback<List<UnsplashPhoto>> {
            override fun onResponse(
                call: Call<List<UnsplashPhoto>>,
                response: Response<List<UnsplashPhoto>>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }

    fun fetchDetailsOnPhoto(id: String , cb: UnsplashDetailResult) {
        retrofit.fetchDetails(id).enqueue(object : Callback<UnsplashPhotoDetails> {
            override fun onResponse(
                call: Call<UnsplashPhotoDetails>,
                response: Response<UnsplashPhotoDetails>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<UnsplashPhotoDetails>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }
}
