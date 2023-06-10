package com.example.cinemate.database

import androidx.room.Embedded
import androidx.room.Relation

data class MoviesAndFavorites(
    @Embedded val movies: Movies,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movie_id"
    )
    val favorites: Favorites
)
