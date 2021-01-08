package com.demo.movies.data.remote

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieServiceApi: MoviesServiceApi
): DataSource() {

    suspend fun getMovies() = getResult { movieServiceApi.getAllMovies() }
}