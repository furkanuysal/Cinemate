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

    @Query("DELETE FROM favorites WHERE movie_Id = :movieId")
    fun deleteFavoriteByMovieId(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE movie_Id = :movieId LIMIT 1)")
    suspend fun isFavorite(movieId: Int): Boolean

}
