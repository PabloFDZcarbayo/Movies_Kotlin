package com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie

@Dao
interface User_DAO  {

    @Insert
    suspend fun createUser(user: User_Entitie)

    @Delete
    suspend fun deleteUser(user: User_Entitie)

    @Update
    suspend fun updateUser(user: User_Entitie)

    @Query ("SELECT * FROM Users WHERE email = :email")
    suspend fun getUser(email: String): User_Entitie?

    @Query ("SELECT * FROM Users WHERE (email = :email  OR username = :username) AND password = :password")
    suspend fun validateUser(username: String,email: String,password: String): User_Entitie?


}