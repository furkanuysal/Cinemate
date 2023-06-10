package com.example.cinemate.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey(autoGenerate = true) val movieId: Int,
    @ColumnInfo(name = "movie_name") val movieName: String,
    @ColumnInfo(name = "movie_genre") val movieGenre: String,
    @ColumnInfo(name = "movie_director") val movieDirector: String,
    @ColumnInfo(name = "movie_year") val movieYear: Int
){
    constructor(movieName: String, movieGenre: String, movieDirector: String, movieYear: Int) :
            this(0, movieName, movieGenre, movieDirector, movieYear)
}