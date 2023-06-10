package com.example.cinemate.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey(autoGenerate = true) val favoriteId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
){
    constructor(movieId: Int) : this(0, movieId)
}

