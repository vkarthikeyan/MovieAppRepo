package com.example.movieapp.app

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.repository.MovieAppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope

open class BaseViewModel: ViewModel() {
    val repository: MovieAppRepository = MovieAppRepository.getInstance()
    val viewModelScope = CoroutineScope(Dispatchers.IO + Job())
}