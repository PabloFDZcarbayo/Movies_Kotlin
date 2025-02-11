package com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Favourites Films", indices = [Index(value = ["title"], unique = true)])
data class Fav_Film_Entitie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val popularity: Double,
    val releaseDate: String,
    val backdropPath: String,
    val isFavourite: Boolean = false
) {
}