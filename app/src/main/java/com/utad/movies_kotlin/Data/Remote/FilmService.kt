package com.utad.movies_kotlin.Data.Remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingFilms(
        @Query("api_key") apiKey: String
    ): Response<Films_Results>

    @GET("movie/upcoming")
    suspend fun getUpcomingFilms(
        @Query("api_key") apiKey: String
    ): Response<Films_Results>

    @GET("movie/top_rated")
    suspend fun getTopRatedFilms(
        @Query("api_key") apiKey: String
    ): Response<Films_Results>

    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query("api_key") apiKey: String
    ): Response<Films_Results>
}