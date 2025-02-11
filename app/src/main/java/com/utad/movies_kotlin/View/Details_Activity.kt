package com.utad.movies_kotlin.View

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.utad.movies_kotlin.Model.Film
import com.utad.movies_kotlin.R
import com.utad.movies_kotlin.ViewModel.Film_ViewModel
import com.utad.movies_kotlin.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Details_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val filmViewmodel: Film_ViewModel by viewModels()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Recuperamos los datos de la pelicula
        val film = intent.getParcelableExtra("film", Film::class.java)
        binding.movieTitle.text = film?.title
        binding.movieOverview.text = film?.overview
        Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500/${film?.posterPath}")
            .into(binding.imgMoviePoster)
        //Comprobamos si la pelicula esta en favoritos
        filmViewmodel.getFavouriteState(film!!)


        //Evento para cambiar el estado de favorito
        binding.btnFavourite.setOnClickListener() {
            filmViewmodel.changeFavouriteState(film!!)
        }

        //Observamos el liveData que nos indica que icono debemos cargar
        filmViewmodel.iconToShow.observe(this) { iconState ->
            when (iconState) {
                "empty" -> binding.btnFavourite.setImageResource(R.drawable.empty_star)
                else -> binding.btnFavourite.setImageResource(R.drawable.gold_star)
            }
        }

        //Observamos los mensajes que nos manda el viewModel
        filmViewmodel.showToast.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }


}


