package com.example.desafioandroid.data.remote

import com.example.desafioandroid.data.remote.ServerMovie

data class MovieResult(
    val page: Int,
    val results: List<ServerMovie>,
    val total_pages: Int,
    val total_results: Int
)