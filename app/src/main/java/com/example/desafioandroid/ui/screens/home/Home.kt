package com.example.desafioandroid.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.MoviesRepository
import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.remote.ServerMovie
import com.example.desafioandroid.ui.theme.DesafioAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(moviesRepository: MoviesRepository) {
    DesafioAndroidTheme {
        // Normalmente en el view model se crearia un factory si estamos con xml
        // Estamos creando un viewModel con el mvoieDao
        val viewModel: HomeViewModel = viewModel{HomeViewModel(moviesRepository)}


        val state by viewModel.state.collectAsState()
        // val viewModel: MainViewModel by viewModel()
        //val state = viewModel.state.observeAsState(HomeViewModel.UiState())
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(text = "Movies") })
                }
            ) { padding ->
                if (state.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (state.movies.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.padding(padding),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(state.movies) {
                            MovieItem(it, { viewModel.onMovieClick(it) })
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        if (movie.favorite) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
        }
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
        )

        Text(
            text = movie.title,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}