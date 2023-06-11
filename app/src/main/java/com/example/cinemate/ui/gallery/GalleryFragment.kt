package com.example.cinemate.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cinemate.R
import com.example.cinemate.database.DatabaseHelper
import com.example.cinemate.database.MovieDao
import com.example.cinemate.database.Movies
import com.example.cinemate.database.MoviesAndFavorites
import com.example.cinemate.databinding.FragmentGalleryBinding
import com.example.cinemate.helpers.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {

    private lateinit var movieDao: MovieDao
    private lateinit var dbHelper: DatabaseHelper

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieGridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        dbHelper = DatabaseHelper.getDatabase(requireContext())
        movieDao = dbHelper.movieDao()

        movieGridView = view.findViewById(R.id.gridViewMovies)
        setupGridView()
        loadMovies()

        return view
    }

    private fun setupGridView() {
        movieAdapter = MovieAdapter(requireContext(), ArrayList())
        movieGridView.adapter = movieAdapter
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            val moviesAndFavorites = withContext(Dispatchers.IO) {
                movieDao.getMoviesAndFavorites()
            }

            val favoriteMovies = moviesAndFavorites.filter { it.favorites != null && it.favorites.isNotEmpty() }
            val movies = favoriteMovies.map { it.movies }
            movieAdapter.movies = movies as ArrayList<Movies>
            movieAdapter.notifyDataSetChanged()
        }
    }


}