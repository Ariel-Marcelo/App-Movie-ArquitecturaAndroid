package com.example.desafioandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.desafioandroid.ui.theme.DesafioAndroidTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
//GSONConverterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesafioAndroidTheme {


                val movies = remember { mutableStateOf<List<ServerMovie>>(emptyList()) }

                LaunchedEffect(key1 = false) {
                    val response = Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(MoviesService::class.java)
                        .getMovies()
                        .results
                    movies.value = response
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            TopAppBar(title = { Text(text = "Movies") })
                        }
                    ){ padding ->
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(180.dp),
                            modifier = Modifier.padding(padding),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            items(movies.value) { movie ->
                                Column {
                                    AsyncImage(
                                        model = "https://image.tmdb.org/t/p/w5185/${movie.poster_path}",
                                        contentDescription = movie.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(2 / 3f)
                                    )
                                    Text(
                                        text = movie.title,
                                        modifier = Modifier.padding(16.dp),
                                        maxLines = 1
                                    )
                                }

                            }
                        }
                    }


                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DesafioAndroidTheme {
        Greeting("Android")
    }
}