package com.example.desafioandroid.data

import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.toMovie
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalDataSource (dao: MoviesDao) {

    
}

class RemoteDataSource {
    suspend fun getMovies() : List<Movie> {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesService::class.java)
            .getMovies()
            .results
            .map { it.toMovie() }
    }
}
class MoviesRepository {


}