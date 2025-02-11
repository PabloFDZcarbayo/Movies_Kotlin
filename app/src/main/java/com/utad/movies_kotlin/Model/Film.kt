package com.utad.movies_kotlin.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Long,

    @SerializedName("original_title")
    val title: String,

    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    val isFavourite : Boolean
) : Parcelable {

    }
