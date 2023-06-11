package com.example.movieapp.feature.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.app.BaseViewModel
import com.example.movieapp.data.network.Result
import com.example.movieapp.feature.home.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

private val _movies =  MutableLiveData<List<Pair<String, List<Movie>>>>()
val movies: LiveData<List<Pair<String, List<Movie>>>>
get() = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
    get() = _error
init {
    getMovies()
}

    private fun getMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = repository.getMovies()) {
                    is Result.Success ->{
                        print("Response: ${result.data}")
                        _movies.postValue(result.data)
                    }
                    is Result.Error -> {
                        _error.postValue(result.exception.message!!)
                    }
                }
            }

        }
    }

}