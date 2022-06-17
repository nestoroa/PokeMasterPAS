package com.nestoroa.pokemaster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nestoroa.pokemaster.PokemonTrainerApplication.Companion.prefs
import com.nestoroa.pokemaster.activities.TrainerRegistration
import com.nestoroa.pokemaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkTrainerValues()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        getAllPokemon()

        binding.btnCapturar.setOnClickListener {
            addPokemon(randPokemon())
        }
        binding.btnFree.setOnClickListener {
            // TODO : DELETE ALL POKEMON
        }
        setSupportActionBar(binding.toolbar)

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
*/

    }

    private fun checkTrainerValues(){
        if( prefs.SHARED_NAME.isNotEmpty() &&
            prefs.SHARED_POKEMONS.isNotEmpty() &&
            prefs.SHARED_USER_PIC.isNotEmpty() )
        {
            binding.tvTrainerName.text = prefs.SHARED_NAME
            binding.tvTrainerPokemonCount.text = "Pokémon capturados: ${prefs.SHARED_POKEMONS.length}"
            Glide.with(this)
                .load(prefs.SHARED_USER_PIC)
                .into(binding.imgTrainerPicture)
        } else
        {
            startActivity(Intent(this, TrainerRegistration::class.java))
        }
    }


    private fun setupRecyclerView(){
        listAdapter = PokemonListAdapter(this)
        binding.rvCapturedPokemon.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
    }

    private fun starterPokemon(): MutableList<Pokemon>{
        val pokemon = Pokemon(
            id = 25,
            name = "Pikachu",
            types = "Electric",
            spriteURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
        return  mutableListOf(pokemon)
    }

    private fun getAllPokemon() {
        val pokemonData = starterPokemon()
        listAdapter.submitList(pokemonData)
    }

    private fun addPokemon(pokemon : Pokemon) {
        val pokemonData = starterPokemon()
        pokemonData.add(pokemon)
        listAdapter.submitList(pokemonData)
    }

    override fun onClick(pokemon: Pokemon) {
        // Nada
    }
}