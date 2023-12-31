package com.example.desafioandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.room.Room
import com.example.desafioandroid.data.local.LocalDataSource
import com.example.desafioandroid.data.MoviesRepository
import com.example.desafioandroid.data.remote.RemoteDataSource
import com.example.desafioandroid.data.local.MoviesDatabase
import com.example.desafioandroid.ui.screens.home.Home
import com.example.desafioandroid.ui.screens.home.HomeViewModel

//GSONConverterFactory

class MainActivity : ComponentActivity() {

    val viewModel: HomeViewModel by viewModels()


    // Esto seria mejor con inyección de dependencias
    lateinit var db: MoviesDatabase

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).build()

        val moviesRepository: MoviesRepository = MoviesRepository(
            localDataSource = LocalDataSource(db.movieDao()),
            remoteDataSource = RemoteDataSource()
        )

        setContent {
            Home(moviesRepository)
        }
    }

}