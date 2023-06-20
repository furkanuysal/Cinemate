package com.example.cinemate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cinemate.R
import com.example.cinemate.database.DatabaseHelper
import com.example.cinemate.database.MovieDao
import com.example.cinemate.database.Movies
import com.example.cinemate.helpers.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var movieDao: MovieDao
    private lateinit var dbHelper: DatabaseHelper

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieGridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        dbHelper = DatabaseHelper.getInstance(requireContext())
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
            val movies = withContext(Dispatchers.IO) {
                movieDao.getAllMovies()
            }
            movieAdapter.movies = movies as ArrayList<Movies>
            movieAdapter.notifyDataSetChanged()
        }
    }
}
