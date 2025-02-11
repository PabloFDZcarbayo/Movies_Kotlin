package com.utad.movies_kotlin.View


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utad.movies_kotlin.Data.Local.Database.ROOM.Entities.User_Entitie
import com.utad.movies_kotlin.R
import com.utad.movies_kotlin.ViewModel.User_ViewModel
import com.utad.movies_kotlin.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val user_viewModel: User_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        binding.btnLogin.setOnClickListener {
            val usernameOrEmail = binding.etUserNameOrEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User_Entitie(
                username = usernameOrEmail,
                password = password,
                email = usernameOrEmail
            )
            user_viewModel.validateUser(
                user,
                binding.etUserNameOrEmail,
                binding.etPassword
            )
        }

        user_viewModel.navigateTo.observe(this) {
            if (it)
                navigateToRV()
        }

        user_viewModel.showToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        //Navegacion a vista registro
        binding.btnMakeAccount.setOnClickListener() {
            val intent = Intent(this, Sing_Up_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToRV() {
        val navigateToRV = Intent(this, Movies_RV_Activity::class.java)
        startActivity(navigateToRV)
    }
}