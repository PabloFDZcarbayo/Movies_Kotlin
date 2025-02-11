package com.utad.movies_kotlin.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.movies_kotlin.Adapter.Film_Adapter
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.Fav_Film_Entitie
import com.utad.movies_kotlin.Data.Repositories.Film_Repository
import com.utad.movies_kotlin.Model.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Film_ViewModel @Inject constructor(private val repository: Film_Repository) : ViewModel() {

    //Con esta clave realizaremos nuestras llamadas a Api
    private val apiKey = "284c122df03587f5f8a10ffba8ad8daf"

    //Indicamos  al RV que peliculas hay que mostrar en cada momento
    private val _moviesToShow = MutableLiveData<List<Film>?>(emptyList())
    val moviesToShow: MutableLiveData<List<Film>?> = _moviesToShow

    //Lo utilizaremos para mandar nuestros mensajes de toast a las vistas
    private val _showToast = MutableLiveData<String?>()
    val showToast: MutableLiveData<String?> = _showToast

    //Indicamos al detail view que icono de favoritos debe mostrar
    private val _iconToShow = MutableLiveData<String?>()
    val iconToShow: MutableLiveData<String?> = _iconToShow


    /*
Esta función nos permite realizar llamadas a una API de manera genérica.
Recibe como parámetro una función suspendida (`apiCall`) que toma un `String` (normalmente una clave de API)
y devuelve una lista de películas (`List<Film>`) o `null` si la llamada no tiene éxito.

La función `loadFilms` se encarga de:
1. Lanzar una corrutina en el `viewModelScope` para realizar la llamada a la API de manera asíncrona.
2. Ejecutar la función `apiCall` pasándole la clave de API (`apiKey`).
3. Manejar el resultado de la llamada:
   - Si la llamada es exitosa y devuelve una lista de películas, se actualiza `_moviesToShow` con esa lista.
   - Si la llamada falla o devuelve `null`, se actualiza `_moviesToShow` con una lista vacía.
4. Capturar cualquier excepción que ocurra durante la llamada y manejar el error actualizando `_moviesToShow` con una lista vacía.

Esta función es genérica y reutilizable, ya que no sabe cómo se obtienen los datos específicos (por ejemplo, películas populares, mejor valoradas, etc.).
En su lugar, delega la lógica de la llamada a la API a la función `apiCall` que se le pasa como parámetro.
*/
    private fun loadFilms(apiCall: suspend (String) -> List<Film>?) {
        viewModelScope.launch {
            try {
                // Llama a la función `apiCall` pasándole la clave de API (`apiKey`).
                val films = apiCall(apiKey)
                if (films != null) {
                    _moviesToShow.postValue(films)
                } else {
                    _moviesToShow.postValue(emptyList())
                }
            } catch (e: Exception) {
                _moviesToShow.postValue(emptyList())
            }
        }
    }

    /* Llama a `loadFilms` y le pasa una función lambda que realiza la llamada a la API
    para obtener las películas mejor valoradas.
    `it` es la clave de API (`apiKey`) que `loadFilms` pasa automáticamente.
     */
    fun loadTopRatedFilms() {
        loadFilms { repository.getTopRatedFilms(it) }
    }

    fun loadPopularFilms() {
        loadFilms { repository.getPopularFilms(it) }
    }

    fun loadNowPlayingFilms() {
        loadFilms { repository.getNowPlayingFilms(it) }
    }

    fun loadUpcomingFilms() {
        loadFilms { repository.getUpcomingFilms(it) }
    }

    //Cargar  la lista de peliculas de favoritas
    fun loadFavouriteFilms() {
        viewModelScope.launch {
            val films = repository.getFavouritesList()
            _moviesToShow.postValue(films)
        }
    }

    //Esta funcion nos permite comprobar si una pelicula ya se encuentra en favoritos
    fun getFavouriteState(film: Film) {
        viewModelScope.launch {
            val favFilm = repository.getFavourite(film)
            iconToShow.value = if (favFilm != null) "gold" else "empty"
        }
    }


    //Esta funcion nos permite anadir o eliminar una pelicula de favoritos dependiendo de si ya estaba o no
    fun changeFavouriteState(film: Film) {
        viewModelScope.launch {
            // Verifica si la película ya está en favoritos
            val favFilm = repository.getFavourite(film)
            if (favFilm != null) {
                /* Elimina la pelicula, idica el icono empty, recarga la nueva lista de
                favoritos e indica que debemos mostrar la lista actualizada, por ultimo mostramos un toast */
                repository.deleteFavourite(favFilm)
                iconToShow.value = "empty"
                showToast.value = "Film removed from favourites"
            } else {
                //Crea una nueva  entidad pelicula en favoritos
                val newFavFilm = Fav_Film_Entitie(
                    id = 0, // El ID se generará automáticamente
                    title = film.title,
                    overview = film.overview,
                    posterPath = film.posterPath,
                    popularity = film.popularity,
                    releaseDate = film.releaseDate,
                    backdropPath = film.backdropPath,
                    isFavourite = true
                )
                /*Anade la pelicula a favoritos, indica el icono gold,
                * por ultimo mostramos un toast*/
                repository.newFavourite(newFavFilm)
                iconToShow.value = "gold"
                showToast.value = "Film added to favourites"
            }
        }

    }

}


