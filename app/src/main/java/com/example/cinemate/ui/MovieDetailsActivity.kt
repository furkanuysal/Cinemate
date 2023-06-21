package com.example.cinemate.ui

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinemate.R
import com.example.cinemate.database.DatabaseHelper
import com.example.cinemate.database.Favorites
import com.example.cinemate.database.FavoritesDao
import com.example.cinemate.database.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var favoritesDao: FavoritesDao
    private lateinit var movieDao: MovieDao


    private lateinit var movieNameTextView: TextView
    private lateinit var movieDirectorTextView: TextView
    private lateinit var movieYearTextView: TextView
    private lateinit var movieGenreTextView: TextView

    private lateinit var buttonAddToFavorites: Button
    private lateinit var buttonDeleteMovie: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movie_detail)

        favoritesDao = DatabaseHelper.getInstance(application).favoriteDao()
        movieDao = DatabaseHelper.getInstance(application).movieDao()

        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites)
        buttonDeleteMovie = findViewById(R.id.buttonDeleteMovie)

        movieNameTextView = findViewById(R.id.movieNameTextView)
        movieDirectorTextView = findViewById(R.id.movieDirectorTextView)
        movieGenreTextView = findViewById(R.id.movieGenreTextView)
        movieYearTextView = findViewById(R.id.movieYearTextView)

        val bundle = intent.getBundleExtra("movieBundle")
        if (bundle != null) {

            val movieName = bundle.getString("movieName")
            val movieGenre = bundle.getString("movieGenre")
            val movieDirector = bundle.getString("movieDirector")
            val movieYear = bundle.getInt("movieYear")
            val movieId = bundle.getInt("movieId")

            lifecycleScope.launch {
                val isFavorite = withContext(Dispatchers.IO){
                    favoritesDao.isFavorite(movieId)
                }
                setButtonLabel(isFavorite)
            }

            movieNameTextView.text = movieName
            movieDirectorTextView.text = movieDirector
            movieGenreTextView.text = movieGenre
            movieYearTextView.text = movieYear.toString()

            buttonAddToFavorites.setOnClickListener {
                toggleFavoriteStatus(movieId)
            }
            buttonDeleteMovie.setOnClickListener{
                removeMovieCompletely(movieId)
                finish()
            }
        }
    }

    private fun toggleFavoriteStatus(movieId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val isFavorite = withContext(Dispatchers.IO) {
                favoritesDao.isFavorite(movieId)
            }

            if (isFavorite) {
                removeFromFavorites(movieId)
                buttonAddToFavorites.text = "Add to Favorites"
            } else {
                addToFavorites(movieId)
                buttonAddToFavorites.text = "Remove from Favorites"
            }
        }
    }

    private fun addToFavorites(movieId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val favorites = Favorites(movieId)
            favoritesDao.insertFavorite(favorites)
            showToast("Movie added to favorites")

        }
    }

    private fun removeFromFavorites(movieId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            favoritesDao.deleteFavoriteByMovieId(movieId)
            showToast("Movie removed from favorites")
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonLabel(isFavorite: Boolean) {
        if (isFavorite) {
            buttonAddToFavorites.text = "Remove from Favorites"
        } else {
            buttonAddToFavorites.text = "Add to Favorites"
        }
    }

    private fun removeMovieCompletely(movieId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            favoritesDao.deleteFavoriteByMovieId(movieId)
            movieDao.deleteMovieByMovieId(movieId)
            showToast("Movie removed completely")
        }
    }

}
