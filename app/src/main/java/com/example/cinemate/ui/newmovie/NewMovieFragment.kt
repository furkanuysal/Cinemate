package com.example.cinemate.ui.newmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cinemate.R
import com.example.cinemate.database.Movies
import com.example.cinemate.database.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewMovieFragment : Fragment() {

    private lateinit var buttonAddMovie: Button
    private lateinit var editTextMovieName: EditText
    private lateinit var editTextMovieGenre: EditText
    private lateinit var editTextMovieDirector: EditText
    private lateinit var editTextMovieYear: EditText

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_newmovie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddMovie = view.findViewById(R.id.buttonAddMovie)
        editTextMovieName = view.findViewById(R.id.editTextMovieName)
        editTextMovieGenre = view.findViewById(R.id.editTextMovieGenre)
        editTextMovieDirector = view.findViewById(R.id.editTextMovieDirector)
        editTextMovieYear = view.findViewById(R.id.editTextMovieYear)

        dbHelper = DatabaseHelper.getInstance(requireContext())

        buttonAddMovie.setOnClickListener {
            val movieName = editTextMovieName.text.toString()
            val movieGenre = editTextMovieGenre.text.toString()
            val movieDirector = editTextMovieDirector.text.toString()
            val movieYear = editTextMovieYear.text.toString().toInt()

            val newMovie = Movies(movieName, movieGenre, movieDirector, movieYear)

            insertMovieToDatabase(newMovie)
        }
    }

    private fun insertMovieToDatabase(movie: Movies) {
        GlobalScope.launch(Dispatchers.IO) {
            dbHelper.movieDao().insertMovie(movie)
        }

        requireActivity().supportFragmentManager.popBackStack()
    }
}
