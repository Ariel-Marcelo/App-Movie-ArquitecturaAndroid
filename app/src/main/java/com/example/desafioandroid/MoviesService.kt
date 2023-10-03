package com.example.desafioandroid

import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=1f54bd990f1cdfb230adb312546d765d&language=pt-BR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getMovies(): List<MovieResult>
}