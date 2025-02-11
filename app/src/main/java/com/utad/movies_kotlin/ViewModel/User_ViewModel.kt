package com.utad.movies_kotlin.ViewModel

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie
import com.utad.movies_kotlin.Data.Repositories.User_Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class User_ViewModel @Inject constructor(private val user_repository: User_Repository) :
    ViewModel() {


    // LiveData para crear un evento que indica a la vista cuando hay que realizar una navegación
    private val _navigateTo = MutableLiveData<Boolean>()
    val navigateTo: LiveData<Boolean> get() = _navigateTo

    //Evento para mostrar Toast
    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> get() = _showToast

    fun CreateUser(
        user: User_Entitie,
        et_UserName: EditText,
        et_Password: EditText,
        et_Email: EditText
    ) {
        viewModelScope.launch {
            val (isvalid, errorMessage) = isValidData(user)
            if (!isvalid) {
                when (errorMessage) {
                    "Error: User is required" -> et_UserName.error = errorMessage
                    "Error: Password is required" -> et_Password.error = errorMessage
                    "Error: Email is required" -> et_Email.error = errorMessage
                }
                return@launch
            }
            try {
                var userExits = user_repository.getUser(user.email)
                if (userExits != null) {
                    _showToast.value = "Error: User already exists"

                } else {
                    user_repository.insertUser(user)
                    _showToast.value = "User created successfully"
                    _navigateTo.value = true
                }

            } catch (Exception: Exception) {
                _showToast.value = "Error: problem creating user"

            }
        }
    }


    fun validateUser(user: User_Entitie, et_UserName: EditText, et_Password: EditText) {
        viewModelScope.launch {
            val (isvalid, errorMessage) = isValidData(user)
            if (!isvalid) {
                when (errorMessage) {
                    "Error: User is required" -> et_UserName.error = errorMessage
                    "Error: Password is required" -> et_Password.error = errorMessage
                }
                return@launch
            } else {
                val userValid =
                    user_repository.validateUser(user.username, user.email, user.password)
                if (userValid != null) {
                    _navigateTo.value = true
                } else {
                    _showToast.value = "Error: User not found"
                }
            }
        }
    }


    // Actualizar un usuario
    fun updateUser(user: User_Entitie) {
        viewModelScope.launch {
            user_repository.updateUser(user)
        }
    }

    // Eliminar un usuario
    fun deleteUser(user: User_Entitie) {
        viewModelScope.launch {
            user_repository.deleteUser(user)

        }
    }


    fun isValidData(user: User_Entitie): Pair<Boolean, String> {
        return when {
            user.username.isBlank() -> Pair(false, "Error: User is required")
            user.password.isBlank() -> Pair(false, "Error: Password is required")
            user.email.isBlank() -> Pair(false, "Error: Email is required")
            else -> Pair(true, "")
        }

    }
}
