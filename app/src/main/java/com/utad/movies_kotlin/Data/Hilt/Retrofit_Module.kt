package com.utad.movies_kotlin.Data.Hilt

import com.utad.movies_kotlin.Data.Remote.FilmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Retrofit_Module {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    @Provides
    @Singleton
    fun provideFilmService(retrofit: Retrofit): FilmService =
        retrofit.create(FilmService::class.java)


}