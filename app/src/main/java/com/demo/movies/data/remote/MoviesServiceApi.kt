package com.demo.movies.data.remote

import com.demo.movies.data.entities.Movie
import retrofit2.Response
import retrofit2.http.GET

interface  MoviesServiceApi {

    companion object {
        const val BASE_URL = "https://api.androidhive.info/"
    }

    @GET ("json/movies.json")
    suspend fun getAllMovies() : Response<List<Movie>>

}