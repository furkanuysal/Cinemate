package com.example.cinemate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movies>

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): Movies

    @Transaction
    @Query("SELECT * FROM movies")
    fun getMoviesAndFavorites(): List<MoviesAndFavorites>

    @Insert
    fun insertMovie(movie: Movies)

    @Update
    fun updateMovie(movie: Movies)

    @Delete
    fun deleteMovie(movie: Movies)
}
