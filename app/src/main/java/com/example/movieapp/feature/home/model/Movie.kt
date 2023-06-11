package com.example.movieapp.feature.home.model

data class Movie(
    val __v: Int,
    val _id: String,
    val description: String,
    val director: List<String>,
    val genre: List<String>,
    val id: String,
    val image: List<List<String>>,
    val imdbid: String,
    val rank: Int,
    val rating: String,
    val thumbnail: String,
    val title: String,
    val writers: List<String>,
    val year: Int
)