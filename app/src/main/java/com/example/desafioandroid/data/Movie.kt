package com.example.desafioandroid.data

import com.example.desafioandroid.data.local.LocalMovie

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val favorite: Boolean
) {
    fun toLocalMovie() = LocalMovie(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        favorite = favorite

    )
}
