package com.example.desafioandroid.data.remote

import com.example.desafioandroid.data.remote.MovieResult
import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=4f0795e7f061b7288fef06b63ae1656e")
    suspend fun getMovies(): MovieResult
}