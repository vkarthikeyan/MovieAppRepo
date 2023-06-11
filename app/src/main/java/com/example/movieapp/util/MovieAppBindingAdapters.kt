package com.example.movieapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object MovieAppBindingAdapters {
    @BindingAdapter(value = ["thumbnail_image"])
    @JvmStatic
    fun loadThumbnail(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url)
            .into(imageView)
    }
}