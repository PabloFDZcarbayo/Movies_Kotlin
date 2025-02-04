package com.utad.movies_kotlin.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie
import com.utad.movies_kotlin.R
import com.utad.movies_kotlin.ViewModel.User_ViewModel
import com.utad.movies_kotlin.databinding.ActivitySingUpBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Sing_Up_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    @Inject
    lateinit var user_viewModel: User_ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Recuperamos los campos del formulario y comprobamoos que este todo correcto
        binding.btnMakeAccount.setOnClickListener {
            val username = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Error: Passwords do not match"
                binding.etConfirmPassword.setText("")

            } else {
                var user = User_Entitie(username = username, password = password, email = email)
                user_viewModel.CreateUser(
                    user,
                    binding.etUserName,
                    binding.etPassword,
                    binding.etEmail
                )
                clearInputFields()
            }
        }

        //Evento para la navegacion
        user_viewModel.navigateToLogin.observe(this) {
            if (it) {
                navigateToLogin()
            }
        }

       //Evento para mostrar Toast
        user_viewModel.showToast.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun navigateToLogin() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    //Funcion para limpiar los campos
    private fun clearInputFields() {
        binding.etUserName.setText("")
        binding.etPassword.setText("")
        binding.etConfirmPassword.setText("")
        binding.etEmail.setText("")

    }
}
