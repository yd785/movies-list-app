package com.demo.movies.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movies", indices = [Index(value = ["title"],
    unique = true)]
)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String,
    val image: String,
    val rating: String,
    val releaseYear: Int,
    val genre: List<String>?
)
