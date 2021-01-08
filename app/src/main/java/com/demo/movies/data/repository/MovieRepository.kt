package com.demo.movies.data.repository

import com.demo.movies.data.local.MovieDao
import com.demo.movies.data.remote.MovieRemoteDataSource
import com.demo.movies.utils.DataAccessOperations
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun fetchMovies() = DataAccessOperations.getAndSaveDataOperation(
        dbQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCall = { localDataSource.insertAll(it) }
    )

    fun getMovie(id: Int) = DataAccessOperations.getDataOperation { localDataSource.getMovie(id) }

    fun getMovies() = DataAccessOperations.getDataOperation { localDataSource.getAllMovies() }


}