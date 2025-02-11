package com.utad.movies_kotlin.View

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.movies_kotlin.Adapter.Film_Adapter
import com.utad.movies_kotlin.Model.Film
import com.utad.movies_kotlin.R
import com.utad.movies_kotlin.ViewModel.Film_ViewModel
import com.utad.movies_kotlin.databinding.ActivityMoviesRvBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Movies_RV_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesRvBinding
    private lateinit var adapter: Film_Adapter
    private val filmViewmodel: Film_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesRvBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToolbar()

        //Por defecto se cargaran las peliculas en cartelera
        filmViewmodel.loadNowPlayingFilms()

        //Inicia el recyclerView
        initializeRecyclerView()

        /* Observamos que peliculas debemos
         cargar en cada momento, y mandamos actualizar el recyclerView */
        filmViewmodel.moviesToShow.observe(this) { films ->
            films?.let {
                updateRecyclerView(it)
            }
        }

    }

    /*Cargamos el menu del toolbar, en este caso cargaremos un menu vacio,
    el cual solo contiene el icoco, mas tarde cargaremos el popupmenu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //Configuramos el toolbar y agregamos el evento que mostrara el popupmenu
    private fun setupToolbar() {
        binding.toolbar.apply {
            // 1. Establece el Toolbar personalizado como ActionBar de la Activity
            setSupportActionBar(this)
            // 3. Configura el comportamiento del menú
            setOnMenuItemClickListener { item ->
                showPopupMenu(this)
                true
            }


        }
    }


    private fun showPopupMenu(popupMenu: View) {
        // 1. Crea un contexto con el tema personalizado para el PopupMenu
        val wrapper = ContextThemeWrapper(this, R.style.PopupTheme)
        // 2. Inicializa el PopupMenu
        PopupMenu(wrapper, popupMenu, Gravity.END).apply {
            // 3. Aqui inflaremos el menu con todas nuestras opciones
            menuInflater.inflate(R.menu.options_menu, menu)

            // 4. Fuerza a mostrar los iconos, ya que por defecto no se muestran
            try {
                val method = this::class.java.getMethod("setForceShowIcon", Boolean::class.java)
                method.invoke(this, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // 5. Asigna un listener para los clics en los ítems del menú
            setOnMenuItemClickListener { item ->
                menuSelection(item.itemId)
                // 7. Cierra el menú después de la selección
                dismiss()
                true
            }
        }.show() // 8. Muestra el menú
    }


    private fun menuSelection(itemId: Int) {
        when (itemId) {
            R.id.MenuOption_Popular -> {
                filmViewmodel.loadPopularFilms()
                binding.toolbar.title = "Popular"
                binding.toolbar.navigationIcon=getResources().getDrawable(R.drawable.ic_popular)            }

            R.id.MenuOption_NowPlaying -> {
                filmViewmodel.loadNowPlayingFilms()
                binding.toolbar.title = "Now Playing"
                binding.toolbar.navigationIcon=getResources().getDrawable(R.drawable.ic_now_playing)
            }

            R.id.MenuOption_TopRated -> {
                filmViewmodel.loadTopRatedFilms()
                binding.toolbar.title = "Top Rated"
                binding.toolbar.navigationIcon=getResources().getDrawable(R.drawable.ic_top_rated)
            }

            R.id.MenuOption_Upcoming -> {
                filmViewmodel.loadUpcomingFilms()
                binding.toolbar.title = "Upcoming"
                binding.toolbar.navigationIcon=getResources().getDrawable(R.drawable.ic_upcoming)
            }

            R.id.MenuOption_Favourites -> {
                filmViewmodel.loadFavouriteFilms()
                binding.toolbar.title = "Favorites"
                binding.toolbar.navigationIcon=getResources().getDrawable(R.drawable.ic_favourites)
            }
        }
    }


    /*En la activity de details, podemos agregar y borrar peliculas de favoritas,
    en el caso de que estuviesemos viendo todas nuestras favoritas,
    con onResume nos aseguramos de que cuando volvamos al RV las favoritas esten actualizadas
     */

    override fun onResume() {
        super.onResume()
        if (binding.toolbar.title == "Favorites")
            filmViewmodel.loadFavouriteFilms()
    }


    /* Inicializamos el recyclerView, y le pasamos una lista vacia, para que no de error
    * Debemos asignarle un adaptador y un LayoutManager.
    * Configuramos el adaptador para que sea un Film_Adapter que  gestiona una lista y un listener */
    fun initializeRecyclerView() {
        binding.RvMovies.layoutManager = LinearLayoutManager(this)
        adapter = Film_Adapter(mutableListOf<Film>()) { film -> navigateToDetails(film) }
        binding.RvMovies.adapter = adapter

    }

    //Notifica un cambio en el recyclerView al adaptador
    private fun updateRecyclerView(films: List<Film>) {
        adapter.newFilms(films)
    }


    /* Metodo para navegar a la pantalla de detalles,
    contiene un parcelable para poder pasar la pelicula */
    fun navigateToDetails(film: Film) {
        val navigateToDetails = Intent(this, Details_Activity::class.java)
        navigateToDetails.putExtra("film", film)
        startActivity(navigateToDetails)
    }


}



