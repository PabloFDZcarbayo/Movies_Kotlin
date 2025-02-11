package com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO

import android.icu.text.CaseMap.Title
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.Fav_Film_Entitie
import com.utad.movies_kotlin.Model.Film

@Dao
interface Film_DAO {

    @Insert
    suspend fun newFavourite(film: Fav_Film_Entitie)

    @Delete
    suspend fun deleteFavourite(film: Fav_Film_Entitie)

    @Query ("SELECT * FROM `Favourites Films` WHERE title = :title")
    suspend fun getFavourite(title: String): Fav_Film_Entitie

    @Query ("SELECT * FROM `Favourites Films`")
    suspend fun getFavouritesList(): MutableList<Film>

}