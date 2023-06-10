package com.example.cinemate.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cinemate.R
import com.example.cinemate.database.Movies

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
        viewHolder.movieNameTextView.text = movie.movieName
        viewHolder.movieYearTextView.text = movie.movieYear.toString()

        return view
    }

    private class ViewHolder(view: View) {
        val movieNameTextView: TextView = view.findViewById(R.id.movie_name)
        val movieYearTextView: TextView = view.findViewById(R.id.movie_year)
    }
}