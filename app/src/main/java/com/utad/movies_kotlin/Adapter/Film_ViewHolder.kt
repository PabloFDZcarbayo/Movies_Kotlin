package com.utad.movies_kotlin.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utad.movies_kotlin.Model.Film
import com.utad.movies_kotlin.databinding.ItemMovieBinding

class Film_ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(film: Film, onClickListener: (Film) -> Unit) {
        binding.movieTitle.text = film.title
        Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500/${film.posterPath}")
            .into(binding.imgMoviePoster)
        itemView.setOnClickListener { onClickListener(film) }


    }

}