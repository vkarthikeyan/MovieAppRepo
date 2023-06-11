package com.example.movieapp.data.repository

import com.example.movieapp.data.model.ErrorResponse
import com.example.movieapp.data.network.MovieAppApi
import com.example.movieapp.data.network.ServiceGenerator
import com.example.movieapp.data.network.apiCall
import com.example.movieapp.feature.home.model.Movie
import com.example.movieapp.data.network.Result as MovieAppResult

class MovieAppRepository(private val api: MovieAppApi) {
    companion object {
        fun getInstance() = MovieAppRepository(api = ServiceGenerator.create())
    }

    suspend fun getMovies() = apiCall { getMovieList() }

    private suspend fun getMovieList(): MovieAppResult<List<Pair<String, List<Movie>>>> {
        val response = api.getMovies()
        if (response.isSuccessful) {
            response.body()?.let { movieList ->
                val allGenres = movieList.flatMap { it.genre }.toSet()
                val requiredList: MutableList<Pair<String, List<Movie>>> = mutableListOf<Pair<String,List<Movie>>>()
                allGenres.forEach { genreString ->
                    val groupedList: List<Movie> = movieList.filter { it -> it.genre.contains(genreString) }
                    requiredList.add(Pair(genreString,groupedList))
                }
                return MovieAppResult.Success(requiredList)
            }
        }
        return MovieAppResult.Error(
            Exception(),
            ErrorResponse(response.errorBody().toString(), message = response.message())
        )
    }

}