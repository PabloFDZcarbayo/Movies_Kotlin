package com.utad.movies_kotlin.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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

    @Inject
    lateinit var user_viewModel: User_ViewModel

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

            var user = User_Entitie(username = usernameOrEmail, password = password, email = usernameOrEmail)
            user_viewModel.validateUser(
                user,
                binding.etUserNameOrEmail,
                binding.etPassword
            )
        }


        //Navegacion a vista registro
        binding.btnMakeAccount.setOnClickListener() {
            val intent = Intent(this, Sing_Up_Activity::class.java)
            startActivity(intent)
        }
    }
}