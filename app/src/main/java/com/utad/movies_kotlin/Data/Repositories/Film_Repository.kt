package com.utad.movies_kotlin.Data.Repositories

import com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO.Film_DAO
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.Fav_Film_Entitie
import com.utad.movies_kotlin.Data.Remote.FilmService
import com.utad.movies_kotlin.Model.Film
import javax.inject.Inject

class Film_Repository @Inject constructor(
    private val filmService: FilmService,
    private val filmDao: Film_DAO
) {

    //Api Calls de NowPlaying
    suspend fun getNowPlayingFilms(apiKey: String): List<Film>? {
        return try {
            val response = filmService.getNowPlayingFilms(apiKey)
            return if (response.isSuccessful) response.body()?.results else null
        } catch (e: Exception) {
            null
        }
    }

    //Api Calls de Upcoming
    suspend fun getUpcomingFilms(apiKey: String): List<Film>? {
        return try {
            val response = filmService.getUpcomingFilms(apiKey)
            return if (response.isSuccessful) response.body()?.results else null
        } catch (e: Exception) {
            null
        }
    }

    //Api Calls de Top Rated
    suspend fun getTopRatedFilms(apiKey: String): List<Film>? {
        return try {
            val response = filmService.getTopRatedFilms(apiKey)
            return if (response.isSuccessful) response.body()?.results else null
        } catch (e: Exception) {
            null
        }
    }

    //Api Calls de Popular
    suspend fun getPopularFilms(apiKey: String): List<Film>? {
        return try {
            val response = filmService.getPopularFilms(apiKey)
            return if (response.isSuccessful) response.body()?.results else null
        } catch (e: Exception) {
            null
        }
    }

    //Agregar pelicula a favoritos
    suspend fun newFavourite(film: Fav_Film_Entitie) = filmDao.newFavourite(film)

    //Eliminar pelicula de favoritos
    suspend fun deleteFavourite(film: Fav_Film_Entitie) = filmDao.deleteFavourite(film)

    //Busca una pelicula en favoritos
    suspend fun getFavourite(film: Film) = filmDao.getFavourite(film.title)

    //Busca todas las peliculas de favoritos
    suspend fun getFavouritesList() = filmDao.getFavouritesList()


}