package com.demo.movies.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.demo.movies.data.entities.Movie
import com.demo.movies.data.repository.MovieRepository
import com.demo.movies.utils.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn


class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _movie = _id.switchMap { id ->
        repository.getMovie(id)
    }

    val movie: LiveData<Resource<Movie>> = _movie

    fun start(id: Int) {
        _id.value = id
    }
}