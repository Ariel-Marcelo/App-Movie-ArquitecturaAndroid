package com.example.desafioandroid.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.MoviesRepository
import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.local.toMovie
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.ServerMovie
import com.example.desafioandroid.data.remote.toLocalMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// EN los view models no debemos tener acceso al contexto??
class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    // tenemos el mutableSateFlow
    // El stateFlow
    // Otra forma de hacerlo, mas rendimiento, tienen una funci{on update
    //private val _state = MutableLiveData(UiState())
    //val state: LiveData<UiState> = _state // getter publico, no se puede modificar desde fuera
    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.requestMovies()

            moviesRepository.movies.collect { movies ->
                _state.value = UiState(
                    movies = movies
                )
            }
        }
    }

    // Exis
    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            // nota que usamos el favorite porque los datos son inmutalbes
            // no valdría si hicieramos movie.favorite = !movie.favorite
            moviesRepository.updateMovie(movie)
        }
    }

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val loading: Boolean = false
    )
}