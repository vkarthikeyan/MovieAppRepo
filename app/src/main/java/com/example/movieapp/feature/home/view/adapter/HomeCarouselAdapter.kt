package com.example.movieapp.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.HomeCarouselItemBinding
import com.example.movieapp.feature.home.model.Movie

/**
 * Adapter class for listing the movies based on the Genres
 * @param moviesList - List of movies TODO: here change the type to Map<String, List<Movie>> where the key is Genre type
 */
class HomeCarouselAdapter(private val moviesList: List<Pair<String, List<Movie>>>): RecyclerView.Adapter<HomeCarouselAdapter.HomeCarouselViewHolder>() {
    class HomeCarouselViewHolder(private val binding:HomeCarouselItemBinding ): RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Pair<String, List<Movie>>) {
            binding.MovieCategory.text = movies.first
            val adapter = MovieCarouselAdapter(movies.second)
            binding.ChildRV.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val binding = DataBindingUtil.inflate<HomeCarouselItemBinding>(LayoutInflater.from(parent.context),
            R.layout.home_carousel_item,parent,false)
        return HomeCarouselViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        //here get and pass the genre based movie list
        holder.bind(moviesList[position])
    }
}