package com.example.cinemate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<Favorites>

    @Query("SELECT * FROM favorites WHERE movie_id = :movieId")
    fun getFavoritesForMovie(movieId: Int): List<Favorites>

    @Insert
    fun insertFavorite(favorite: Favorites)

    @Delete
    fun deleteFavorite(favorite: Favorites)
}
