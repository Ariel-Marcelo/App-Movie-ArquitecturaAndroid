package com.example.desafioandroid.data.remote

import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.local.LocalMovie

data class ServerMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val favorite: Boolean
) {

}

fun ServerMovie.toLocalMovie() = LocalMovie(
    id = 0, // Asi la app genera su propio id porque la app tiene
    title = title,
    overview = overview,
    poster_path = poster_path,
    favorite = favorite
)

fun ServerMovie.toMovie() = Movie(
    id = 0, // Asi la app genera su propio id porque la app tiene
    title = title,
    overview = overview,
    poster_path = poster_path,
    favorite = favorite
)
