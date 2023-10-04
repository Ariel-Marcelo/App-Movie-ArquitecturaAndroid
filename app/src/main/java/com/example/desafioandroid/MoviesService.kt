package com.example.desafioandroid

import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=4f0795e7f061b7288fef06b63ae1656e")
    suspend fun getMovies(): MovieResult
}