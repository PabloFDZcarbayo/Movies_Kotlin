package com.utad.movies_kotlin.Adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.utad.movies_kotlin.Model.Film
import com.utad.movies_kotlin.R

class Film_Adapter(private val films: MutableList<Film>, private val onClickListener: (Film) -> Unit) :
    RecyclerView.Adapter<Film_ViewHolder>() {


        fun newFilms(newFilms: List<Film>) {
            val oldSize = films.size
            films.clear()
            notifyItemRangeRemoved(0, oldSize)
            films.addAll(newFilms)
            notifyItemRangeInserted(0, newFilms.size)
        }



    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): Film_ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return Film_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Film_ViewHolder, position: Int) {
        holder.bind(films[position], onClickListener)
    }

    override fun getItemCount(): Int = films.size
}