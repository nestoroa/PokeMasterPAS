package com.nestoroa.pokemaster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.nestoroa.pokemaster.PokemonTrainerApplication.Companion.prefs
import com.nestoroa.pokemaster.activities.TrainerRegistration
import com.nestoroa.pokemaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        NetworkSingleton.getInstance(this.applicationContext).requestQueue

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()

        checkTrainerValues()

        binding.btnCapturar.setOnClickListener {
            capturePokemon()
        }
        binding.btnFree.setOnClickListener { removePokemon() }

        setContentView(binding.root)
    }

    private fun checkTrainerValues() {
        if (prefs.getName() != "" && prefs.getPicURL() != "") {
            loadTrainerData()
        } else {
            startActivity(Intent(this, TrainerRegistration::class.java))
        }
    }

    private fun loadTrainerData() {
        Glide.with(this)
            .load(prefs.getPicURL())
            .into(binding.imgTrainerPicture)
        binding.tvTrainerName.text = prefs.getName()
        listAdapter.submitList(prefs.getPokemons() as MutableList<Pokemon>)
        binding.tvTrainerPokemonCount.text =
            "Pokémon capturados: ${prefs.getPokemons().count()}"
    }

    private fun setupRecyclerView() {
        listAdapter = PokemonListAdapter(this)
        binding.rvCapturedPokemon.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
    }

    private fun addPokemon(pokemon: Pokemon) {
        val pokemonData = listAdapter.currentList.toMutableList()
        pokemonData.add(pokemon)
        listAdapter.submitList(pokemonData)
        binding.tvTrainerPokemonCount.text =
            "Pokémon capturados: ${pokemonData.count()}"
    }

    private fun removePokemon() {
        val pokemonData = listAdapter.currentList.toMutableList()
        pokemonData.clear()
        listAdapter.submitList(pokemonData)
        binding.tvTrainerPokemonCount.text =
            "Pokémon capturados: ${pokemonData.count()}"
    }


    private fun capturePokemon() {
        val url = "https://pokeapi.co/api/v2/pokemon/${(1..666).random()}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val pkName = response.getString("name")
                val pkTypes = response.getJSONArray("types")
                var pkType = pkTypes.getJSONObject(0).getJSONObject("type").getString("name")
                for (i in 1 until pkTypes.length()) {
                    pkType =
                        pkType + ", " + pkTypes.getJSONObject(i).getJSONObject("type")
                            .getString("name")
                }
                val pkNumber = response.getInt("id")
                val pkSpriteURL = response.getJSONObject("sprites").getString("front_default")
                Log.d("PokeLog", "$pkName,$pkNumber,$pkType,$pkSpriteURL")
                addPokemon(
                    Pokemon(
                        name = pkName,
                        id = pkNumber,
                        types = pkType,
                        spriteURL = pkSpriteURL
                    )
                )
            },
            { error ->
                addPokemon(
                    Pokemon(
                        name = "MissingNo",
                        spriteURL = "http://images.wikia.com/unanything/images/c/c1/MISSINGNO.png",
                        id = 999,
                        types = error.toString()
                    )
                )
            }
        )
        NetworkSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onClick(pokemon: Pokemon) {
        // Nada
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu_item -> {
                prefs.wipe()
                checkTrainerValues()
            }
        }
        return when (item.itemId) {
            R.id.logout_menu_item -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onResume() {
        super.onResume()
        loadTrainerData()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        loadTrainerData()
    }

    override fun onDestroy() {
        super.onDestroy()
        prefs.savePokemons(listAdapter.currentList.toMutableList())
    }

    override fun onStop() {
        super.onStop()
        prefs.savePokemons(listAdapter.currentList.toMutableList())
    }


}