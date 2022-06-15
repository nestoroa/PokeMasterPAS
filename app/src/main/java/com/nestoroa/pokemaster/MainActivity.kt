package com.nestoroa.pokemaster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.nestoroa.pokemaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NetworkSingleton.getInstance(this.applicationContext).requestQueue

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        getPokemons()

        binding.btnCapturar.setOnClickListener {
            var url = "https://pokeapi.co/api/v2/pokemon/${(1..666).random()}"
            var jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    var pkName = response.getString("name")
                    var pkTypes = response.getJSONArray("types")
                    var pkType = pkTypes.getJSONObject(0).getJSONObject("type").getString("name")
                    for (i in 1 until pkTypes.length()) {
                        pkType = pkType + ", " + pkTypes.getJSONObject(i).getJSONObject("type").getString("name")
                    }
                    val pkNumber = response.getInt("id")
                    val pkSpriteURL = response.getJSONObject("sprites").getString("front_default")
                    Log.d("PokeLog","$pkName,$pkNumber,$pkType,$pkSpriteURL")
                    addPokemon(Pokemon(
                        name = pkName,
                        id = pkNumber,
                        types = pkType,
                        spriteURL = pkSpriteURL
                    ))
                },
                { error ->
                    var pokemon = Pokemon(
                        name = "MissingNo",
                        spriteURL = "http://images.wikia.com/unanything/images/c/c1/MISSINGNO.png",
                        id = 999,
                        types = error.toString()
                    )
                    addPokemon(pokemon)

                }
            )
            NetworkSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        }
        binding.btnFree.setOnClickListener {
            removePokemon()
        }
        setSupportActionBar(binding.toolbar)

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
*/

    }

    override fun onClick(pokemon: Pokemon) {
        TODO("Not yet implemented")
    }


    private fun setupRecyclerView(){
        listAdapter = PokemonListAdapter(this)
        binding.rvCapturedPokemon.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
    }


    private fun pokemons(): MutableList<Pokemon>{
        val pokemon = Pokemon(
            id = 25,
            name = "Pikachu",
            types = "Electric",
            spriteURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
        return  mutableListOf(pokemon)
    }


    private fun getPokemons() {
        val pokemonData = pokemons()
        listAdapter.submitList(pokemonData)
    }

    private fun addPokemon(pokemon : Pokemon) {
        val pokemonData = listAdapter.currentList.toMutableList()
        pokemonData.add(pokemon)
        listAdapter.submitList(pokemonData)
    }

    private fun removePokemon() {
        val pokemonData = listAdapter.currentList.toMutableList()
        pokemonData.clear()
        listAdapter.submitList(pokemonData)
    }


/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
*/
}