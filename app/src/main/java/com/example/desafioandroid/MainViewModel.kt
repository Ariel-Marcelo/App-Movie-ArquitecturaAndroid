package com.example.desafioandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {
    // tenemos el mutableSateFlow
    // El stateFlow
    // Otra forma de hacerlo, mas rendimiento, tienen una funci{on update
    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state // getter publico, no se puede modificar desde fuera

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                movies = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MoviesService::class.java)
                    .getMovies()
                    .results
            )

        }
    }

    // Exis
    fun onMovieClick(movie: ServerMovie) {
        val movies = _state.value?.movies?.toMutableList() ?: mutableListOf()
        movies.replaceAll { if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else movie }
    }

    data class UiState(
        val movies: List<ServerMovie> = emptyList(),
        val loading: Boolean = false
    )
}