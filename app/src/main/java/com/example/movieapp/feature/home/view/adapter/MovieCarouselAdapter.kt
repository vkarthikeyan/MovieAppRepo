package com.example.movieapp.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieCarouselItemBinding
import com.example.movieapp.feature.home.model.Movie

class MovieCarouselAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MovieCarouselAdapter.MovieCarouselViewHolder>() {
    class MovieCarouselViewHolder(val binding: MovieCarouselItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCarouselViewHolder {
        val binding = DataBindingUtil.inflate<MovieCarouselItemBinding>(LayoutInflater.from(parent.context),
            R.layout.movie_carousel_item,parent,false)
        return MovieCarouselViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}