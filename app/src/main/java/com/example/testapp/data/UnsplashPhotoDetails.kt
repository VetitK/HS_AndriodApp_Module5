package com.example.testapp.data

import com.example.testapp.interfaces.CurrentUserCollection
import com.example.testapp.interfaces.Exif
import com.example.testapp.interfaces.Links
import com.example.testapp.interfaces.Location
import com.example.testapp.interfaces.Tag
import com.example.testapp.interfaces.Urls
import com.example.testapp.interfaces.User

data class UnsplashPhotoDetails(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollection>?,
    val description: String?,
    val downloads: Int?,
    val exif: Exif?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: Links?,
    val location: Location?,
    val public_domain: Boolean?,
    val tags: List<Tag>?,
    val updated_at: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
)