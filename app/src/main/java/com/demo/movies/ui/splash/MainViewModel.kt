package com.demo.movies.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.demo.movies.data.repository.MovieRepository

class MainViewModel @ViewModelInject constructor (
    private val repository: MovieRepository
    ) : ViewModel() {

    val loadingStatus = repository.fetchMovies().map { it.status }
}