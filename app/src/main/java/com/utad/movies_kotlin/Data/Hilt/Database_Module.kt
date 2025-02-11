package com.utad.movies_kotlin.Data.Hilt

import android.content.Context
import androidx.room.Room
import com.utad.movies_kotlin.Data.Local.Database.ROOM.AppDatabase
import com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO.Film_DAO
import com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO.User_DAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) //componente predefinido en Hilt que está asociado con el ciclo de vida de la aplicación.
// Las dependencias que se instalan aquí son únicas y viven hasta que la aplicación termina.

//object garantiza que habrá una unica instancia en la app
object Database_Module {
    @Provides
    @Singleton //Unica instancia de la base de datos
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration() // -> Si detecta una version nueva, borrara y recreara la base de datos, solo util en desarrollo
            .build()
    }
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): User_DAO = appDatabase.user_dao()

    @Provides
    @Singleton
    fun providesFilmDao(appDatabase: AppDatabase): Film_DAO = appDatabase.film_dao()
}