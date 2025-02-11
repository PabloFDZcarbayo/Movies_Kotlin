package com.utad.movies_kotlin.Data.Repositories

import com.utad.movies_kotlin.Data.Local.Database.ROOM.DAO.User_DAO
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie
import javax.inject.Inject


// Clase que nos permite interactuar con la base de datos
class User_Repository @Inject constructor(private val user_DAO: User_DAO) {

    // Insertar un usuario
    suspend fun insertUser(user: User_Entitie) {
        user_DAO.createUser(user)
    }

    // Actualizar un usuario
    suspend fun updateUser(user: User_Entitie) {
        user_DAO.updateUser(user)
    }

    // Eliminar un usuario
    suspend fun deleteUser(user: User_Entitie) {
        user_DAO.deleteUser(user)

    }

    //Buscar un usuario por su email
    suspend fun getUser(email: String): User_Entitie? {
        return user_DAO.getUser(email)
    }

    suspend fun validateUser(username: String, email: String, password: String): User_Entitie? {
        return user_DAO.validateUser(
            username, email, password
        )
    }

}
