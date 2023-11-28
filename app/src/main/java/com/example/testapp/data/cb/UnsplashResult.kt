package com.example.testapp.data.cb

import com.example.testapp.data.UnsplashPhoto
import com.example.testapp.data.UnsplashPhotoDetails

interface UnsplashResult {

     fun onDataFetchedSuccess(images: List<UnsplashPhoto>)

     fun onDataFetchedFailed()
}

interface UnsplashDetailResult {
    fun onDataFetchedSuccess(detail: UnsplashPhotoDetails)

    fun onDataFetchedFailed()
}