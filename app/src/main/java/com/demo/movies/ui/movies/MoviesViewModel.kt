package com.demo.movies.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.demo.movies.data.repository.MovieRepository

class MoviesViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
): ViewModel() {

    val movies = repository.getMovies()
}