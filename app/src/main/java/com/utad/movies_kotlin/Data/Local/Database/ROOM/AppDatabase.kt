package com.utad.movies_kotlin.Data.Local.Database.ROOM

import androidx.room.Database
import androidx.room.RoomDatabase
import com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO.User_DAO
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie

@Database (entities = [User_Entitie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun user_dao(): User_DAO



}

