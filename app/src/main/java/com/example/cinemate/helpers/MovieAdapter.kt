package com.example.cinemate.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cinemate.R
import com.example.cinemate.database.Movies
import com.example.cinemate.ui.MovieDetailsActivity

class MovieAdapter : BaseAdapter {

    lateinit var context: Context
    lateinit var movies: ArrayList<Movies>


    constructor(context: Context, movies: ArrayList<Movies>) : super(){
        this.context = context
        this.movies = movies
    }
    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.movie_grid_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val movie = movies[position]
        view.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("movieId", movie.movieId)
            bundle.putString("movieName", movie.movieName)
            bundle.putString("movieGenre", movie.movieGenre)
            bundle.putString("movieDirector", movie.movieDirector)
            bundle.putInt("movieYear", movie.movieYear)

            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movieBundle", bundle)
            context.startActivity(intent)

        }


        viewHolder.movieNameTextView.text = movie.movieName
        viewHolder.movieYearTextView.text = movie.movieYear.toString()

        return view
    }

    private class ViewHolder(view: View) {
        val movieNameTextView: TextView = view.findViewById(R.id.movie_name)
        val movieYearTextView: TextView = view.findViewById(R.id.movie_year)
    }
}