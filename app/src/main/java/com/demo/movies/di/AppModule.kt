@file:Suppress("DEPRECATION")

package com.demo.movies.di

import android.content.Context
import com.demo.movies.data.local.AppDatabase
import com.demo.movies.data.local.MovieDao
import com.demo.movies.data.remote.MovieRemoteDataSource
import com.demo.movies.data.remote.MoviesServiceApi
import com.demo.movies.data.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MoviesServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.movieDao()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): MoviesServiceApi = retrofit.create(MoviesServiceApi::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(movieService: MoviesServiceApi) = MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MovieRemoteDataSource,
                          localDataSource: MovieDao) =
        MovieRepository(remoteDataSource, localDataSource)

}