package com.utad.movies_kotlin.Data.Remote

import com.google.gson.annotations.SerializedName
import com.utad.movies_kotlin.Model.Film

data class Films_Results(
    //Este es el nombre que devulce Tmbd para su array de resultados
    @SerializedName("results") val results: List<Film>
) {
}