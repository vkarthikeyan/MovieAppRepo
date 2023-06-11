package com.example.movieapp.data.network

import com.example.movieapp.feature.home.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface MovieAppApi {

    @GET(".")
    suspend fun getMovies(): Response<List<Movie>>
}